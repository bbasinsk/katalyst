@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.Validation.Companion.invalid
import io.github.bbasinsk.validation.Validation.Companion.valid
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.mapInvalid
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.decodeFromSchemaValue(value: SchemaValue): Validation<InvalidJson, A> =
    Validation.decode(schema = this, value = value, path = ArrayList(8))

@Suppress("UNCHECKED_CAST")
private fun <A> Validation.Companion.decode(
    schema: Schema<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A> =
    when (schema) {
        is Schema.Empty -> valid(null as A)
        is Schema.Dynamic -> valid(value as A)
        is Schema.Bytes -> decodeBytes(value, path) as Validation<InvalidJson, A>
        is Schema.Lazy<A> -> decode(schema.schema(), value, path)
        is Schema.Metadata -> decode(schema.schema, value, path)
        is Schema.Primitive -> decodePrimitive(schema, value, path)
        is Schema.Default -> decodeDefault(schema, value, path)
        is Schema.Optional<*> -> decodeOptional(schema, value, path) as Validation<InvalidJson, A>
        is Schema.OrElse<A, *> -> decode(schema.preferred, value, path).orElse { preferredErrors ->
            decode(schema.fallback, value, path).andThen { b ->
                fromResult(schema.unsafeDecode(b)) { e ->
                    InvalidJson.FieldError(schema.metadata.name, """"${e.message ?: "unknown error"}"""", path.toList())
                }
            }.orElse { fallbackErrors ->
                invalid(InvalidJson.Or(preferredErrors, fallbackErrors))
            }
        }
        is Schema.Transform<A, *> -> decodeTransform(schema, value, path)
        is Schema.Collection<*> -> decodeList(schema, value, path) as Validation<InvalidJson, A>
        is Schema.StringMap<*> -> decodeStringMap(schema, value, path) as Validation<InvalidJson, A>
        is Schema.Record<*> -> decodeRecord(schema as Schema.Record<A>, value, path)
        is Schema.Union<*> -> decodeUnion(schema as Schema.Union<A>, value, path)
    }

private fun <A> Validation.Companion.decodePrimitive(
    schema: Schema.Primitive<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A> {
    val error = InvalidJson.FieldError(schema.name, value.toFoundString(), path.toList())
    @Suppress("UNCHECKED_CAST")
    return when (schema) {
        is Schema.Primitive.Boolean ->
            if (value is SchemaValue.Bool) valid(value.value as A) else invalid(error)
        is Schema.Primitive.Int ->
            if (value is SchemaValue.Integer) valid(value.value.toInt() as A) else invalid(error)
        is Schema.Primitive.Long ->
            if (value is SchemaValue.Integer) valid(value.value as A) else invalid(error)
        is Schema.Primitive.Double -> when (value) {
            is SchemaValue.Decimal -> valid(value.value as A)
            is SchemaValue.Integer -> valid(value.value.toDouble() as A)
            else -> invalid(error)
        }
        is Schema.Primitive.Float -> when (value) {
            is SchemaValue.Decimal -> valid(value.value.toFloat() as A)
            is SchemaValue.Integer -> valid(value.value.toFloat() as A)
            else -> invalid(error)
        }
        is Schema.Primitive.String ->
            if (value is SchemaValue.Str) valid(value.value as A) else invalid(error)
        is Schema.Primitive.Enumeration<*> ->
            if (value is SchemaValue.Str) {
                fromResult(schema.decodePrimitiveString(value.value)) { error } as Validation<InvalidJson, A>
            } else {
                invalid(error)
            }
    }
}

private fun Validation.Companion.decodeBytes(
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, ByteArray> {
    val error = InvalidJson.FieldError("base64 encoded", value.toFoundString(), path.toList())
    return if (value is SchemaValue.Str) {
        runCatching { Base64.decode(value.value) }.mapInvalid { error }
    } else {
        invalid(error)
    }
}

private fun <A> Validation.Companion.decodeOptional(
    schema: Schema.Optional<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A?> =
    if (value is SchemaValue.Null) valid(null) else decode(schema.schema, value, path)

private fun <A> Validation.Companion.decodeDefault(
    schema: Schema.Default<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A> =
    if (value is SchemaValue.Null) valid(schema.default) else decode(schema.schema, value, path)

private fun <A, B> Validation.Companion.decodeTransform(
    schema: Schema.Transform<A, B>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A> =
    decode(schema.schema, value, path)
        .andThen { b ->
            schema.decode(b).fold(
                { valid(it) },
                { e -> invalid(InvalidJson.FieldError(schema.metadata.name, """"${e.message ?: "unknown error"}"""", path.toList())) }
            )
        }

private fun <A> Validation.Companion.decodeList(
    schema: Schema.Collection<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, List<A>> =
    if (value is SchemaValue.Arr) {
        sequence(
            value.values.mapIndexed { i, item ->
                path.add(Segment.Index(i))
                val result = decode(schema.itemSchema, item, path)
                path.removeAt(path.lastIndex)
                result
            }
        )
    } else {
        invalid(InvalidJson.FieldError("Array", value.toFoundString(), path.toList()))
    }

private fun <V> Validation.Companion.decodeStringMap(
    schema: Schema.StringMap<V>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, Map<String, V>> =
    if (value is SchemaValue.Obj) {
        sequence(
            value.entries.map { (key, v) ->
                path.add(Segment.Field(key))
                val result = decode(schema.valueSchema, v, path).mapValid { key to it }
                path.removeAt(path.lastIndex)
                result
            }
        ).mapValid { it.toMap() }
    } else {
        invalid(InvalidJson.FieldError("Object", value.toFoundString(), path.toList()))
    }

private fun <A> Validation.Companion.decodeRecord(
    schema: Schema.Record<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A> =
    if (value is SchemaValue.Obj) {
        sequence(
            schema.unsafeFields.map {
                path.add(Segment.Field(it.name))
                val result = decode(it.schema, value.entries[it.name] ?: SchemaValue.Null, path)
                path.removeAt(path.lastIndex)
                result
            }
        ).mapValid { schema.unsafeConstruct(it) }
    } else {
        invalid(InvalidJson.FieldError("Object", value.toFoundString(), path.toList()))
    }

private fun <A> Validation.Companion.decodeUnion(
    schema: Schema.Union<A>,
    value: SchemaValue,
    path: ArrayList<Segment>
): Validation<InvalidJson, A> =
    @Suppress("UNCHECKED_CAST")
    if (value is SchemaValue.Obj) {
        val discriminatorValue = value.entries[schema.key] ?: SchemaValue.Null
        path.add(Segment.Field(schema.key))
        val keyResult = decode(Schema.string(), discriminatorValue, path)
        path.removeAt(path.lastIndex)
        keyResult
            .andThen { identifier ->
                val cases = schema.unsafeCases
                requireNotNull(cases.find { it.name == identifier }) {
                    InvalidJson.FieldError(
                        expected = cases.map { it.name }.joinToString(", ", "[", "]"),
                        found = identifier,
                        path = path.toList() + Segment.Field(schema.key)
                    )
                }
            }.andThen { case -> decode(case.schema as Schema<A>, value, path) }
    } else {
        invalid(InvalidJson.FieldError("Object", value.toFoundString(), path.toList()))
    }

private fun SchemaValue.toFoundString(): String = when (this) {
    is SchemaValue.Null -> "null"
    is SchemaValue.Bool -> value.toString()
    is SchemaValue.Integer -> value.toString()
    is SchemaValue.Decimal -> value.toString()
    is SchemaValue.Str -> "\"$value\""
    is SchemaValue.Arr -> "Array"
    is SchemaValue.Obj -> "Object"
}
