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
            field(int(), "value") { value },
            UnionExample::IntExample
        )

    private fun Schema.Companion.stringExample(): Schema<UnionExample.StringExample> =
        record(
            field(string(), "value") { value },
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
                """.trimIndent(),
                Json.Default
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
                """.trimIndent(),
                Json.Default
            )
        )
    }

    @Test
    fun `union fails deserialize with invalid type field`() {
        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
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
                """.trimIndent(),
                Json.Default
            )
        )
    }

    @Test
    fun `union fails deserialize with unknown type`() {
        assertEquals(
            Validation.invalid(
                InvalidJson.FieldError(
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
                """.trimIndent(),
                Json.Default
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
                """.trimIndent(),
                Json.Default
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

    sealed interface Decision {
        data object Save : Decision
        data class Revise(val feedback: String) : Decision
        data object Discard : Decision
    }

    private fun Schema.Companion.decision(): Schema<Decision> =
        union(
            case(record { Decision.Save }),
            case(
                record(
                    field(string(), "feedback") { feedback },
                    Decision::Revise
                )
            ),
            case(record { Decision.Discard }),
            key = "kind"
        )

    @Test
    fun `union serializes empty case to discriminator-only object`() {
        assertEquals(
            Json.parseToJsonElement("""{"kind": "Save"}"""),
            Schema.decision().encodeToJsonElement(Decision.Save)
        )
    }

    @Test
    fun `union serializes case with fields alongside discriminator`() {
        assertEquals(
            Json.parseToJsonElement("""{"kind": "Revise", "feedback": "make it vegetarian"}"""),
            Schema.decision().encodeToJsonElement(Decision.Revise("make it vegetarian"))
        )
    }

    @Test
    fun `union deserializes empty case from discriminator-only object`() {
        assertEquals(
            Validation.valid(Decision.Save),
            Schema.decision().decodeFromJsonString("""{"kind": "Save"}""", Json.Default)
        )
        assertEquals(
            Validation.valid(Decision.Discard),
            Schema.decision().decodeFromJsonString("""{"kind": "Discard"}""", Json.Default)
        )
    }

    @Test
    fun `union deserializes case with fields`() {
        assertEquals(
            Validation.valid(Decision.Revise("make it vegetarian")),
            Schema.decision().decodeFromJsonString(
                """{"kind": "Revise", "feedback": "make it vegetarian"}""",
                Json.Default
            )
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
