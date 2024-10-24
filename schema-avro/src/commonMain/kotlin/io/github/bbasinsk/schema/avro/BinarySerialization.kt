package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Case
import io.github.bbasinsk.schema.Field
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.avro.ByteAllocation.ID_SIZE
import io.github.bbasinsk.schema.avro.ByteAllocation.MAGIC_BYTE
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.io.EncoderFactory
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

object BinarySerialization {
    private val encoderFactory: EncoderFactory = EncoderFactory.get()

    fun <A> Schema<A>.serialize(schemaId: Int, payload: A): ByteArray? =
        if (payload == null) null
        else ByteArrayOutputStream().use { out ->
            out.write(MAGIC_BYTE.toInt())
            out.write(ByteBuffer.allocate(ID_SIZE).putInt(schemaId).array())
            when (this) {
                is Schema.Bytes -> out.write(payload as ByteArray)
                else -> out.writeEncodedData(toAvroSchema(), encode(payload))
            }
            out.toByteArray()
        }

    private fun ByteArrayOutputStream.writeEncodedData(schema: org.apache.avro.Schema, data: Any?) =
        encoderFactory.binaryEncoder(this, null).let { encoder ->
            GenericDatumWriter<Any>(schema).write(data, encoder)
            encoder.flush()
        }

    // Output is GenericData.Record, GenericData.Array, GenericData.EnumSymbol, Map, ByteBuffer, primitive (String, Int, etc), or null
    @Suppress("UNCHECKED_CAST") // Due to type erasure
    private fun <A> Schema<A>.encode(value: A): Any? =
        when (this) {
            is Schema.Empty -> null
            is Schema.Bytes -> ByteBuffer.wrap(value as ByteArray)
            is Schema.Primitive -> value
            is Schema.Lazy -> this.schema().encode(value)
            is Schema.OrElse -> this.preferred.encode(value)
            is Schema.Optional<*> -> if (value == null) null else (schema as Schema<Any>).encode(value)
            is Schema.Default -> if (value == null) default else schema.encode(value)
            is Schema.Transform<A, *> -> (schema as Schema<Any?>).encode(encode(value))
            is Schema.Enumeration -> GenericData.EnumSymbol(toAvroSchema(), value.toString())
            is Schema.Union<*> -> unsafeCases().firstNotNullOf { case ->
                (case as Case<A, Any?>).deconstruct(value)?.let { value ->
                    case.schema.encode(value)
                }
            }

            is Schema.Record<*> -> {
                GenericData.Record(toAvroSchema()).also { data ->
                    unsafeFields().forEach { field ->
                        val fieldValue = (field as Field<A, Any?>).extract(value)
                        data.put(field.name, field.schema.encode(fieldValue))
                    }
                }
            }

            is Schema.Collection<*> ->
                GenericData.Array(toAvroSchema(), (value as List<Any?>).map { (itemSchema as Schema<Any?>).encode(it) })

            is Schema.StringMap<*> -> (value as Map<String, Any?>)
                .mapValues { (_, value) -> (valueSchema as Schema<Any?>).encode(value) }
        }

}
