package io.github.bbasinsk.schema.json

fun List<Segment>.encodeJsonPathDotNotation(): String =
    "$" + joinToString(separator = "") { segment ->
        when (segment) {
            is Segment.Field -> ".${segment.name}"
            is Segment.Index -> "[${segment.index}]"
        }
    }

fun String.decodeJsonPathDotNotation(): List<Segment> =
    split('$', '.', '[').mapNotNull { segment ->
        when {
            segment.endsWith(']') -> Segment.Index(segment.removeSuffix("]").toInt())
            segment.isNotBlank() -> Segment.Field(segment)
            else -> null
        }
    }
