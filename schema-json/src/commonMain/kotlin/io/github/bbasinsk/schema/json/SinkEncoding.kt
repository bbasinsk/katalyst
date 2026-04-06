@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import kotlinx.io.Sink
import kotlinx.io.writeString
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.encodeToSink(value: A, sink: Sink, config: JsonEncodingConfig) {
    encodeToSink(value, sink, config, depth = 0)
}

fun SchemaValue.encodeToSink(sink: Sink, config: JsonEncodingConfig) {
    encodeDynamicToSink(this, sink, config, depth = 0)
}

private fun Sink.writeIndent(print: JsonEncodingConfig.PrintConfig, depth: Int) {
    writeString(print.newLine)
    writeString(print.indent.repeat(depth))
}

private fun <A> Schema<A>.encodeToSink(value: A, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    when (this) {
        is Schema.Empty -> sink.writeString("null")
        is Schema.Dynamic -> encodeDynamicToSink(value as SchemaValue, sink, config, depth)
        is Schema.Bytes -> sink.writeJsonString(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitive(value, sink, config)
        is Schema.Lazy -> schema().encodeToSink(value, sink, config, depth)
        is Schema.Metadata -> schema.encodeToSink(value, sink, config, depth)
        is Schema.Optional<*> -> encodeOptional(value, sink, config, depth)
        is Schema.Default -> schema.encodeToSink(value, sink, config, depth)
        is Schema.OrElse<A, *> -> preferred.encodeToSink(value, sink, config, depth)
        is Schema.Transform<A, *> -> encodeTransform(value, sink, config, depth)
        is Schema.Collection<*> -> encodeList(value as List<*>, sink, config, depth)
        is Schema.StringMap<*> -> encodeStringMap(value as Map<*, *>, sink, config, depth)
        is Schema.Union<*> -> encodeUnion(value, sink, config, depth)
        is Schema.Record<*> -> encodeRecord(value, sink, config, depth)
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
            sink.writeJsonDouble(d)
        }
        is Schema.Primitive.Float -> {
            val f = value as Float
            if (f.isNaN() || f.isInfinite()) {
                require(config.allowSpecialFloatingPointValues) { "Non-finite float value: $f" }
            }
            sink.writeJsonDouble(f.toDouble())
        }
        is Schema.Primitive.String -> sink.writeJsonString(value as String)
        is Schema.Primitive.Enumeration<*> -> sink.writeJsonString(value.toString())
    }
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptional(value: Any?, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    if (value == null) sink.writeString("null") else schema.encodeToSink(value as A, sink, config, depth)
}

private fun <A, B> Schema.Transform<A, B>.encodeTransform(value: A, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    schema.encodeToSink(encode(value), sink, config, depth)
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeList(value: List<Any?>, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    val print = config.printConfig
    sink.writeString("[")
    if (value.isNotEmpty()) {
        value.forEachIndexed { i, item ->
            if (i > 0) sink.writeString(",")
            sink.writeIndent(print, depth + 1)
            itemSchema.encodeToSink(item as A, sink, config, depth + 1)
        }
        sink.writeIndent(print, depth)
    }
    sink.writeString("]")
}

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMap(value: Map<*, *>, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    val print = config.printConfig
    sink.writeString("{")
    if (value.isNotEmpty()) {
        var first = true
        for ((k, v) in value) {
            if (!first) sink.writeString(",")
            first = false
            sink.writeIndent(print, depth + 1)
            sink.writeJsonString(k as String)
            sink.writeString(print.colon)
            valueSchema.encodeToSink(v as V, sink, config, depth + 1)
        }
        sink.writeIndent(print, depth)
    }
    sink.writeString("}")
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecord(value: Any?, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    val print = config.printConfig
    sink.writeString("{")
    var first = true
    for (field in unsafeFields) {
        val schema = field.schema as Schema<Any?>
        val fieldValue = field.extract(value as A)
        if (!config.explicitNulls && fieldValue == null) continue
        if (!first) sink.writeString(",")
        first = false
        sink.writeIndent(print, depth + 1)
        sink.writeJsonString(field.name)
        sink.writeString(print.colon)
        schema.encodeToSink(fieldValue, sink, config, depth + 1)
    }
    if (!first) sink.writeIndent(print, depth)
    sink.writeString("}")
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnion(value: Any?, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    val print = config.printConfig
    val cases = unsafeCases
    val (case, caseValue) = cases
        .firstNotNullOfOrNull { case -> case.deconstruct(value as A)?.let { case to it } }
        ?: error("No case found for ${value as A}")
    sink.writeString("{")
    sink.writeIndent(print, depth + 1)
    sink.writeJsonString(key)
    sink.writeString(print.colon)
    sink.writeJsonString(case.name)
    encodeRecordFieldsInline(case.schema as Schema<Any?>, caseValue, sink, config, depth + 1, key)
    sink.writeIndent(print, depth)
    sink.writeString("}")
}

private fun encodeRecordFieldsInline(schema: Schema<Any?>, value: Any?, sink: Sink, config: JsonEncodingConfig, depth: Int, discriminatorKey: String) {
    val print = config.printConfig
    @Suppress("UNCHECKED_CAST")
    when (schema) {
        is Schema.Record<*> -> {
            val record = schema as Schema.Record<Any?>
            for (field in record.unsafeFields) {
                require(field.name != discriminatorKey) {
                    "Union case field '${field.name}' conflicts with discriminator key '$discriminatorKey'"
                }
                val fieldSchema = field.schema as Schema<Any?>
                val fieldValue = field.extract(value)
                if (!config.explicitNulls && fieldValue == null) continue
                sink.writeString(",")
                sink.writeIndent(print, depth)
                sink.writeJsonString(field.name)
                sink.writeString(print.colon)
                fieldSchema.encodeToSink(fieldValue, sink, config, depth)
            }
        }
        is Schema.Transform<*, *> -> {
            val transform = schema as Schema.Transform<Any?, Any?>
            encodeRecordFieldsInline(transform.schema, transform.encode(value), sink, config, depth, discriminatorKey)
        }
        is Schema.Metadata -> encodeRecordFieldsInline(schema.schema, value, sink, config, depth, discriminatorKey)
        is Schema.Lazy -> encodeRecordFieldsInline(schema.schema(), value, sink, config, depth, discriminatorKey)
        is Schema.Default -> encodeRecordFieldsInline(schema.schema, value, sink, config, depth, discriminatorKey)
        is Schema.OrElse<*, *> -> encodeRecordFieldsInline(schema.preferred as Schema<Any?>, value, sink, config, depth, discriminatorKey)
        is Schema.Optional<*> -> encodeRecordFieldsInline(schema.schema as Schema<Any?>, value, sink, config, depth, discriminatorKey)
        is Schema.Empty,
        is Schema.Dynamic,
        is Schema.Bytes,
        is Schema.Primitive,
        is Schema.Collection<*>,
        is Schema.StringMap<*>,
        is Schema.Union<*> ->
            error("Union case schema must resolve to a Record, found ${schema::class.simpleName}")
    }
}

private fun encodeDynamicToSink(value: SchemaValue, sink: Sink, config: JsonEncodingConfig, depth: Int) {
    val print = config.printConfig
    when (value) {
        is SchemaValue.Null -> sink.writeString("null")
        is SchemaValue.Bool -> sink.writeString(value.value.toString())
        is SchemaValue.Integer -> sink.writeString(value.value.toString())
        is SchemaValue.Decimal -> {
            if (value.value.isNaN() || value.value.isInfinite()) {
                require(config.allowSpecialFloatingPointValues) { "Non-finite double value in SchemaValue.Decimal: ${value.value}" }
            }
            sink.writeString(value.value.toString())
        }
        is SchemaValue.Str -> sink.writeJsonString(value.value)
        is SchemaValue.Arr -> {
            sink.writeString("[")
            if (value.values.isNotEmpty()) {
                value.values.forEachIndexed { i, item ->
                    if (i > 0) sink.writeString(",")
                    sink.writeIndent(print, depth + 1)
                    encodeDynamicToSink(item, sink, config, depth + 1)
                }
                sink.writeIndent(print, depth)
            }
            sink.writeString("]")
        }
        is SchemaValue.Obj -> {
            sink.writeString("{")
            if (value.entries.isNotEmpty()) {
                var first = true
                for ((k, v) in value.entries) {
                    if (!first) sink.writeString(",")
                    first = false
                    sink.writeIndent(print, depth + 1)
                    sink.writeJsonString(k)
                    sink.writeString(print.colon)
                    encodeDynamicToSink(v, sink, config, depth + 1)
                }
                sink.writeIndent(print, depth)
            }
            sink.writeString("}")
        }
    }
}

// JS toString() omits trailing .0 for whole-number doubles (e.g. 42.0 -> "42")
private fun Sink.writeJsonDouble(d: Double) {
    val s = d.toString()
    if ('.' !in s && 'e' !in s && 'E' !in s && !d.isNaN() && !d.isInfinite()) {
        writeString(s + ".0")
    } else {
        writeString(s)
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
