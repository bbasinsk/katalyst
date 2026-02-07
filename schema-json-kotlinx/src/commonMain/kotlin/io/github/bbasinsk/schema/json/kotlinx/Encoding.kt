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
    encodeToJsonBytes(value, json.toEncodingConfig())

fun <A> Schema<A>.encodeToJsonBytes(value: A, config: JsonEncodingConfig): ByteArray =
    kotlinx.io.Buffer().also { encodeToSink(value, it, config) }.readByteArray()

fun <A> Schema<A>.encodeToJsonString(value: A, json: Json = Json.Default): String =
    encodeToJsonString(value, json.toEncodingConfig())

fun <A> Schema<A>.encodeToJsonString(value: A, config: JsonEncodingConfig): String =
    encodeToJsonBytes(value, config).decodeToString()

fun <A> Schema<A>.encodeToJsonElement(value: A, json: Json = Json.Default): JsonElement =
    encodeToJsonElement(value, json.toEncodingConfig())

fun <A> Schema<A>.encodeToJsonElement(value: A, config: JsonEncodingConfig): JsonElement =
    when (this) {
        is Schema.Empty -> JsonNull
        is Schema.Bytes -> JsonPrimitive(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitive(value)
        is Schema.Lazy -> schema().encodeToJsonElement(value, config)
        is Schema.Metadata -> schema.encodeToJsonElement(value, config)
        is Schema.Optional<*> -> encodeOptional(value, config)
        is Schema.Default -> schema.encodeToJsonElement(value, config)
        is Schema.OrElse<A, *> -> preferred.encodeToJsonElement(value, config)
        is Schema.Transform<A, *> -> encodeTransform(value, config)
        is Schema.Collection<*> -> encodeList(value as List<*>, config)
        is Schema.StringMap<*> -> encodeStringMap(value as Map<*, *>, config)
        is Schema.Union<*> -> encodeUnion(value, config)
        is Schema.Record<*> -> encodeRecord(value, config)
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
private fun <A> Schema.Optional<A>.encodeOptional(value: Any?, config: JsonEncodingConfig): JsonElement =
    if (value == null) JsonNull else schema.encodeToJsonElement(value as A, config)

private fun <A, B> Schema.Transform<A, B>.encodeTransform(value: A, config: JsonEncodingConfig): JsonElement =
    this.schema.encodeToJsonElement(this.encode(value), config)

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeList(value: List<Any?>, config: JsonEncodingConfig): JsonArray =
    JsonArray(value.map { this.itemSchema.encodeToJsonElement(it as A, config) })

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMap(value: Map<*, *>, config: JsonEncodingConfig): JsonObject =
    JsonObject(
        value.mapKeys { it.key as String }
            .mapValues { this.valueSchema.encodeToJsonElement(it.value as V, config) }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecord(value: Any?, config: JsonEncodingConfig): JsonElement =
    JsonObject(
        this.unsafeFields().mapNotNull { field ->
            val schema = field.schema as Schema<Any?>
            val fieldValue = field.extract(value as A)
            val encodedValue = schema.encodeToJsonElement(fieldValue, config)

            if (!config.explicitNulls && fieldValue == null) {
                null
            } else {
                field.name to encodedValue
            }
        }.toMap()
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnion(value: Any?, config: JsonEncodingConfig): JsonElement {
    val (case, caseValue) = unsafeCases()
        .firstNotNullOfOrNull { case -> case.deconstruct(value as A)?.let { case to it } }
        ?: error("No case found for ${value as A}")
    val discriminator = mapOf(this.key to JsonPrimitive(case.name))
    val obj = (case.schema as Schema<Any?>).encodeToJsonElement(caseValue, config).jsonObject
    return JsonObject(discriminator.plus(obj))
}
