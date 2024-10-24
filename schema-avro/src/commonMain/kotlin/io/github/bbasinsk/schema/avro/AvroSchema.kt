package io.github.bbasinsk.schema.avro

import io.github.bbasinsk.schema.Field
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive
import org.apache.avro.Schema as AvroSchema
import org.apache.avro.Schema.Field as AvroField
import org.apache.avro.Schema.Type as AvroType

fun Schema<*>.toAvroSchema(): AvroSchema =
    when (this) {
        is Schema.Empty ->
            AvroSchema.create(AvroType.NULL)

        is Schema.Bytes ->
            AvroSchema.create(AvroType.BYTES)

        is Schema.Enumeration ->
            AvroSchema.createEnum(metadata.name, null, metadata.namespace, values.map { it.toString() })

        is Schema.Lazy ->
            this.schema().toAvroSchema()

        is Schema.Default ->
            this.schema.toAvroSchema()

        is Schema.OrElse ->
            this.preferred.toAvroSchema()

        is Schema.Primitive ->
            when (primitive) {
                StandardPrimitive.Boolean -> AvroSchema.create(AvroType.BOOLEAN)
                StandardPrimitive.Int -> AvroSchema.create(AvroType.INT)
                StandardPrimitive.String -> AvroSchema.create(AvroType.STRING)
                StandardPrimitive.Double -> AvroSchema.create(AvroType.DOUBLE)
                StandardPrimitive.Float -> AvroSchema.create(AvroType.FLOAT)
                StandardPrimitive.Long -> AvroSchema.create(AvroType.LONG)
            }

        is Schema.Transform<*, *> ->
            this.schema.toAvroSchema()

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
