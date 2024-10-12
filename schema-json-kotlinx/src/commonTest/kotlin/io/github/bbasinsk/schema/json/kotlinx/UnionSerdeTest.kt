package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.json.InvalidJson
import io.github.bbasinsk.schema.json.Segment
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UnionSerdeTest {

    sealed interface UnionExample {
        data class IntExample(val value: Int) : UnionExample
        data class StringExample(val value: String) : UnionExample
    }

    private fun Schema.Companion.intExample(): Schema<UnionExample.IntExample> =
        record(
            field(UnionExample.IntExample::value, int()),
            UnionExample::IntExample
        )

    private fun Schema.Companion.stringExample(): Schema<UnionExample.StringExample> =
        record(
            field(UnionExample.StringExample::value, string()),
            UnionExample::StringExample
        )

    private fun Schema.Companion.unionExample(): Schema<UnionExample> =
        union(
            case(intExample()),
            case(stringExample())
        )

    @Test
    fun `union deserializes with first case`() {
        assertEquals(
            Validation.valid(UnionExample.IntExample(42)),
            Schema.unionExample().decodeFromJsonString(
                """
                {
                    "type": "IntExample",
                    "value": 42
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun `union deserializes with second case`() {
        assertEquals(
            Validation.valid(UnionExample.StringExample("hello")),
            Schema.unionExample().decodeFromJsonString(
                """
                {
                    "type": "StringExample",
                    "value": "hello"
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun `union fails deserialize with invalid type field`() {
        assertEquals(
            Validation.invalid(
                InvalidJson(
                    expected = "String",
                    found = "42",
                    path = listOf(Segment.Field("type"))
                )
            ),
            Schema.unionExample().decodeFromJsonString(
                """
                {
                    "type": 42,
                    "value": "hello"
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun `union fails deserialize with unknown type`() {
        assertEquals(
            Validation.invalid(
                InvalidJson(
                    expected = "[IntExample, StringExample]",
                    found = "Other",
                    path = listOf(Segment.Field("type"))
                )
            ),
            Schema.unionExample().decodeFromJsonString(
                """
                {
                    "type": "Other",
                    "value": "hello"
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun `union deserializes with custom discriminator`() {
        val schema = Schema.union(
            case(Schema.intExample()),
            case(Schema.stringExample()),
            key = "subtype"
        )

        assertEquals(
            Validation.valid(UnionExample.StringExample("hello")),
            schema.decodeFromJsonString(
                """
                {
                    "subtype": "StringExample",
                    "value": "hello"
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun `union serializes first case`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                {
                    "type": "IntExample",
                    "value": 42
                }
                """.trimIndent()
            ),
            Schema.unionExample().encodeToJsonElement(UnionExample.IntExample(42))
        )
    }

    @Test
    fun `union serializes second case`() {
        assertEquals(
            Json.parseToJsonElement(
                """
                {
                    "type": "StringExample",
                    "value": "hello"
                }
                """.trimIndent()
            ),
            Schema.unionExample().encodeToJsonElement(UnionExample.StringExample("hello"))
        )
    }

    @Test
    fun `union serializes with custom discriminator`() {
        val schema = Schema.union(
            case(Schema.intExample()),
            case(Schema.stringExample()),
            key = "subtype"
        )

        assertEquals(
            Json.parseToJsonElement(
                """
                {
                    "subtype": "StringExample",
                    "value": "hello"
                }
                """.trimIndent()
            ),
            schema.encodeToJsonElement(UnionExample.StringExample("hello"))
        )
    }
}
