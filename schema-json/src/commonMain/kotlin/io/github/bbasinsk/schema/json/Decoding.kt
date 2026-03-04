package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.validation.Validation

fun decodeSchemaValueFromString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    decodeSchemaValueFromBytes(str.encodeToByteArray(), config)

fun <A> Schema<A>.decodeFromJsonString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromString(str, config))

fun <A> Schema<A>.decodeFromJsonBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromBytes(bytes, config))
