package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import kotlinx.io.readByteArray

fun <A> Schema<A>.encodeToJsonBytes(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): ByteArray =
    kotlinx.io.Buffer().also { encodeToSink(value, it, config) }.readByteArray()

fun <A> Schema<A>.encodeToJsonString(value: A, config: JsonEncodingConfig = JsonEncodingConfig()): String =
    encodeToJsonBytes(value, config).decodeToString()

fun SchemaValue.encodeToJsonBytes(config: JsonEncodingConfig = JsonEncodingConfig()): ByteArray =
    kotlinx.io.Buffer().also { encodeToSink(it, config) }.readByteArray()

fun SchemaValue.encodeToJsonString(config: JsonEncodingConfig = JsonEncodingConfig()): String =
    encodeToJsonBytes(config).decodeToString()
