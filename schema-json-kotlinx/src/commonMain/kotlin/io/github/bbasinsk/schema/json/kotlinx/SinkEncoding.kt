@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import kotlinx.io.Sink
import kotlinx.io.writeString
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.encodeToSink(value: A, sink: Sink, config: JsonEncodingConfig) {
    when (this) {
        is Schema.Empty -> sink.writeString("null")
        is Schema.Bytes -> sink.writeJsonString(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitive(value, sink, config)
        is Schema.Lazy -> schema().encodeToSink(value, sink, config)
        is Schema.Metadata -> schema.encodeToSink(value, sink, config)
        is Schema.Optional<*> -> encodeOptional(value, sink, config)
        is Schema.Default -> schema.encodeToSink(value, sink, config)
        is Schema.OrElse<A, *> -> preferred.encodeToSink(value, sink, config)
        is Schema.Transform<A, *> -> encodeTransform(value, sink, config)
        is Schema.Collection<*> -> encodeList(value as List<*>, sink, config)
        is Schema.StringMap<*> -> encodeStringMap(value as Map<*, *>, sink, config)
        is Schema.Union<*> -> encodeUnion(value, sink, config)
        is Schema.Record<*> -> encodeRecord(value, sink, config)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Primitive<A>.encodePrimitive(value: A, sink: Sink, config: JsonEncodingConfig) {
    when (this) {
        is Schema.Primitive.Boolean -> sink.writeString((value as Boolean).toString())
        is Schema.Primitive.Int -> sink.writeString((value as Int).toString())
        is Schema.Primitive.Long -> sink.writeString((value as Long).toString())
        is Schema.Primitive.Double -> {
            val d = value as Double
            if (d.isNaN() || d.isInfinite()) {
                require(config.allowSpecialFloatingPointValues) { "Non-finite double value: $d" }
            }
            sink.writeString(d.toString())
        }
        is Schema.Primitive.Float -> {
            val f = value as Float
            if (f.isNaN() || f.isInfinite()) {
                require(config.allowSpecialFloatingPointValues) { "Non-finite float value: $f" }
            }
            sink.writeString(f.toString())
        }
        is Schema.Primitive.String -> sink.writeJsonString(value as String)
        is Schema.Primitive.Enumeration<*> -> sink.writeJsonString(value.toString())
    }
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptional(value: Any?, sink: Sink, config: JsonEncodingConfig) {
    if (value == null) sink.writeString("null") else schema.encodeToSink(value as A, sink, config)
}

private fun <A, B> Schema.Transform<A, B>.encodeTransform(value: A, sink: Sink, config: JsonEncodingConfig) {
    schema.encodeToSink(encode(value), sink, config)
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeList(value: List<Any?>, sink: Sink, config: JsonEncodingConfig) {
    sink.writeString("[")
    value.forEachIndexed { i, item ->
        if (i > 0) sink.writeString(",")
        itemSchema.encodeToSink(item as A, sink, config)
    }
    sink.writeString("]")
}

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMap(value: Map<*, *>, sink: Sink, config: JsonEncodingConfig) {
    sink.writeString("{")
    var first = true
    for ((k, v) in value) {
        if (!first) sink.writeString(",")
        first = false
        sink.writeJsonString(k as String)
        sink.writeString(":")
        valueSchema.encodeToSink(v as V, sink, config)
    }
    sink.writeString("}")
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecord(value: Any?, sink: Sink, config: JsonEncodingConfig) {
    sink.writeString("{")
    var first = true
    for (field in unsafeFields()) {
        val schema = field.schema as Schema<Any?>
        val fieldValue = field.extract(value as A)
        if (!config.explicitNulls && fieldValue == null) continue
        if (!first) sink.writeString(",")
        first = false
        sink.writeJsonString(field.name)
        sink.writeString(":")
        schema.encodeToSink(fieldValue, sink, config)
    }
    sink.writeString("}")
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnion(value: Any?, sink: Sink, config: JsonEncodingConfig) {
    val (case, caseValue) = unsafeCases()
        .firstNotNullOfOrNull { case -> case.deconstruct(value as A)?.let { case to it } }
        ?: error("No case found for ${value as A}")
    sink.writeString("{")
    sink.writeJsonString(key)
    sink.writeString(":")
    sink.writeJsonString(case.name)
    encodeRecordFieldsInline(case.schema as Schema<Any?>, caseValue, sink, config)
    sink.writeString("}")
}

private fun encodeRecordFieldsInline(schema: Schema<Any?>, value: Any?, sink: Sink, config: JsonEncodingConfig) {
    @Suppress("UNCHECKED_CAST")
    when (schema) {
        is Schema.Record<*> -> {
            val record = schema as Schema.Record<Any?>
            for (field in record.unsafeFields()) {
                val fieldSchema = field.schema as Schema<Any?>
                val fieldValue = field.extract(value)
                if (!config.explicitNulls && fieldValue == null) continue
                sink.writeString(",")
                sink.writeJsonString(field.name)
                sink.writeString(":")
                fieldSchema.encodeToSink(fieldValue, sink, config)
            }
        }
        is Schema.Transform<*, *> -> {
            val transform = schema as Schema.Transform<Any?, Any?>
            encodeRecordFieldsInline(transform.schema, transform.encode(value), sink, config)
        }
        is Schema.Metadata -> encodeRecordFieldsInline(schema.schema, value, sink, config)
        is Schema.Lazy -> encodeRecordFieldsInline(schema.schema(), value, sink, config)
        is Schema.Default -> encodeRecordFieldsInline(schema.schema, value, sink, config)
        is Schema.OrElse<*, *> -> encodeRecordFieldsInline(schema.preferred as Schema<Any?>, value, sink, config)
        is Schema.Optional<*> -> encodeRecordFieldsInline(schema.schema as Schema<Any?>, value, sink, config)
        is Schema.Empty,
        is Schema.Bytes,
        is Schema.Primitive,
        is Schema.Collection<*>,
        is Schema.StringMap<*>,
        is Schema.Union<*> ->
            error("Union case schema must resolve to a Record, found ${schema::class.simpleName}")
    }
}

private fun escapeFor(ch: Char): String? = when (ch) {
    '"' -> "\\\""
    '\\' -> "\\\\"
    '\n' -> "\\n"
    '\r' -> "\\r"
    '\t' -> "\\t"
    '\b' -> "\\b"
    '\u000C' -> "\\f"
    else -> if (ch.code in 0..0x1F) "\\u${ch.code.toString(16).padStart(4, '0')}" else null
}

private fun Sink.writeJsonString(value: String) {
    writeString("\"")
    var last = 0
    for (i in value.indices) {
        val escape = escapeFor(value[i]) ?: continue
        if (i > last) writeString(value, last, i)
        writeString(escape)
        last = i + 1
    }
    if (last < value.length) writeString(value, last, value.length)
    writeString("\"")
}
