package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.Segment
import io.github.bbasinsk.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionSerdeTest {

    @Test
    fun `list serializes with valid values`() {
        val schema = Schema.list(Schema.int())
        assertEquals("[1,2,3]", schema.encodeToJsonString(listOf(1, 2, 3)))
    }

    @Test
    fun `list deserializes with valid json`() {
        val schema = Schema.list(Schema.int())
        assertEquals(
            Validation.valid(listOf(1, 2, 3)),
            schema.decodeFromJsonString("[1, 2, 3]")
        )
    }

    @Test
    fun `list works with empty list json`() {
        val str = ListRecord.schema.encodeToJsonElement(ListRecord(emptyList()))
        assertEquals("""{"value":[]}""", str.toString())
    }

    @Test
    fun `list fails deserialize with invalid values`() {
        val schema = Schema.list(Schema.int())
        assertEquals(
            Validation.invalid(
                InvalidJson(
                    expected = "Int",
                    found = """"not-an-int"""",
                    path = listOf(Segment.Index(0))
                ),
                InvalidJson(
                    expected = "Int",
                    found = "null",
                    path = listOf(Segment.Index(2))
                )
            ),
            schema.decodeFromJsonString("""["not-an-int", 42, null]""")
        )
    }

    @Test
    fun `stringMap serializes with valid values`() {
        val schema = Schema.stringMap(Schema.int())
        assertEquals("""{"a":1,"b":2}""", schema.encodeToJsonString(mapOf("a" to 1, "b" to 2)))
    }

    @Test
    fun `stringMap deserializes with valid json`() {
        val schema = Schema.stringMap(Schema.int())
        assertEquals(
            Validation.valid(mapOf("a" to 1, "b" to 2)),
            schema.decodeFromJsonString("""{"a": 1, "b": 2}""")
        )
    }

    @Test
    fun `stringMap fails deserialize with invalid values`() {
        val schema = Schema.stringMap(Schema.int())
        assertEquals(
            Validation.invalid(
                InvalidJson(
                    expected = "Int",
                    found = """"not-an-int"""",
                    path = listOf(Segment.Field("a"))
                ),
                InvalidJson(
                    expected = "Int",
                    found = "null",
                    path = listOf(Segment.Field("b"))
                )
            ),
            schema.decodeFromJsonString("""{"a": "not-an-int", "b": null}""")
        )
    }
}

data class ListRecord(val value: List<Int>) {
    companion object {
        val schema: Schema<ListRecord> = with(Schema) {
            record(
                field(list(int()).default(emptyList()), "value") { value },
                ::ListRecord
            )
        }
    }
}
