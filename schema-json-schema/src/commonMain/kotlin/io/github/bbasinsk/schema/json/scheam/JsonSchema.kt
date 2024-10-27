package io.github.bbasinsk.schema.json.scheam

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

// TODO: Probably a non-kotlinx encodeNull?
private val json = Json {
    encodeDefaults = false
}

fun JsonSchema.encodeToJsonElement(): JsonElement =
    Schema.jsonSchema().encodeToJsonElement(this, json)

fun JsonSchema.encodeToJsonString(): String =
    json.encodeToString(encodeToJsonElement())

sealed interface JsonSchema {

    data class NullSchema(
        val stuff: Nothing? = null
    ) : JsonSchema {
        companion object {
            val schema: Schema<NullSchema> = with(Schema.Companion) {
                record(
                    field("stuff", empty()) { it.stuff },
                    ::NullSchema
                )
            }
        }
    }

    data class IntegerSchema(
        val stuff: Nothing? = null
    ) : JsonSchema {
        companion object {
            val schema: Schema<IntegerSchema> = with(Schema.Companion) {
                record(
                    field("stuff", empty()) { it.stuff },
                    ::IntegerSchema
                )
            }
        }
    }

    data class NumberSchema(
        val stuff: Nothing? = null
    ) : JsonSchema {
        companion object {
            val schema: Schema<NumberSchema> = with(Schema.Companion) {
                record(
                    field("stuff", empty()) { it.stuff },
                    ::NumberSchema
                )
            }
        }
    }

    data class StringSchema(
        val enum: List<String>? = null
    ) : JsonSchema {
        companion object {
            val schema: Schema<StringSchema> = with(Schema.Companion) {
                record(
                    field("enum", list(string()).optional().default(null)) { it.enum },
                    ::StringSchema
                )
            }
        }
    }

    data class BooleanSchema(
        val stuff: Nothing? = null
    ) : JsonSchema {
        companion object {
            val schema: Schema<BooleanSchema> = with(Schema.Companion) {
                record(
                    field("stuff", empty()) { it.stuff },
                    ::BooleanSchema
                )
            }
        }
    }

    data class ArraySchema(
        val items: JsonSchema
    ) : JsonSchema {
        companion object {
            val schema: Schema<ArraySchema> = with(Schema.Companion) {
                record(
                    field("items", jsonSchema()) { it.items },
                    ::ArraySchema
                )
            }
        }
    }

    data class ObjectSchema(
        val properties: Map<String, JsonSchema>? = null,
        val additionalProperties: JsonSchema? = null,
        val oneOf: List<JsonSchema>? = null,
    ) : JsonSchema {
        companion object {
            val schema: Schema<ObjectSchema> = with(Schema.Companion) {
                record(
                    field("properties", stringMap(lazy { jsonSchema() }).optional()) { it.properties },
                    field("additionalProperties", lazy { jsonSchema() }.optional()) { it.additionalProperties },
                    field("oneOf", list(lazy { jsonSchema() }).optional()) { it.oneOf },
                    ::ObjectSchema
                )
            }
        }
    }
}

fun Schema.Companion.jsonSchema(): Schema<JsonSchema> =
    lazy {
        union(
            case(JsonSchema.NullSchema.schema, name = "null"),
            case(JsonSchema.ObjectSchema.schema, name = "object"),
            case(JsonSchema.StringSchema.schema, name = "string"),
            case(JsonSchema.NumberSchema.schema, name = "number"),
            case(JsonSchema.IntegerSchema.schema, name = "integer"),
            case(JsonSchema.BooleanSchema.schema, name = "boolean"),
            case(JsonSchema.ArraySchema.schema, name = "array"),
            key = "type"
        )
    }

fun Schema<*>.toJsonSchema(): JsonSchema =
    toJsonSchemaImpl()

private fun <A> Schema<A>.toJsonSchemaImpl(): JsonSchema {
    val s = when (this) {
        is Schema.Empty -> JsonSchema.NullSchema()

        is Schema.Bytes -> JsonSchema.StringSchema()
        // contentEncoding?
//            .defaultValue(default)

        is Schema.Enumeration -> JsonSchema.StringSchema(
            enum = values.map { it.toString() }
        )

        is Schema.Lazy -> this.schema().toJsonSchemaImpl()
        is Schema.Default -> this.schema.toJsonSchemaImpl()
        is Schema.OrElse -> this.preferred.toJsonSchemaImpl()

        is Schema.Primitive ->
            when (primitive) {
                StandardPrimitive.Boolean -> JsonSchema.BooleanSchema()
                StandardPrimitive.Int -> JsonSchema.IntegerSchema()
                StandardPrimitive.Long -> JsonSchema.IntegerSchema()
                StandardPrimitive.String -> JsonSchema.StringSchema()
                StandardPrimitive.Double -> JsonSchema.NumberSchema()
                StandardPrimitive.Float -> JsonSchema.NumberSchema()
            }

        is Schema.Transform<*, *> ->
            @Suppress("unchecked_cast")
            (this.schema as Schema<A>).toJsonSchemaImpl()

        is Schema.Optional<*> ->
            @Suppress("unchecked_cast")
            (this.schema as Schema<A>).toJsonSchemaImpl()

        is Schema.Collection<*> ->
            JsonSchema.ArraySchema(itemSchema.toJsonSchemaImpl())

        is Schema.StringMap<*> ->
            JsonSchema.ObjectSchema(
                additionalProperties = valueSchema.toJsonSchemaImpl()
            )

        is Schema.Union<*> ->
            JsonSchema.ObjectSchema(
                oneOf = unsafeCases().map { it.schema.toJsonSchemaImpl() }
            )

        is Schema.Record<*> ->
            JsonSchema.ObjectSchema(
                properties = unsafeFields().associate { it.name to it.schema.toJsonSchemaImpl() }
            )
    }

    return s
}
