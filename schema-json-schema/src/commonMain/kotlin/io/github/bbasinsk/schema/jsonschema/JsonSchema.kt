package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.DefinitionNameResolver
import io.github.bbasinsk.schema.ObjectMetadata
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
    @Serializable(with = TypeListSerializer::class) val type: List<String>? = null,
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
    val resolver = DefinitionNameResolver()
    val schema = toJsonSchemaImpl(JsonOptions(), definitions, inlineRefs = true, resolver)
    return schema.copy(defs = definitions.takeIf { it.isNotEmpty() })
}

private fun String.typeOrNull(metadata: JsonOptions): List<String> =
    listOfNotNull(this, "null".takeIf { metadata.optional })

private fun JsonSchema.orNull(metadata: JsonOptions): JsonSchema =
    if (metadata.optional) {
        copy(
            type = null,
            anyOf = listOf(
                JsonSchema(type = this.type),
                JsonSchema(type = listOf("null")),
            )
        )
    } else {
        this
    }

private fun <A> Schema<A>.toJsonSchemaImpl(
    options: JsonOptions,
    definitions: MutableMap<String, JsonSchema>,
    inlineRefs: Boolean,
    resolver: DefinitionNameResolver,
): JsonSchema {
    return when (this) {
        is Schema.Empty -> JsonSchema(type = listOf("null"), description = options.description)
        is Schema.Bytes -> JsonSchema(type = listOf("string"), contentEncoding = "base64", description = options.description).orNull(options)

        is Schema.Lazy -> this.schema().toJsonSchemaImpl(options, definitions, inlineRefs, resolver)
        is Schema.Metadata -> this.schema.toJsonSchemaImpl(options.copy(description = this.metadata.description), definitions, inlineRefs, resolver)

        is Schema.Default -> this.schema.toJsonSchemaImpl(options, definitions, inlineRefs, resolver)
        is Schema.OrElse<A, *> -> this.preferred.toJsonSchemaImpl(options, definitions, inlineRefs, resolver)

        is Primitive ->
            when (this) {
                is Primitive.Boolean -> JsonSchema(type = listOf("boolean"), description = options.description).orNull(options)
                is Primitive.Double -> JsonSchema(type = listOf("number"), description = options.description).orNull(options)
                is Primitive.Float -> JsonSchema(type = listOf("number"), description = options.description).orNull(options)
                is Primitive.Int -> JsonSchema(type = listOf("integer"), description = options.description).orNull(options)
                is Primitive.Long -> JsonSchema(type = listOf("integer"), description = options.description).orNull(options)
                is Primitive.String -> JsonSchema(type = listOf("string"), description = options.description).orNull(options)
                is Primitive.Enumeration<*> -> JsonSchema(
                    type = listOf("string"),
                    enum = values.map { it.toString() },
                    description = options.description
                ).orNull(options)
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

            else -> schema.toJsonSchemaImpl(options, definitions, inlineRefs, resolver)
        }

        is Schema.Optional<*> ->
            schema.toJsonSchemaImpl(options.copy(optional = true), definitions, inlineRefs, resolver)

        is Schema.Collection<*> -> JsonSchema(
            type = listOf("array"),
            items = itemSchema.toJsonSchemaImpl(options, definitions, inlineRefs, resolver)
        ).orNull(options)

        is Schema.StringMap<*> -> JsonSchema(
            type = listOf("object"),
//            additionalProperties = valueSchema.toJsonSchemaImpl(metadata, definitions)
        ).orNull(options)

        is Schema.Union<*> -> {
            val typeName = resolver.resolve(this, this.metadata)
            if (!definitions.containsKey(typeName)) {
                definitions[typeName] = JsonSchema(type = listOf("object")).orNull(options) // temporary placeholder for recursive record
                val computedUnionSchema = JsonSchema(
//                    type = "object",
                    anyOf = unsafeCases().map { case ->
                        case.schema.toJsonSchemaImpl(
                            JsonOptions(unionKey = this.key to JsonSchema(enum = listOf(case.name))),
                            definitions,
                            inlineRefs = true,
                            resolver = resolver,
                        )
                    }.plus(listOfNotNull(JsonSchema(type = listOf("null")).takeIf { options.optional }))
                )
                definitions[typeName] = computedUnionSchema
            }
            val unionSchema = definitions[typeName]!!
            return if (inlineRefs) unionSchema.also { definitions.remove(typeName) } else JsonSchema(ref = "#/${'$'}defs/$typeName")
        }

        is Schema.Record<*> -> {
            val typeName = resolver.resolve(this, this.metadata)
            if (!definitions.containsKey(typeName)) {
                definitions[typeName] = JsonSchema(type = listOf("object")).orNull(options) // temporary placeholder for recursive records

                val unionKeyProperty = options.unionKey?.let { mapOf(it) } ?: emptyMap()
                val properties = unionKeyProperty + unsafeFields().associate { field ->
                    field.name to field.schema.toJsonSchemaImpl(JsonOptions(), definitions, inlineRefs = false, resolver = resolver)
                }

                val computedRecordSchema = JsonSchema(
                    type = listOf("object"),
                    properties = properties,
                    required = properties
//                        .filter { it.schema.isRequired() }
                        .map { it.key },
                    additionalProperties = false,
                    description = options.description
                ).orNull(options)
                definitions[typeName] = computedRecordSchema
            }
            val recordSchema = definitions[typeName]!!
            return if (inlineRefs) recordSchema.also { definitions.remove(typeName) } else JsonSchema(ref = "#/${'$'}defs/$typeName")
        }
    }
}
