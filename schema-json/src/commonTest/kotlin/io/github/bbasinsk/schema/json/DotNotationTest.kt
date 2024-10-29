package io.github.bbasinsk.schema.json

import kotlin.test.Test
import kotlin.test.assertEquals

class DotNotationTest {

    @Test
    fun `encoding dot notation empty`() {
        val path = emptyList<Segment>()
        assertEquals("$", path.encodeJsonPathDotNotation())
        assertEquals(path, "".decodeJsonPathDotNotation())
    }

    @Test
    fun `encoding dot notation - field-field-index`() {
        val path = listOf(Segment.Field("foo"), Segment.Field("bar"), Segment.Index(42))
        val encoded = "$.foo.bar[42]"
        assertEquals(encoded, path.encodeJsonPathDotNotation())
        assertEquals(path, encoded.decodeJsonPathDotNotation())
    }

    @Test
    fun `encoding dot notation field-index-field`() {
        val path = listOf(Segment.Field("foo"), Segment.Index(42), Segment.Field("bar"))
        val encoded = "$.foo[42].bar"
        assertEquals(encoded, path.encodeJsonPathDotNotation())
        assertEquals(path, encoded.decodeJsonPathDotNotation())
    }

    @Test
    fun `encoding dot notation index-field-field`() {
        val path = listOf(Segment.Index(42), Segment.Field("foo"), Segment.Field("bar"))
        val encoded = "$[42].foo.bar"
        assertEquals(encoded, path.encodeJsonPathDotNotation())
        assertEquals(path, encoded.decodeJsonPathDotNotation())
    }
}
