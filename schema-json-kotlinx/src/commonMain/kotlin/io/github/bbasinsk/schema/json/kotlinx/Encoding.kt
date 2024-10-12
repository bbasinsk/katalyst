package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import java.util.Base64 // TODO: multiplatform base64

fun <A> Schema<A>.encodeToJsonString(value: A, json: Json = Json.Default): String =
    json.encodeToString(encodeToJsonElement(value, json))

fun <A> Schema<A>.encodeToJsonElement(value: A, json: Json = Json.Default): JsonElement =
    when (this) {
        is Schema.Empty -> JsonNull
        is Schema.Bytes -> JsonPrimitive(Base64.getEncoder().encodeToString(value as ByteArray))
        is Schema.Primitive -> encodePrimitive(value)
        is Schema.Lazy -> with(Schema.Companion) { this.schema().encodeToJsonElement(value, json) }
        is Schema.Optional<*> -> encodeOptional(value, json)
        is Schema.Default -> schema.encodeToJsonElement(value, json)
        is Schema.OrElse -> preferred.encodeToJsonElement(value, json)
        is Schema.Transform<A, *> -> encodeTransform(value, json)
        is Schema.Enumeration -> JsonPrimitive(value.toString())
        is Schema.Collection<*> -> encodeList(value as List<*>, json)
        is Schema.StringMap<*> -> encodeStringMap(value as Map<*, *>, json)
        is Schema.Union<*> -> encodeUnion(value, json)
        is Schema.Record<*> -> encodeRecord(value, json)
    }

private fun <A> Schema.Primitive<A>.encodePrimitive(value: A): JsonPrimitive =
    when (this.primitive) {
        StandardPrimitive.Boolean -> JsonPrimitive(value as Boolean)
        StandardPrimitive.Double -> JsonPrimitive(value as Double)
        StandardPrimitive.Float -> JsonPrimitive(value as Float)
        StandardPrimitive.Int -> JsonPrimitive(value as Int)
        StandardPrimitive.Long -> JsonPrimitive(value as Long)
        StandardPrimitive.String -> JsonPrimitive(value as String)
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptional(value: Any?, json: Json): JsonElement =
    if (value == null) {
        JsonNull
    } else {
        this.schema.encodeToJsonElement(value as A, json)
    }

private fun <A, B> Schema.Transform<A, B>.encodeTransform(value: A, json: Json): JsonElement =
    this.schema.encodeToJsonElement(this.encode(value), json)

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeList(value: List<Any?>, json: Json): JsonArray =
    JsonArray(value.map { this.itemSchema.encodeToJsonElement(it as A, json) })

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMap(value: Map<*, *>, json: Json): JsonObject =
    JsonObject(
        value.mapKeys { it.key as String }
            .mapValues { this.valueSchema.encodeToJsonElement(it.value as V, json) }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecord(value: Any?, json: Json): JsonElement =
    JsonObject(
        this.unsafeFields().associate { field ->
            field.name to (field.schema as Schema<Any?>).encodeToJsonElement(field.extract(value as A), json)
        }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnion(value: Any?, json: Json): JsonElement {
    val case = this.unsafeCases().find { it.deconstruct(value as A) != null } ?: error("No case found for ${value as A}")
    val discriminator = mapOf(this.key to JsonPrimitive(case.name))
    val obj = (case.schema as Schema<Any?>).encodeToJsonElement(case.deconstruct(value as A)!!, json).jsonObject
    return JsonObject(discriminator.plus(obj))
}
