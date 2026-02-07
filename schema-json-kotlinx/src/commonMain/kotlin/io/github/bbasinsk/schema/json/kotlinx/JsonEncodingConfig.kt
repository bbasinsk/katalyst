package io.github.bbasinsk.schema.json.kotlinx

import kotlinx.serialization.json.Json

data class JsonEncodingConfig(
    val explicitNulls: Boolean,
    val allowSpecialFloatingPointValues: Boolean
)

fun Json.toEncodingConfig(): JsonEncodingConfig = JsonEncodingConfig(
    explicitNulls = configuration.explicitNulls,
    allowSpecialFloatingPointValues = configuration.allowSpecialFloatingPointValues
)
