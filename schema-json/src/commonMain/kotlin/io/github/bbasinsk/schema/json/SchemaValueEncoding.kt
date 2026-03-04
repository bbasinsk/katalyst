@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.encodeToSchemaValue(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): SchemaValue =
    when (this) {
        is Schema.Empty -> SchemaValue.Null
        is Schema.Dynamic -> value as SchemaValue
        is Schema.Bytes -> SchemaValue.Str(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitiveToSchemaValue(value)
        is Schema.Lazy -> schema().encodeToSchemaValue(value, config)
        is Schema.Metadata -> schema.encodeToSchemaValue(value, config)
        is Schema.Optional<*> -> encodeOptionalToSchemaValue(value, config)
        is Schema.Default -> schema.encodeToSchemaValue(value, config)
        is Schema.OrElse<A, *> -> preferred.encodeToSchemaValue(value, config)
        is Schema.Transform<A, *> -> encodeTransformToSchemaValue(value, config)
        is Schema.Collection<*> -> encodeCollectionToSchemaValue(value as List<*>, config)
        is Schema.StringMap<*> -> encodeStringMapToSchemaValue(value as Map<*, *>, config)
        is Schema.Union<*> -> encodeUnionToSchemaValue(value, config)
        is Schema.Record<*> -> encodeRecordToSchemaValue(value, config)
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Primitive<A>.encodePrimitiveToSchemaValue(value: A): SchemaValue =
    when (this) {
        is Schema.Primitive.Boolean -> SchemaValue.Bool(value as kotlin.Boolean)
        is Schema.Primitive.String -> SchemaValue.Str(value as kotlin.String)
        is Schema.Primitive.Int -> SchemaValue.Integer((value as kotlin.Int).toLong())
        is Schema.Primitive.Long -> SchemaValue.Integer(value as kotlin.Long)
        is Schema.Primitive.Float -> SchemaValue.Decimal((value as kotlin.Float).toDouble())
        is Schema.Primitive.Double -> SchemaValue.Decimal(value as kotlin.Double)
        is Schema.Primitive.Enumeration<*> -> SchemaValue.Str(value.toString())
    }

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptionalToSchemaValue(value: Any?, config: JsonEncodingConfig): SchemaValue =
    if (value == null) SchemaValue.Null else schema.encodeToSchemaValue(value as A, config)

private fun <A, B> Schema.Transform<A, B>.encodeTransformToSchemaValue(value: A, config: JsonEncodingConfig): SchemaValue =
    schema.encodeToSchemaValue(encode(value), config)

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeCollectionToSchemaValue(value: List<Any?>, config: JsonEncodingConfig): SchemaValue =
    SchemaValue.Arr(value.map { itemSchema.encodeToSchemaValue(it as A, config) })

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMapToSchemaValue(value: Map<*, *>, config: JsonEncodingConfig): SchemaValue =
    SchemaValue.Obj(
        value.entries.associate { (k, v) -> (k as String) to valueSchema.encodeToSchemaValue(v as V, config) }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecordToSchemaValue(value: Any?, config: JsonEncodingConfig): SchemaValue =
    SchemaValue.Obj(
        unsafeFields.mapNotNull { field ->
            val schema = field.schema as Schema<Any?>
            val fieldValue = field.extract(value as A)
            if (!config.explicitNulls && fieldValue == null) {
                null
            } else {
                field.name to schema.encodeToSchemaValue(fieldValue, config)
            }
        }.toMap()
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnionToSchemaValue(value: Any?, config: JsonEncodingConfig): SchemaValue {
    val typedValue = value as A
    val cases = unsafeCases
    val (case, caseValue) = cases
        .firstNotNullOfOrNull { case -> case.deconstruct(typedValue)?.let { case to it } }
        ?: error("No case found for value '$typedValue' in union '${metadata.name}' (available cases: ${cases.map { it.name }})")
    val discriminator = mapOf(key to SchemaValue.Str(case.name))
    val caseObj = (case.schema as Schema<Any?>).encodeToSchemaValue(caseValue, config)
    val caseFields = (caseObj as? SchemaValue.Obj)?.entries
        ?: error("Union case '${case.name}' in '${metadata.name}' must encode to SchemaValue.Obj, but got ${caseObj::class.simpleName}")
    require(key !in caseFields) {
        "Union case '${case.name}' in '${metadata.name}' has field '$key' that conflicts with discriminator key"
    }
    return SchemaValue.Obj(discriminator + caseFields)
}
