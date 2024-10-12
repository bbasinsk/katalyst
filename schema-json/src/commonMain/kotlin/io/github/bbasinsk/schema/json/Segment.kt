package io.github.bbasinsk.schema.json

sealed interface Segment {
    data class Field(val name: String) : Segment
    data class Index(val index: Int) : Segment
}
