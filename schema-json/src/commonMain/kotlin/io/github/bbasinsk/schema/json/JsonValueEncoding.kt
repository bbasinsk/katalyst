@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.JsonValue
import io.github.bbasinsk.schema.Schema
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Deprecated("Use encodeToJsonValue instead", ReplaceWith("encodeToJsonValue(value, config)"))
fun <A> Schema<A>.encodeToSchemaValue(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): JsonValue =
    encodeToJsonValue(value, config)

fun <A> Schema<A>.encodeToJsonValue(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): JsonValue =
    when (this) {
        is Schema.Empty -> JsonValue.Null
        is Schema.Dynamic -> value as JsonValue
        is Schema.Bytes -> JsonValue.Str(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitiveToJsonValue(value)
        is Schema.Lazy -> schema().encodeToJsonValue(value, config)
        is Schema.Metadata -> schema.encodeToJsonValue(value, config)
        is Schema.Optional<*> -> encodeOptionalToJsonValue(value, config)
        is Schema.Default -> schema.encodeToJsonValue(value, config)
        is Schema.OrElse<A, *> -> preferred.encodeToJsonValue(value, config)
        is Schema.Transform<A, *> -> encodeTransformToJsonValue(value, config)
        is Schema.Collection<*> -> encodeCollectionToJsonValue(value as List<*>, config)
        is Schema.StringMap<*> -> encodeStringMapToJsonValue(value as Map<*, *>, config)
        is Schema.Union<*> -> encodeUnionToJsonValue(value, config)
        is Schema.Record<*> -> encodeRecordToJsonValue(value, config)
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Primitive<A>.encodePrimitiveToJsonValue(value: A): JsonValue =
    when (this) {
        is Schema.Primitive.Boolean -> JsonValue.Bool(value as kotlin.Boolean)
        is Schema.Primitive.String -> JsonValue.Str(value as kotlin.String)
        is Schema.Primitive.Int -> JsonValue.Number.of(value as kotlin.Int)
        is Schema.Primitive.Long -> JsonValue.Number.of(value as kotlin.Long)
        is Schema.Primitive.Float -> JsonValue.Number.of(value as kotlin.Float)
        is Schema.Primitive.Double -> JsonValue.Number.of(value as kotlin.Double)
        is Schema.Primitive.Enumeration<*> -> JsonValue.Str(value.toString())
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptionalToJsonValue(value: Any?, config: JsonEncodingConfig): JsonValue =
    if (value == null) JsonValue.Null else schema.encodeToJsonValue(value as A, config)

private fun <A, B> Schema.Transform<A, B>.encodeTransformToJsonValue(value: A, config: JsonEncodingConfig): JsonValue =
    schema.encodeToJsonValue(encode(value), config)

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeCollectionToJsonValue(value: List<Any?>, config: JsonEncodingConfig): JsonValue =
    JsonValue.Arr(value.map { itemSchema.encodeToJsonValue(it as A, config) })

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMapToJsonValue(value: Map<*, *>, config: JsonEncodingConfig): JsonValue =
    JsonValue.Obj(
        value.entries.associate { (k, v) -> (k as String) to valueSchema.encodeToJsonValue(v as V, config) }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecordToJsonValue(value: Any?, config: JsonEncodingConfig): JsonValue =
    JsonValue.Obj(
        unsafeFields.mapNotNull { field ->
            val schema = field.schema as Schema<Any?>
            val fieldValue = field.extract(value as A)
            if (!config.explicitNulls && fieldValue == null) {
                null
            } else {
                field.name to schema.encodeToJsonValue(fieldValue, config)
            }
        }.toMap()
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnionToJsonValue(value: Any?, config: JsonEncodingConfig): JsonValue {
    val typedValue = value as A
    val cases = unsafeCases
    val (case, caseValue) = cases
        .firstNotNullOfOrNull { case -> case.deconstruct(typedValue)?.let { case to it } }
        ?: error("No case found for value '$typedValue' in union '${metadata.name}' (available cases: ${cases.map { it.name }})")
    val discriminator = mapOf(key to JsonValue.Str(case.name))
    val caseObj = (case.schema as Schema<Any?>).encodeToJsonValue(caseValue, config)
    val caseFields = (caseObj as? JsonValue.Obj)?.entries
        ?: error("Union case '${case.name}' in '${metadata.name}' must encode to JsonValue.Obj, but got ${caseObj::class.simpleName}")
    require(key !in caseFields) {
        "Union case '${case.name}' in '${metadata.name}' has field '$key' that conflicts with discriminator key"
    }
    return JsonValue.Obj(discriminator + caseFields)
}
