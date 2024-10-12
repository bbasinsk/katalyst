package io.github.bbasinsk.schema.json

data class InvalidJson(
    val expected: String,
    val found: String,
    val path: List<Segment>
) {
    fun message(): String = buildString {
        append("Expected $expected, found $found")
        if (path.isNotEmpty()) append(" at path: ${path.encodeJsonPathDotNotation()}")
    }
}
