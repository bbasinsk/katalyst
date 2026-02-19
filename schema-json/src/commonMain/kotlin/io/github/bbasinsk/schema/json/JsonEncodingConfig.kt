package io.github.bbasinsk.schema.json

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
