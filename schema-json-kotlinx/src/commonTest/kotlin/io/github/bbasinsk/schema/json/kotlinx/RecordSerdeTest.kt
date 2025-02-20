package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.Segment
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class RecordSerdeTest {
    data class MyRecord(val value: Int)

    fun Schema.Companion.myRecord(): Schema<MyRecord> = record(
        field(int(), "value") { value },
        ::MyRecord
    )

    @Test
    fun `record serializes with valid value`() {
        assertEquals(
            Json.parseToJsonElement("""{"value": 42}"""),
            Schema.myRecord().encodeToJsonElement(MyRecord(42))
        )
    }

    @Test
    fun `record deserializes with valid json`() {
        assertEquals(
            Validation.valid(MyRecord(42)),
            Schema.myRecord().decodeFromJsonString("""{"value": 42}""")
        )
    }

    @Test
    fun `record fails deserialize with invalid value`() {
        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Int",
                    found = """"not-an-int"""",
                    path = listOf(Segment.Field("value"))
                )
            ),
            Schema.myRecord().decodeFromJsonString("""{"value": "not-an-int"}""")
        )
    }

    @Test
    fun `record fails deserialize with missing field`() {
        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
                    expected = "Int",
                    found = "null",
                    path = listOf(Segment.Field("value"))
                )
            ),
            Schema.myRecord().decodeFromJsonString("{}")
        )
    }

    @Test
    fun `record deserializes with missing field and default value`() {
        val myRecordWithDefault = Schema.record(
            Schema.field(Schema.int().default(42), "value") { value },
            ::MyRecord
        )

        assertEquals(
            Validation.valid(MyRecord(42)),
            myRecordWithDefault.decodeFromJsonString("{}")
        )
    }

    @Test
    fun `record deserializes with optional field`() {
        data class MyOptionalRecord(val value: Int?)

        val schema = Schema.record(
            Schema.field(Schema.int().optional(), "value") { value },
            ::MyOptionalRecord
        )

        assertEquals(
            Validation.valid(MyOptionalRecord(null)),
            schema.decodeFromJsonString("{}")
        )
    }
}
