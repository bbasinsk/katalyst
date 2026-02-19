@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.encodeToSchemaValue(value: A): SchemaValue =
    when (this) {
        is Schema.Empty -> SchemaValue.Null
        is Schema.Dynamic -> value as SchemaValue
        is Schema.Bytes -> SchemaValue.Str(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitiveToSchemaValue(value)
        is Schema.Lazy -> schema().encodeToSchemaValue(value)
        is Schema.Metadata -> schema.encodeToSchemaValue(value)
        is Schema.Optional<*> -> encodeOptionalToSchemaValue(value)
        is Schema.Default -> schema.encodeToSchemaValue(value)
        is Schema.OrElse<A, *> -> preferred.encodeToSchemaValue(value)
        is Schema.Transform<A, *> -> encodeTransformToSchemaValue(value)
        is Schema.Collection<*> -> encodeCollectionToSchemaValue(value as List<*>)
        is Schema.StringMap<*> -> encodeStringMapToSchemaValue(value as Map<*, *>)
        is Schema.Union<*> -> encodeUnionToSchemaValue(value)
        is Schema.Record<*> -> encodeRecordToSchemaValue(value)
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
private fun <A> Schema.Optional<A>.encodeOptionalToSchemaValue(value: Any?): SchemaValue =
    if (value == null) SchemaValue.Null else schema.encodeToSchemaValue(value as A)

private fun <A, B> Schema.Transform<A, B>.encodeTransformToSchemaValue(value: A): SchemaValue =
    schema.encodeToSchemaValue(encode(value))

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeCollectionToSchemaValue(value: List<Any?>): SchemaValue =
    SchemaValue.Arr(value.map { itemSchema.encodeToSchemaValue(it as A) })

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMapToSchemaValue(value: Map<*, *>): SchemaValue =
    SchemaValue.Obj(
        value.entries.associate { (k, v) -> (k as String) to valueSchema.encodeToSchemaValue(v as V) }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecordToSchemaValue(value: Any?): SchemaValue =
    SchemaValue.Obj(
        unsafeFields().associate { field ->
            val schema = field.schema as Schema<Any?>
            field.name to schema.encodeToSchemaValue(field.extract(value as A))
        }
    )

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnionToSchemaValue(value: Any?): SchemaValue {
    val typedValue = value as A
    val (case, caseValue) = unsafeCases()
        .firstNotNullOfOrNull { case -> case.deconstruct(typedValue)?.let { case to it } }
        ?: error("No case found for value '$typedValue' in union '${metadata.name}' (available cases: ${unsafeCases().map { it.name }})")
    val discriminator = mapOf(key to SchemaValue.Str(case.name))
    val caseObj = (case.schema as Schema<Any?>).encodeToSchemaValue(caseValue)
    val caseFields = (caseObj as? SchemaValue.Obj)?.entries
        ?: error("Union case '${case.name}' in '${metadata.name}' must encode to SchemaValue.Obj, but got ${caseObj::class.simpleName}")
    return SchemaValue.Obj(discriminator + caseFields)
}
