package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.JsonValue
import io.github.bbasinsk.schema.Schema
import kotlinx.io.readByteArray

fun <A> Schema<A>.encodeToJsonBytes(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): ByteArray =
    kotlinx.io.Buffer().also { encodeToSink(value, it, config) }.readByteArray()

fun <A> Schema<A>.encodeToJsonString(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): String =
    encodeToJsonBytes(value, config).decodeToString()

fun JsonValue.encodeToJsonBytes(config: JsonEncodingConfig = JsonEncodingConfig()): ByteArray =
    kotlinx.io.Buffer().also { encodeToSink(it, config) }.readByteArray()

fun JsonValue.encodeToJsonString(config: JsonEncodingConfig = JsonEncodingConfig()): String =
    encodeToJsonBytes(config).decodeToString()
