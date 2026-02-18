package io.github.bbasinsk.schema.json.kotlinx

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

data class JsonEncodingConfig(
    val explicitNulls: Boolean = true,
    val allowSpecialFloatingPointValues: Boolean = false,
    val printConfig: PrintConfig = PrintConfig.compact
) {
    data class PrintConfig(
        val newLine: String,
        val indent: String,
        val colon: String,
    ) {
        companion object {
            fun pretty(indent: String = "  ") = PrintConfig(newLine = "\n", indent = indent, colon = ": ")
            val compact = PrintConfig(newLine = "", indent = "", colon = ":")
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun Json.toEncodingConfig(): JsonEncodingConfig = JsonEncodingConfig(
    explicitNulls = configuration.explicitNulls,
    allowSpecialFloatingPointValues = configuration.allowSpecialFloatingPointValues,
    printConfig = if (configuration.prettyPrint) JsonEncodingConfig.PrintConfig.pretty(indent = configuration.prettyPrintIndent) else JsonEncodingConfig.PrintConfig.compact
)
