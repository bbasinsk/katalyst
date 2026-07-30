package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.JsonValue
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.validation.Validation

@Deprecated("Use decodeJsonValueFromString instead", ReplaceWith("decodeJsonValueFromString(str, config)"))
fun decodeSchemaValueFromString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): JsonValue =
    decodeJsonValueFromString(str, config)

fun decodeJsonValueFromString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): JsonValue =
    decodeJsonValueFromBytes(str.encodeToByteArray(), config)

fun <A> Schema<A>.decodeFromJsonString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromJsonValue(decodeJsonValueFromString(str, config))

fun <A> Schema<A>.decodeFromJsonBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromJsonValue(decodeJsonValueFromBytes(bytes, config))
