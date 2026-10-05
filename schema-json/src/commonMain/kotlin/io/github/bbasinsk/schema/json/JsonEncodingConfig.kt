package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema

data class JsonEncodingConfig(
    val explicitNulls: Boolean = true,
    val allowSpecialFloatingPointValues: Boolean = false,
    val printConfig: PrintConfig = PrintConfig.compact,
    /** Leave out record fields declared with `.default()` whose value equals that default. */
    val omitDefaults: Boolean = false,
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

/** Whether a record field holding [value] under [schema] is left out of the encoding. */
fun JsonEncodingConfig.skipsField(schema: Schema<*>, value: Any?): Boolean =
    (!explicitNulls && value == null) || (omitDefaults && schema.isAtDefault(value))

private fun Schema<*>.isAtDefault(value: Any?): Boolean = when (this) {
    is Schema.Default -> value == default
    is Schema.Metadata -> schema.isAtDefault(value)
    is Schema.Lazy -> schema().isAtDefault(value)
    else -> false
}
