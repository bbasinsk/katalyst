package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.json.JsonEncodingConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun Json.toEncodingConfig(): JsonEncodingConfig = JsonEncodingConfig(
    explicitNulls = configuration.explicitNulls,
    allowSpecialFloatingPointValues = configuration.allowSpecialFloatingPointValues,
    printConfig = if (configuration.prettyPrint) JsonEncodingConfig.PrintConfig.pretty(indent = configuration.prettyPrintIndent) else JsonEncodingConfig.PrintConfig.compact
)
