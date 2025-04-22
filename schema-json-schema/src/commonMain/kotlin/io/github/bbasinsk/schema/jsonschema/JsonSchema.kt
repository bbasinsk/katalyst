package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.FieldMetadata
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.isRequired
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

// TODO: Probably a non-kotlinx encodeNull?
private val json = Json {
    explicitNulls = false
}

fun JsonSchema.encodeToJsonElement(): JsonElement =
    JsonSchema.schema.encodeToJsonElement(this, json)

fun JsonSchema.encodeToJsonString(): String =
    json.encodeToString(encodeToJsonElement())

data class JsonSchema(
    val type: String? = null,
    val description: String? = null,
    val enum: List<String>? = null,
    val items: JsonSchema? = null,
    val properties: Map<String, JsonSchema>? = null,
    val additionalProperties: JsonSchema? = null,
    val oneOf: List<JsonSchema>? = null,
    val required: List<String>? = null,
    val const: String? = null,
) {
    companion object {
        val schema: Schema<JsonSchema> = with(Schema.Companion) {
            record(
                field(string().optional(), "type") { type },
                field(string().optional(), "description") { description },
                field(list(string()).optional(), "enum") { enum },
                field(lazy { schema }.optional(), "items") { items },
                field(stringMap(lazy { schema }).optional(), "properties") { properties },
                field(lazy { schema }.optional(), "additionalProperties") { additionalProperties },
                field(list(lazy { schema }).optional(), "oneOf") { oneOf },
                field(list(string()).optional(), "required") { required },
                field(string().optional(), "const") { const },
                ::JsonSchema
            )
        }
    }
}

fun Schema<*>.toJsonSchema(): JsonSchema =
    toJsonSchemaImpl(FieldMetadata())

private fun <A> Schema<A>.toJsonSchemaImpl(metadata: FieldMetadata): JsonSchema {
    return when (this) {
        is Schema.Empty -> JsonSchema(type = "null", description = metadata.description)
        is Schema.Bytes -> JsonSchema(type = "string", description = metadata.description)
        // contentEncoding?
//            .defaultValue(default)

        is Schema.Lazy -> this.schema().toJsonSchemaImpl(metadata)
        is Schema.Metadata -> this.schema.toJsonSchemaImpl(this.metadata)

        is Schema.Default -> this.schema.toJsonSchemaImpl(metadata)
        is Schema.OrElse<A, *> -> this.preferred.toJsonSchemaImpl(metadata)

        is Schema.Primitive ->
            when (this) {
                is Schema.Primitive.Boolean -> JsonSchema(type = "boolean", description = metadata.description)
                is Schema.Primitive.Double -> JsonSchema(type = "number", description = metadata.description)
                is Schema.Primitive.Float -> JsonSchema(type = "number", description = metadata.description)
                is Schema.Primitive.Int -> JsonSchema(type = "integer", description = metadata.description)
                is Schema.Primitive.Long -> JsonSchema(type = "integer", description = metadata.description)
                is Schema.Primitive.String -> JsonSchema(type = "string", description = metadata.description)
                is Schema.Primitive.Enumeration<*> ->
                    JsonSchema(type = "string", enum = values.map { it.toString() }, description = metadata.description)
            }

        is Schema.Transform<*, *> -> schema.toJsonSchemaImpl(metadata)
        is Schema.Optional<*> -> schema.toJsonSchemaImpl(metadata)

        is Schema.Collection<*> -> JsonSchema(
            type = "array",
            items = itemSchema.toJsonSchemaImpl(metadata)
        )

        is Schema.StringMap<*> -> JsonSchema(
            type = "object",
            additionalProperties = valueSchema.toJsonSchemaImpl(metadata)
        )

        is Schema.Union<*> -> JsonSchema(
            type = "object",
            oneOf = unsafeCases().map { it to it.schema.toJsonSchemaImpl(FieldMetadata()) }.map { (case, jsonSchema) ->
                jsonSchema.copy(properties = jsonSchema.properties?.plus(key to JsonSchema(const = case.name)))
            }
        )

        is Schema.Record<*> -> JsonSchema(
            type = "object",
            properties = unsafeFields().associate { it.name to it.schema.toJsonSchemaImpl(FieldMetadata()) },
            required = unsafeFields().filter { it.schema.isRequired() }.map { it.name }
        )
    }
}
