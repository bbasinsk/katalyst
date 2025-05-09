package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.avro.ByteAllocation.ID_SIZE
import io.github.bbasinsk.schema.avro.ByteAllocation.MAGIC_BYTE
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.Validation.Companion.invalid
import io.github.bbasinsk.validation.Validation.Companion.valid
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.mapInvalid
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse
import org.apache.avro.Schema.Type
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DecoderFactory
import org.apache.avro.util.Utf8
import java.nio.ByteBuffer
import kotlin.reflect.typeOf


sealed interface DeserializationError {
    data class InvalidField(val reason: String) : DeserializationError
    data class Or(
        val preferred: List<DeserializationError>,
        val fallback: List<DeserializationError>
    ) : DeserializationError

    private fun tab(n: Int) = "    ".repeat(n)
    fun reason(indent: Int = 0): String =
        when (this) {
            is InvalidField -> reason
            is Or -> buildString {
                appendLine(tab(indent) + "Either:")
                appendLine(tab(indent + 1) + preferred.joinToString { it.reason(indent + 1) })
                appendLine(tab(indent + 1) + fallback.joinToString { it.reason(indent + 1) })
            }
        }
}

object BinaryDeserialization {
    private val decoderFactory: DecoderFactory = DecoderFactory.get()

    fun <A> Schema<A>.deserializeIgnoringSchemaId(payload: ByteArray?): Validation<DeserializationError, A> =
        deserialize(payload) { toAvroSchema() }

    fun <A> Schema<A>.deserialize(
        payload: ByteArray?,
        getSchema: (Int) -> org.apache.avro.Schema
    ): Validation<DeserializationError, A> {
        if (payload == null) return decode(null)

        val buffer = ByteBuffer.wrap(payload)

        if (buffer.get() != MAGIC_BYTE) {
            return invalid(DeserializationError.InvalidField("Unknown magic byte!"))
        }

        val id = buffer.int
        val writerSchema = getSchema(id)

        val length = buffer.limit() - 1 - ID_SIZE

        return if (writerSchema.type == Type.BYTES) {
            decode(ByteBuffer.wrap(ByteArray(length).also { buffer.get(it, 0, length) }))
        } else {
            val start = buffer.position() + buffer.arrayOffset()
            val decoder = decoderFactory.binaryDecoder(buffer.array(), start, length, null)

            Validation
                .runCatching { GenericDatumReader<Any>(writerSchema, toAvroSchema()).read(null, decoder) }
                .mapInvalid { e ->
                    DeserializationError.InvalidField(
                        e.message ?: "Unable to decode bytes: ${e.stackTraceToString()}"
                    )
                }
                .andThen { decode(it) }
        }
    }

    /*
    * Input is GenericData.Record, GenericData.Array, GenericData.EnumSymbol, Map, ByteBuffer, primitive (String, Int, etc), or null
    */
    @Suppress("UNCHECKED_CAST") // Due to type erasure
    private fun <A> Schema<A>.decode(input: Any?): Validation<DeserializationError, A> =
        when (this) {
            is Schema.Empty ->
                valid(null as A)

            is Schema.Bytes ->
                read(input) { it as? ByteBuffer? }.mapValid { it.array() as A }

            is Schema.Lazy -> this.schema().decode(input)
            is Schema.Metadata -> this.schema.decode(input)

            is Schema.Optional<*> ->
                if (input == null) valid(null as A) else (schema as Schema<A>).decode(input)

            is Schema.Default ->
                if (input == null) valid(this.default) else this.schema.decode(input)

            is Schema.OrElse<A, *> ->
                preferred.decode(input).orElse { preferredErrors ->
                    fallback.decode(input).andThen { b ->
                        Validation.fromResult(unsafeDecode(b)) {
                            DeserializationError.InvalidField(it.message ?: "unable to decode")
                        }
                    }.orElse { fallbackErrors ->
                        invalid(DeserializationError.Or(preferredErrors, fallbackErrors))
                    }
                }

            is Schema.Transform<A, *> ->
                this.schema.decode(input).andThen { b ->
                    (this as Schema.Transform<A, Any?>).decode(b).fold({ decoded ->
                        valid(decoded)
                    }, { error ->
                        invalid(DeserializationError.InvalidField(error.message ?: "unable to transform"))
                    })
                }

            is Schema.Primitive ->
                Validation.fromResult(this.decodePrimitiveString(input.toString())) {
                    DeserializationError.InvalidField("Unable to parse $input as ${this.name}")
                }

            is Schema.Collection<*> ->
                read(input) { it as? GenericData.Array<*> }.andThen { array ->
                    Validation.sequence(array.map { itemSchema.decode(it) }) as Validation<DeserializationError, A>
                }

            is Schema.Union<*> ->
                read(input) { input as? GenericRecord }.andThen { record: GenericRecord ->
                    val cases = unsafeCases()
                    Validation.requireNotNull(cases.find { it.name == record.schema.name }) {
                        DeserializationError.InvalidField("Invalid case for ${record.schema.name}, expected one of ${cases.map { it.name }}")
                    }.andThen { case ->
                        (case.schema as Schema<A>).decode(record)
                    }
                }

            is Schema.Record<*> ->
                read(input) { input as? GenericRecord }.andThen { record: GenericRecord ->
                    Validation.sequence(
                        unsafeFields().map { field ->
                            Validation.requireNotNull(record.schema.getField(field.name)) {
                                DeserializationError.InvalidField("Field ${field.name} not found in schema ${record.schema.name}")
                            }.mapValid { schemaField ->
                                record.get(schemaField.pos())
                            }.andThen { fieldValue ->
                                field.schema.decode(fieldValue)
                            }
                        }
                    ).mapValid { fieldValues -> unsafeConstruct(fieldValues) as A }
                }

            is Schema.StringMap<*> ->
                read(input) { it as? Map<Utf8, *> }.andThen { stringMap ->
                    Validation.sequence(
                        stringMap.map { (key, value) ->
                            valueSchema.decode(value).mapValid { key.toString() to it }
                        }
                    ).mapValid { it.toMap() } as Validation<DeserializationError, A>
                }
        }

    private inline fun <reified A> read(input: Any?, fromGeneric: (Any?) -> A?): Validation<DeserializationError, A> =
        fromGeneric(input)
            ?.let { valid(it) }
            ?: invalid(DeserializationError.InvalidField("Expected ${typeOf<A>()} but found ${input?.javaClass}"))
}
