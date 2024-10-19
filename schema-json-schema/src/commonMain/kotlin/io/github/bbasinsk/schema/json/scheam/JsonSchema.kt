package io.github.bbasinsk.schema.json.scheam

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive
import org.everit.json.schema.ArraySchema
import org.everit.json.schema.BooleanSchema
import org.everit.json.schema.CombinedSchema
import org.everit.json.schema.EnumSchema
import org.everit.json.schema.NullSchema
import org.everit.json.schema.NumberSchema
import org.everit.json.schema.ObjectSchema
import org.everit.json.schema.StringSchema
import org.everit.json.schema.Schema as JsonSchema

fun Schema<*>.toJsonSchema(): JsonSchema =
    toJsonSchemaImpl(
        nullable = null,
        default = null
    )

private fun <A> Schema<A>.toJsonSchemaImpl(
    nullable: Boolean?,
    default: A?,
): JsonSchema =
    when (this) {
        is Schema.Empty -> NullSchema.Builder().build()

        is Schema.Bytes -> StringSchema.Builder()
            // contentEncoding?
            .defaultValue(default).build()

        is Schema.Enumeration -> EnumSchema.Builder().possibleValues(values).build()

        is Schema.Lazy -> this.schema().toJsonSchemaImpl(nullable = nullable, default = default)

        is Schema.Default -> this.schema.toJsonSchemaImpl(default = this.default, nullable = nullable)

        is Schema.OrElse -> this.preferred.toJsonSchemaImpl(nullable = nullable, default = default)

        is Schema.Primitive ->
            when (primitive) {
                StandardPrimitive.Boolean ->
                    BooleanSchema.Builder().nullable(nullable).defaultValue(default).build()

                StandardPrimitive.Int ->
                    NumberSchema.Builder().requiresInteger(true).nullable(nullable).defaultValue(default).build()

                StandardPrimitive.Long ->
                    NumberSchema.Builder().requiresInteger(true).nullable(nullable).defaultValue(default).build()

                StandardPrimitive.String ->
                    StringSchema.Builder().nullable(nullable).defaultValue(default).build()

                StandardPrimitive.Double ->
                    NumberSchema.Builder().nullable(nullable).defaultValue(default).build()

                StandardPrimitive.Float ->
                    NumberSchema.Builder().nullable(nullable).defaultValue(default).build()
            }

        is Schema.Transform<*, *> ->
            @Suppress("unchecked_cast")
            (this.schema as Schema<A>).toJsonSchemaImpl(nullable = nullable, default = default)

        is Schema.Optional<*> ->
            @Suppress("unchecked_cast")
            (this.schema as Schema<A>).toJsonSchemaImpl(nullable = true, default = default)

        is Schema.StringMap<*> ->
            ObjectSchema.Builder().schemaOfAdditionalProperties(valueSchema.toJsonSchema()).build()

        is Schema.Collection<*> ->
            ArraySchema.Builder().addItemSchema(itemSchema.toJsonSchema()).build()

        is Schema.Union<*> ->
            CombinedSchema.Builder()
                .criterion(CombinedSchema.ONE_CRITERION)
                .subschemas(unsafeCases().map { it.schema.toJsonSchema() })
                .build()

        is Schema.Record<*> ->
            ObjectSchema.Builder()
                .let {
                    unsafeFields().fold(it) { builder, field ->
                        builder.addPropertySchema(field.name, field.schema.toJsonSchema())
                    }
                }
                .build()
    }
