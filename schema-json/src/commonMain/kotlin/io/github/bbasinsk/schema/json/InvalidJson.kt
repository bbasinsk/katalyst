package io.github.bbasinsk.schema.json


sealed interface InvalidJson {
    data class Or(
        val preferred: List<InvalidJson>,
        val fallback: List<InvalidJson>
    ) : InvalidJson

    data class FieldError(
        val expected: String,
        val found: String,
        val path: List<Segment>
    ) : InvalidJson

    private fun tab(n: Int) = "    ".repeat(n)
    fun reason(indent: Int = 0): String = when (this) {
        is FieldError -> buildString {
            append("Expected $expected, found $found")
            if (path.isNotEmpty()) append(" at path: ${path.encodeJsonPathDotNotation()}")
        }

        is Or -> buildString {
            appendLine(tab(indent) + "Either:")
            appendLine(tab(indent + 1) + preferred.joinToString { it.reason(indent + 1) })
            appendLine(tab(indent + 1) + fallback.joinToString { it.reason(indent + 1) })
        }
    }
}
