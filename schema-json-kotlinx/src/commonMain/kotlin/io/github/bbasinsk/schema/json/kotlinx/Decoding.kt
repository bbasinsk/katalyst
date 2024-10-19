package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.Segment
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.Validation.Invalid
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.mapInvalid
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
// why is kotlin/gradle letting me import/use this?
import java.util.Base64 // TODO: multiplatform base64?

fun <A> Schema<A>.decodeFromJsonString(str: String, json: Json = Json.Default): Validation<InvalidJson, A> =
    decodeFromJsonElement(
        str.takeIf { it.isNotBlank() }?.let { json.parseToJsonElement(it) } ?: JsonNull
    )

fun <A> Schema<A>.decodeFromJsonElement(json: JsonElement): Validation<InvalidJson, A> =
    Validation.decode(schema = this, json = json, path = listOf())

@Suppress("UNCHECKED_CAST")
private fun <A> Validation.Companion.decode(
    schema: Schema<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> =
    when (schema) {
        is Schema.Empty -> valid(null as A)
        is Schema.Bytes -> decodeBytes(json, path) as Validation<InvalidJson, A>
        is Schema.Lazy<A> -> decode(schema.schema(), json, path)
        is Schema.Primitive -> decodePrimitive(schema, json, path)
        is Schema.Enumeration -> decodeEnumeration(schema, json, path)
        is Schema.Default -> decodeDefault(schema, json, path)
        is Schema.Optional<*> -> decodeOptional(schema, json, path) as Validation<InvalidJson, A>
        is Schema.OrElse -> decode(schema.preferred, json, path).orElse { errors ->
            decode(schema.fallback, json, path).orElse { Invalid(errors) }
        }
        is Schema.Transform<A, *> -> decodeTransform(schema, json, path)
        is Schema.Collection<*> -> decodeList(schema, json, path) as Validation<InvalidJson, A>
        is Schema.StringMap<*> -> stringMap(schema, json, path) as Validation<InvalidJson, A>
        is Schema.Record<*> -> decodeRecord(schema as Schema.Record<A>, json, path)
        is Schema.Union<*> -> decodeUnion(schema as Schema.Union<A>, json, path)
    }

private fun <A> Validation.Companion.decodePrimitive(
    schema: Schema.Primitive<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> {
    val error = InvalidJson(schema.primitive.javaClass.simpleName, json.toString(), path)
    return if (json is JsonNull) {
        invalid(error)
    } else {
        runCatching { json.jsonPrimitive }
            .mapInvalid { error }
            .andThen { if (it.isString != schema.isJsonString()) invalid(error) else valid(it) }
            .andThen { schema.primitive.parse(it.content)?.let { value -> valid(value) } ?: invalid(error) }
    }
}

private fun Validation.Companion.decodeBytes(
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, ByteArray> =
    runCatching { json.jsonPrimitive.content }
        .andThen { runCatching { Base64.getDecoder().decode(it) } }
        .mapInvalid { InvalidJson("base64 encoded", json.toString(), path) }

private fun <A> Schema.Primitive<A>.isJsonString(): Boolean =
    when (this.primitive) {
        StandardPrimitive.String -> true
        StandardPrimitive.Boolean -> false
        StandardPrimitive.Double -> false
        StandardPrimitive.Float -> false
        StandardPrimitive.Int -> false
        StandardPrimitive.Long -> false
    }

private fun <A> Validation.Companion.decodeOptional(
    schema: Schema.Optional<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A?> =
    if (json is JsonNull) valid(null) else decode(schema.schema, json, path)

private fun <A> Validation.Companion.decodeDefault(
    schema: Schema.Default<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> =
    if (json is JsonNull) valid(schema.default) else decode(schema.schema, json, path)

private fun <A, B> Validation.Companion.decodeTransform(
    schema: Schema.Transform<A, B>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> =
    decode(schema.schema, json, path)
        .andThen { b ->
            schema.decode(b).fold(
                { valid(it) },
                { e -> invalid(InvalidJson(schema.metadata.name, """"${e.message ?: "unknown error"}"""", path)) }
            )
        }

private fun <A> Validation.Companion.decodeEnumeration(
    schema: Schema.Enumeration<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> =
    runCatching { json.jsonPrimitive.content }
        .mapInvalid { InvalidJson("JsonPrimitive", json.javaClass.simpleName, path) }
        .andThen { jsonStr ->
            schema.values.find { it.toString() == jsonStr }
                ?.let { valid(it) }
                ?: invalid(InvalidJson(schema.values.joinToListString(), jsonStr, path))
        }

private fun <A> Validation.Companion.decodeList(
    schema: Schema.Collection<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, List<A>> =
    runCatching { json.jsonArray }
        .mapInvalid { InvalidJson("JsonArray", json.javaClass.simpleName, path) }
        .andThen {
            sequence(
                it.mapIndexed { i, json -> decode(schema.itemSchema, json, path + Segment.Index(i)) }
            )
        }

private fun <V> Validation.Companion.stringMap(
    schema: Schema.StringMap<V>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, Map<String, V>> =
    runCatching { json.jsonObject }
        .mapInvalid { InvalidJson("JsonObject", json.javaClass.simpleName, path) }.andThen { jsonObject ->
            sequence(
                jsonObject.entries.map { (key, value) ->
                    decode(schema.valueSchema, value, path + Segment.Field(key)).mapValid { key to it }
                }
            ).mapValid { it.toMap() }
        }

private fun <A> Validation.Companion.decodeRecord(
    schema: Schema.Record<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> =
    runCatching { json.jsonObject }
        .mapInvalid { InvalidJson("JsonObject", json.javaClass.simpleName, path) }
        .andThen { jsonObject ->
            sequence(
                schema.unsafeFields().map {
                    decode(it.schema, jsonObject[it.name] ?: JsonNull, path + Segment.Field(it.name))
                }
            )
        }
        .mapValid { schema.unsafeConstruct(it) }

private fun <A> Validation.Companion.decodeUnion(
    schema: Schema.Union<A>,
    json: JsonElement,
    path: List<Segment>
): Validation<InvalidJson, A> =
    @Suppress("UNCHECKED_CAST")
    runCatching { json.jsonObject }
        .mapInvalid { InvalidJson("JsonObject", json.javaClass.simpleName, path) }
        .andThen { decode(Schema.string(), it[schema.key] ?: JsonNull, path + Segment.Field(schema.key)) }
        .andThen { identifier ->
            requireNotNull(schema.unsafeCases().find { it.name == identifier }) {
                InvalidJson(
                    expected = schema.unsafeCases().map { it.name }.joinToListString(),
                    found = identifier,
                    path = path + Segment.Field(schema.key)
                )
            }
        }.andThen { case -> decode(case.schema as Schema<A>, json, path) }

private fun <A> List<A>.joinToListString(): String =
    joinToString(", ", "[", "]")
