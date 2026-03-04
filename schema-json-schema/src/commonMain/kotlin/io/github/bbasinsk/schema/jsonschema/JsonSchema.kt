package io.github.bbasinsk.schema.jsonschema

import io.github.bbasinsk.schema.DefinitionNameResolver
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
    @SerialName($$"$ref") val ref: String? = null,
    @SerialName($$"$defs") val defs: Map<String, JsonSchema>? = null
)

data class JsonOptions(
    val description: String? = null,
    val optional: Boolean = false,
    val unionKey: Pair<String, JsonSchema>? = null
)

fun Schema<*>.toJsonSchema(maxRecursionDepth: Int? = null): JsonSchema {
    require(maxRecursionDepth == null || maxRecursionDepth >= 0) { "maxRecursionDepth must be non-negative, got $maxRecursionDepth" }
    val definitions = mutableMapOf<String, JsonSchema>()
    val resolver = DefinitionNameResolver()
    val unrollState = maxRecursionDepth?.let { UnrollState(maxDepth = it) }
    val schema = toJsonSchemaImpl(JsonOptions(), definitions, inlineRefs = true, resolver, unrollState)
    return schema.copy(defs = definitions.takeIf { it.isNotEmpty() })
}

private fun JsonSchema.orNull(metadata: JsonOptions): JsonSchema = orNullType(metadata)

// Was previously used for maybe OpenAI to support nullable objects
private fun JsonSchema.orNullAnyOf(metadata: JsonOptions): JsonSchema =
    when {
        metadata.optional -> copy(
            type = null,
            anyOf = listOf(JsonSchema(type = type), JsonSchema(type = listOf("null")))
        )

        else -> this
    }

private fun JsonSchema.orNullType(metadata: JsonOptions): JsonSchema =
    when {
        metadata.optional -> copy(type = type?.plus("null"))
        else -> this
    }

// Identity-based collections using === (needed because Schema types are data classes)
private class IdentitySet<T> {
    private val items = mutableListOf<T>()
    fun contains(item: T): Boolean = items.any { it === item }
    fun add(item: T): Boolean = if (contains(item)) false else { items.add(item); true }
}

private class IdentityMap<K, V> {
    private val entries = mutableListOf<kotlin.Pair<K, V>>()
    operator fun get(key: K): V? = entries.firstOrNull { it.first === key }?.second
    operator fun set(key: K, value: V) {
        entries.removeAll { it.first === key }
        entries.add(key to value)
    }
    fun remove(key: K) { entries.removeAll { it.first === key } }
}

private fun containsReference(schema: Schema<*>, target: Schema.Union<*>, visited: IdentitySet<Schema<*>> = IdentitySet()): Boolean {
    if (!visited.add(schema)) return false
    return when (schema) {
        is Schema.Lazy -> containsReference(schema.schema(), target, visited)
        is Schema.Union<*> -> schema === target || schema.unsafeCases().any { containsReference(it.schema, target, visited) }
        is Schema.Record<*> -> schema.unsafeFields().any { containsReference(it.schema, target, visited) }
        is Schema.Collection<*> -> containsReference(schema.itemSchema, target, visited)
        is Schema.Optional<*> -> containsReference(schema.schema, target, visited)
        is Schema.Metadata<*> -> containsReference(schema.schema, target, visited)
        is Schema.Default<*> -> containsReference(schema.schema, target, visited)
        is Schema.Transform<*, *> -> containsReference(schema.schema, target, visited)
        is Schema.OrElse<*, *> -> containsReference(schema.preferred, target, visited)
        is Schema.StringMap<*> -> containsReference(schema.valueSchema, target, visited)
        is Schema.Empty, is Schema.Bytes, is Schema.Dynamic, is Schema.Primitive -> false
    }
}

private class UnrollState(
    val maxDepth: Int,
    val refTargets: IdentityMap<Schema.Union<*>, String> = IdentityMap(),
    val completedUnions: IdentitySet<Schema.Union<*>> = IdentitySet(),
)

private fun <A> Schema<A>.toJsonSchemaImpl(
    options: JsonOptions,
    definitions: MutableMap<String, JsonSchema>,
    inlineRefs: Boolean,
    resolver: DefinitionNameResolver,
    unrollState: UnrollState? = null,
): JsonSchema {
    return when (this) {
        is Schema.Empty -> JsonSchema(type = listOf("null"), description = options.description)
        is Schema.Dynamic -> JsonSchema(description = options.description)
        is Schema.Bytes -> JsonSchema(type = listOf("string"), contentEncoding = "base64", description = options.description).orNull(options)

        is Schema.Lazy -> this.schema().toJsonSchemaImpl(options, definitions, inlineRefs, resolver, unrollState)
        is Schema.Metadata -> this.schema.toJsonSchemaImpl(options.copy(description = this.metadata.description), definitions, inlineRefs, resolver, unrollState)

        is Schema.Default -> this.schema.toJsonSchemaImpl(options, definitions, inlineRefs, resolver, unrollState)
        is Schema.OrElse<A, *> -> this.preferred.toJsonSchemaImpl(options, definitions, inlineRefs, resolver, unrollState)

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

        is Schema.Transform<*, *> -> schema.toJsonSchemaImpl(options, definitions, inlineRefs, resolver, unrollState)

        is Schema.Optional<*> ->
            schema.toJsonSchemaImpl(options.copy(optional = true), definitions, inlineRefs, resolver, unrollState)

        is Schema.Collection<*> -> JsonSchema(
            type = listOf("array"),
            items = itemSchema.toJsonSchemaImpl(options, definitions, inlineRefs, resolver, unrollState)
        ).orNull(options)

        is Schema.StringMap<*> -> JsonSchema(
            type = listOf("object"),
        ).orNull(options)

        is Schema.Union<*> -> {
            val typeName = resolver.resolve(this, this.metadata)

            if (unrollState != null) {
                fun refOrNullable(defName: String): JsonSchema {
                    val ref = JsonSchema(ref = "#/${'$'}defs/$defName")
                    return if (options.optional) JsonSchema(anyOf = listOf(ref, JsonSchema(type = listOf("null")))) else ref
                }

                // Back-reference: during level generation, recursive refs point one level down
                val refTarget = unrollState.refTargets[this]
                if (refTarget != null) {
                    return refOrNullable(refTarget)
                }

                // Already fully unrolled in a previous encounter
                if (unrollState.completedUnions.contains(this)) {
                    return refOrNullable("${typeName}_${unrollState.maxDepth}")
                }

                // Check if this union is actually recursive
                val isRecursive = unsafeCases().any { case -> containsReference(case.schema, this) }
                if (isRecursive) {
                    generateUnrolledLevels(this, typeName, definitions, resolver, unrollState)
                    unrollState.completedUnions.add(this)
                    return refOrNullable("${typeName}_${unrollState.maxDepth}")
                }
            }

            // Non-recursive or no unrolling: existing behavior
            if (!definitions.containsKey(typeName)) {
                definitions[typeName] = JsonSchema(type = listOf("object")).orNull(options)
                val computedUnionSchema = JsonSchema(
                    anyOf = unsafeCases().map { case ->
                        case.schema.toJsonSchemaImpl(
                            JsonOptions(unionKey = this.key to JsonSchema(enum = listOf(case.name))),
                            definitions,
                            inlineRefs = true,
                            resolver = resolver,
                            unrollState = unrollState,
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
                    field.name to field.schema.toJsonSchemaImpl(JsonOptions(), definitions, inlineRefs = false, resolver = resolver, unrollState = unrollState)
                }

                val computedRecordSchema = JsonSchema(
                    type = listOf("object"),
                    properties = properties,
                    required = properties
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

private fun generateUnrolledLevels(
    union: Schema.Union<*>,
    typeName: String,
    definitions: MutableMap<String, JsonSchema>,
    resolver: DefinitionNameResolver,
    unrollState: UnrollState,
) {
    val cases = union.unsafeCases()
    val terminalCases = cases.filter { !containsReference(it.schema, union) }
    require(terminalCases.isNotEmpty()) { "Union '$typeName' has no terminal (non-recursive) cases and cannot be unrolled" }

    for (depth in 0..unrollState.maxDepth) {
        val levelName = "${typeName}_$depth"
        val casesToUse = if (depth == 0) terminalCases else cases

        if (depth > 0) {
            unrollState.refTargets[union] = "${typeName}_${depth - 1}"
        }

        val levelSchema = JsonSchema(
            anyOf = casesToUse.map { case ->
                case.schema.toJsonSchemaImpl(
                    JsonOptions(unionKey = union.key to JsonSchema(enum = listOf(case.name))),
                    definitions,
                    inlineRefs = true,
                    resolver = resolver,
                    unrollState = unrollState,
                )
            }
        )
        definitions[levelName] = levelSchema
    }

    unrollState.refTargets.remove(union)
}
