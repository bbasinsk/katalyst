package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Primitive
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

// TODO: Probably a non-kotlinx encodeNull?
private val json = Json {
    explicitNulls = false
}

fun JsonSchema.encodeToJsonElement(): JsonElement =
    Json.encodeToJsonElement(this)

fun JsonSchema.encodeToJsonString(): String =
    json.encodeToString(encodeToJsonElement())

@Serializable
data class JsonSchema(
    val type: String? = null,
    val description: String? = null,
    val enum: List<String>? = null,
    val format: String? = null,
    val contentEncoding: String? = null,
    val items: JsonSchema? = null,
    val properties: Map<String, JsonSchema>? = null,
//    val additionalProperties: JsonSchema? = null,
    val additionalProperties: Boolean? = null,
    val anyOf: List<JsonSchema>? = null,
    val oneOf: List<JsonSchema>? = null,
    val required: List<String>? = null,
    val const: String? = null,
    @SerialName("${'$'}ref") val ref: String? = null,
    @SerialName("${'$'}defs") val defs: Map<String, JsonSchema>? = null
)

data class JsonOptions(
    val description: String? = null,
    val optional: Boolean = false,
    val unionKey: Pair<String, JsonSchema>? = null
)

fun Schema<*>.toJsonSchema(): JsonSchema {
    val definitions = mutableMapOf<String, JsonSchema>()
    val schema = toJsonSchemaImpl(JsonOptions(), definitions, inlineRefs = true)
    return schema.copy(defs = definitions.takeIf { it.isNotEmpty() })
}

private fun String.typeOrNull(metadata: JsonOptions): String =
    this
//    listOfNotNull(this, "null".takeIf { metadata.optional })

private fun <A> Schema<A>.toJsonSchemaImpl(
    options: JsonOptions,
    definitions: MutableMap<String, JsonSchema>,
    inlineRefs: Boolean,
): JsonSchema {
    return when (this) {
        is Schema.Empty -> JsonSchema(type = "null", description = options.description)
        is Schema.Bytes -> JsonSchema(type = "string".typeOrNull(options), contentEncoding = "base64", description = options.description)

        is Schema.Lazy -> this.schema().toJsonSchemaImpl(options, definitions, inlineRefs)
        is Schema.Metadata -> this.schema.toJsonSchemaImpl(options.copy(description = this.metadata.description), definitions, inlineRefs)

        is Schema.Default -> this.schema.toJsonSchemaImpl(options, definitions, inlineRefs)
        is Schema.OrElse<A, *> -> this.preferred.toJsonSchemaImpl(options, definitions, inlineRefs)

        is Primitive ->
            when (this) {
                is Primitive.Boolean -> JsonSchema(type = "boolean".typeOrNull(options), description = options.description)
                is Primitive.Double -> JsonSchema(type = "number".typeOrNull(options), description = options.description)
                is Primitive.Float -> JsonSchema(type = "number".typeOrNull(options), description = options.description)
                is Primitive.Int -> JsonSchema(type = "integer".typeOrNull(options), description = options.description)
                is Primitive.Long -> JsonSchema(type = "integer".typeOrNull(options), description = options.description)
                is Primitive.String -> JsonSchema(type = "string".typeOrNull(options), description = options.description)
                is Primitive.Enumeration<*> -> JsonSchema(
                    type = "string".typeOrNull(options),
                    enum = values.map { it.toString() },
                    description = options.description
                )
            }

//        is Schema.Transform<*, *> -> when {
//            metadata.name.lowercase() == "uuid" && outputOptions.supportsStringFormat("uuid") ->
//                SchemaObject(type = "string", format = "uuid", nullable = field.nullable)
//
//            metadata.name.lowercase() == "localdate" && outputOptions.supportsStringFormat("date") ->
//                SchemaObject(type = "string", format = "date", nullable = field.nullable)
//
//            metadata.name.lowercase() == "instant" && outputOptions.supportsStringFormat("date-time") ->
//                SchemaObject(type = "string", format = "date-time", nullable = field.nullable)
//
//            else -> schema.toSchemaObjectImpl(field, outputOptions)
//        }

        is Schema.Transform<*, *> -> when {
//            metadata.name.lowercase() == "duration" ->
//                JsonSchema(type = "string".typeOrNull(options), format = "duration", description = options.description)
//
//            metadata.name.lowercase() == "uuid" ->
//                JsonSchema(type = "string".typeOrNull(options), format = "uuid", description = options.description)
//
//            metadata.name.lowercase() == "localdate" ->
//                JsonSchema(type = "string".typeOrNull(options), format = "date", description = options.description)
//
//            metadata.name.lowercase() == "instant" ->
//                JsonSchema(type = "string".typeOrNull(options), format = "date-time", description = options.description)

            else -> schema.toJsonSchemaImpl(options, definitions, inlineRefs)
        }

        is Schema.Optional<*> ->
            schema.toJsonSchemaImpl(options.copy(optional = true), definitions, inlineRefs)

        is Schema.Collection<*> -> JsonSchema(
            type = "array".typeOrNull(options),
            items = itemSchema.toJsonSchemaImpl(options, definitions, inlineRefs)
        )

        is Schema.StringMap<*> -> JsonSchema(
            type = "object".typeOrNull(options),
//            additionalProperties = valueSchema.toJsonSchemaImpl(metadata, definitions)
        )

        is Schema.Union<*> -> {
            val typeName = this.metadata.qualifiedName()
            if (!definitions.containsKey(typeName)) {
                definitions[typeName] = JsonSchema(type = "object".typeOrNull(options)) // temporary placeholder for recursive record
                val computedUnionSchema = JsonSchema(
//                    type = "object",
                    anyOf = unsafeCases().map { case ->
                        case.schema.toJsonSchemaImpl(
                            JsonOptions(unionKey = this.key to JsonSchema(enum = listOf(case.name))),
                            definitions,
                            inlineRefs = false,
                        )
                    }
                )
                definitions[typeName] = computedUnionSchema
            }
            val unionSchema = definitions[typeName]!!
            if (inlineRefs) unionSchema.also { definitions.remove(typeName) }
            else JsonSchema(ref = "#/${'$'}defs/$typeName")
        }

        is Schema.Record<*> -> {
            val typeName = this.metadata.qualifiedName()
            if (!definitions.containsKey(typeName)) {
                definitions[typeName] = JsonSchema(type = "object".typeOrNull(options)) // temporary placeholder for recursive records

                val unionKeyProperty = options.unionKey?.let { mapOf(it) } ?: emptyMap()
                val properties = unionKeyProperty + unsafeFields().associate { field ->
                    field.name to field.schema.toJsonSchemaImpl(JsonOptions(), definitions, inlineRefs = false)
                }

                val computedRecordSchema = JsonSchema(
                    type = "object".typeOrNull(options),
                    properties = properties,
                    required = properties
//                        .filter { it.schema.isRequired() }
                        .map { it.key },
                    additionalProperties = false,
                    description = options.description
                )
                definitions[typeName] = computedRecordSchema
            }
            val recordSchema = definitions[typeName]!!
            if (inlineRefs) recordSchema.also { definitions.remove(typeName) }
            else JsonSchema(ref = "#/${'$'}defs/$typeName")
        }
    }
}
