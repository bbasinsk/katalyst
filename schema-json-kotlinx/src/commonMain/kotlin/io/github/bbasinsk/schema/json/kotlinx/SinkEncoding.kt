@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import kotlinx.io.Sink
import kotlinx.io.writeString
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.encodeToSink(value: A, sink: Sink, explicitNulls: Boolean) {
    when (this) {
        is Schema.Empty -> sink.writeString("null")
        is Schema.Bytes -> sink.writeJsonString(Base64.encode(value as ByteArray))
        is Schema.Primitive -> encodePrimitive(value, sink)
        is Schema.Lazy -> schema().encodeToSink(value, sink, explicitNulls)
        is Schema.Metadata -> schema.encodeToSink(value, sink, explicitNulls)
        is Schema.Optional<*> -> encodeOptional(value, sink, explicitNulls)
        is Schema.Default -> schema.encodeToSink(value, sink, explicitNulls)
        is Schema.OrElse<A, *> -> preferred.encodeToSink(value, sink, explicitNulls)
        is Schema.Transform<A, *> -> encodeTransform(value, sink, explicitNulls)
        is Schema.Collection<*> -> encodeList(value as List<*>, sink, explicitNulls)
        is Schema.StringMap<*> -> encodeStringMap(value as Map<*, *>, sink, explicitNulls)
        is Schema.Union<*> -> encodeUnion(value, sink, explicitNulls)
        is Schema.Record<*> -> encodeRecord(value, sink, explicitNulls)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Primitive<A>.encodePrimitive(value: A, sink: Sink) {
    when (this) {
        is Schema.Primitive.Boolean -> sink.writeString((value as Boolean).toString())
        is Schema.Primitive.Int -> sink.writeString((value as Int).toString())
        is Schema.Primitive.Long -> sink.writeString((value as Long).toString())
        is Schema.Primitive.Double -> {
            val d = value as Double
            require(!d.isNaN() && !d.isInfinite()) { "Non-finite double value: $d" }
            sink.writeString(d.toString())
        }
        is Schema.Primitive.Float -> {
            val f = value as Float
            require(!f.isNaN() && !f.isInfinite()) { "Non-finite float value: $f" }
            sink.writeString(f.toString())
        }
        is Schema.Primitive.String -> sink.writeJsonString(value as String)
        is Schema.Primitive.Enumeration<*> -> sink.writeJsonString(value.toString())
    }
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Optional<A>.encodeOptional(value: Any?, sink: Sink, explicitNulls: Boolean) {
    if (value == null) sink.writeString("null") else schema.encodeToSink(value as A, sink, explicitNulls)
}

private fun <A, B> Schema.Transform<A, B>.encodeTransform(value: A, sink: Sink, explicitNulls: Boolean) {
    schema.encodeToSink(encode(value), sink, explicitNulls)
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Collection<A>.encodeList(value: List<Any?>, sink: Sink, explicitNulls: Boolean) {
    sink.writeString("[")
    value.forEachIndexed { i, item ->
        if (i > 0) sink.writeString(",")
        itemSchema.encodeToSink(item as A, sink, explicitNulls)
    }
    sink.writeString("]")
}

@Suppress("UNCHECKED_CAST")
private fun <V> Schema.StringMap<V>.encodeStringMap(value: Map<*, *>, sink: Sink, explicitNulls: Boolean) {
    sink.writeString("{")
    var first = true
    for ((k, v) in value) {
        if (!first) sink.writeString(",")
        first = false
        sink.writeJsonString(k as String)
        sink.writeString(":")
        valueSchema.encodeToSink(v as V, sink, explicitNulls)
    }
    sink.writeString("}")
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Record<A>.encodeRecord(value: Any?, sink: Sink, explicitNulls: Boolean) {
    sink.writeString("{")
    var first = true
    for (field in unsafeFields()) {
        val schema = field.schema as Schema<Any?>
        val fieldValue = field.extract(value as A)
        if (!explicitNulls && fieldValue == null) continue
        if (!first) sink.writeString(",")
        first = false
        sink.writeJsonString(field.name)
        sink.writeString(":")
        schema.encodeToSink(fieldValue, sink, explicitNulls)
    }
    sink.writeString("}")
}

@Suppress("UNCHECKED_CAST")
private fun <A> Schema.Union<A>.encodeUnion(value: Any?, sink: Sink, explicitNulls: Boolean) {
    val case = unsafeCases().find { it.deconstruct(value as A) != null } ?: error("No case found for ${value as A}")
    val caseValue = case.deconstruct(value as A)!!
    sink.writeString("{")
    sink.writeJsonString(key)
    sink.writeString(":")
    sink.writeJsonString(case.name)
    encodeRecordFieldsInline(case.schema as Schema<Any?>, caseValue, sink, explicitNulls)
    sink.writeString("}")
}

private fun encodeRecordFieldsInline(schema: Schema<Any?>, value: Any?, sink: Sink, explicitNulls: Boolean) {
    @Suppress("UNCHECKED_CAST")
    when (schema) {
        is Schema.Record<*> -> {
            val record = schema as Schema.Record<Any?>
            for (field in record.unsafeFields()) {
                val fieldSchema = field.schema as Schema<Any?>
                val fieldValue = field.extract(value)
                if (!explicitNulls && fieldValue == null) continue
                sink.writeString(",")
                sink.writeJsonString(field.name)
                sink.writeString(":")
                fieldSchema.encodeToSink(fieldValue, sink, explicitNulls)
            }
        }
        is Schema.Transform<*, *> -> {
            val transform = schema as Schema.Transform<Any?, Any?>
            encodeRecordFieldsInline(transform.schema, transform.encode(value), sink, explicitNulls)
        }
        is Schema.Metadata -> encodeRecordFieldsInline(schema.schema, value, sink, explicitNulls)
        is Schema.Lazy -> encodeRecordFieldsInline(schema.schema(), value, sink, explicitNulls)
        is Schema.Default -> encodeRecordFieldsInline(schema.schema, value, sink, explicitNulls)
        else -> error("Union case schema must be a Record (possibly wrapped in Transform/Metadata/Lazy/Default), found ${schema::class.simpleName}")
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
