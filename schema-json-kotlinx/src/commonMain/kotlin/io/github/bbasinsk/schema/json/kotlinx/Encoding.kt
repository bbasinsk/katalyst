@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import kotlinx.io.readByteArray
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.encodeToJsonBytes(value: A, json: Json = Json.Default): ByteArray =
    kotlinx.io.Buffer().also { encodeToSink(value, it, json.configuration.explicitNulls) }.readByteArray()

fun <A> Schema<A>.encodeToJsonString(value: A, json: Json = Json.Default): String =
    encodeToJsonBytes(value, json).decodeToString()

fun <A> Schema<A>.encodeToJsonElement(value: A, json: Json = Json.Default): JsonElement =
    when (this) {
        is Schema.Empty -> JsonNull
        is Schema.Bytes -> JsonPrimitive(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitive(value)
        is Schema.Lazy -> schema().encodeToJsonElement(value, json)
        is Schema.Metadata -> schema.encodeToJsonElement(value, json)
        is Schema.Optional<*> -> encodeOptional(value, json)
        is Schema.Default -> schema.encodeToJsonElement(value, json)
        is Schema.OrElse<A, *> -> preferred.encodeToJsonElement(value, json)
        is Schema.Transform<A, *> -> encodeTransform(value, json)
        is Schema.Collection<*> -> encodeList(value as List<*>, json)
        is Schema.StringMap<*> -> encodeStringMap(value as Map<*, *>, json)
        is Schema.Union<*> -> encodeUnion(value, json)
        is Schema.Record<*> -> encodeRecord(value, json)
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Primitive<A>.encodePrimitive(value: A): JsonPrimitive =
    when (this) {
        is Schema.Primitive.Boolean -> JsonPrimitive(value as Boolean)
        is Schema.Primitive.Double -> JsonPrimitive(value as Double)
        is Schema.Primitive.Float -> JsonPrimitive(value as Float)
        is Schema.Primitive.Int -> JsonPrimitive(value as Int)
        is Schema.Primitive.Long -> JsonPrimitive(value as Long)
        is Schema.Primitive.String -> JsonPrimitive(value as String)
        is Schema.Primitive.Enumeration<*> -> JsonPrimitive(value.toString())
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptional(value: Any?, json: Json): JsonElement =
    if (value == null) JsonNull else schema.encodeToJsonElement(value as A, json)

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
        this.unsafeFields().mapNotNull { field ->
            val schema = field.schema as Schema<Any?>
            val fieldValue = field.extract(value as A)
            val encodedValue = schema.encodeToJsonElement(fieldValue, json)

            if (!json.configuration.explicitNulls && fieldValue == null) {
                null
            } else {
                field.name to encodedValue
            }
        }.toMap()
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnion(value: Any?, json: Json): JsonElement {
    val case = unsafeCases().find { it.deconstruct(value as A) != null } ?: error("No case found for ${value as A}")
    val discriminator = mapOf(this.key to JsonPrimitive(case.name))
    val obj = (case.schema as Schema<Any?>).encodeToJsonElement(case.deconstruct(value as A)!!, json).jsonObject
    return JsonObject(discriminator.plus(obj))
}
