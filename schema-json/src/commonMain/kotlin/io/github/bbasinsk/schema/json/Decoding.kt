package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.validation.Validation
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString

fun decodeSchemaValueFromString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    decodeSchemaValueFromSource(Buffer().apply { writeString(str) }, config)

fun decodeSchemaValueFromBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    decodeSchemaValueFromSource(Buffer().apply { write(bytes) }, config)

fun <A> Schema<A>.decodeFromSource(source: Source, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromSource(source, config))

fun <A> Schema<A>.decodeFromJsonString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromString(str, config))

fun <A> Schema<A>.decodeFromJsonBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromBytes(bytes, config))
