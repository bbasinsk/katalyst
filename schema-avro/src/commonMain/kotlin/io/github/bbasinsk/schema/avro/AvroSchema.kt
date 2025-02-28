package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Field
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Primitive
import org.apache.avro.Schema as AvroSchema
import org.apache.avro.Schema.Field as AvroField
import org.apache.avro.Schema.Type as AvroType

fun Schema<*>.toAvroSchema(): AvroSchema =
    when (this) {
        is Schema.Empty -> AvroSchema.create(AvroType.NULL)
        is Schema.Bytes -> AvroSchema.create(AvroType.BYTES)
        is Schema.Lazy -> schema().toAvroSchema()
        is Schema.Default -> schema.toAvroSchema()
        is Schema.OrElse<*, *> -> preferred.toAvroSchema()
        is Primitive -> when (this) {
            is Primitive.Boolean -> AvroSchema.create(AvroType.BOOLEAN)
            is Primitive.Double -> AvroSchema.create(AvroType.DOUBLE)
            is Primitive.Float -> AvroSchema.create(AvroType.FLOAT)
            is Primitive.Int -> AvroSchema.create(AvroType.INT)
            is Primitive.Long -> AvroSchema.create(AvroType.LONG)
            is Primitive.String -> AvroSchema.create(AvroType.STRING)
            is Primitive.Enumeration<*> ->
                AvroSchema.createEnum(metadata.name, null, metadata.namespace, values.map { it.toString() })
        }

        is Schema.Transform<*, *> -> this.schema.toAvroSchema()

        is Schema.Optional<*> ->
            AvroSchema.createUnion(listOf(AvroSchema.create(AvroType.NULL), schema.toAvroSchema()))

        is Schema.StringMap<*> ->
            AvroSchema.createMap(valueSchema.toAvroSchema())

        is Schema.Collection<*> ->
            AvroSchema.createArray(itemSchema.toAvroSchema())

        is Schema.Union<*> ->
            AvroSchema.createUnion(unsafeCases().map { it.schema.toAvroSchema() })

        is Schema.Record<*> ->
            AvroSchema.createRecord(
                /* name = */ metadata.name,
                /* doc = */ null,
                /* namespace = */ metadata.namespace,
                /* isError = */ false,
                /* fields = */ this.unsafeFields().map { it.toAvroField() }
            )
    }

private fun Field<*, *>.toAvroField(): AvroField =
    AvroField(
        name,
        schema.toAvroSchema(),
        null,
        if (schema is Schema.Optional) AvroField.NULL_DEFAULT_VALUE else null
    )
