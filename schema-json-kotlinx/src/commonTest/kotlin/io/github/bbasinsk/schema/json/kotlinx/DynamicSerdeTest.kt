package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.JsonValue
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.JsonEncodingConfig
import io.github.bbasinsk.schema.json.encodeToJsonString
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DynamicSerdeTest {

    private val schema = Schema.dynamic()

    private fun roundTrip(value: JsonValue) {
        val encoded = schema.encodeToJsonString(value)
        val decoded = schema.decodeFromJsonString(encoded, Json.Default)
        assertEquals(Validation.valid(value), decoded)
    }

    private fun assertEncodingsMatch(value: JsonValue, config: JsonEncodingConfig = JsonEncodingConfig()) {
        val fromElement = schema.encodeToJsonElement(value, config).toString()
        val fromSink = schema.encodeToJsonString(value, config)
        assertEquals(fromElement, fromSink)
    }

    // Round-trip all 7 variants

    @Test
    fun `null round-trip`() = roundTrip(JsonValue.Null)

    @Test
    fun `bool true round-trip`() = roundTrip(JsonValue.Bool(true))

    @Test
    fun `bool false round-trip`() = roundTrip(JsonValue.Bool(false))

    @Test
    fun `integer round-trip`() = roundTrip(JsonValue.Number("42"))

    @Test
    fun `negative integer round-trip`() = roundTrip(JsonValue.Number("-100"))

    @Test
    fun `decimal round-trip`() = roundTrip(JsonValue.Number("3.14"))

    @Test
    fun `string round-trip`() = roundTrip(JsonValue.Str("hello"))

    @Test
    fun `empty string round-trip`() = roundTrip(JsonValue.Str(""))

    @Test
    fun `empty array round-trip`() = roundTrip(JsonValue.Arr(emptyList()))

    @Test
    fun `array round-trip`() = roundTrip(
        JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Str("two"), JsonValue.Bool(true)))
    )

    @Test
    fun `empty object round-trip`() = roundTrip(JsonValue.Obj(emptyMap()))

    @Test
    fun `object round-trip`() = roundTrip(
        JsonValue.Obj(mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Str("hello")))
    )

    // Nested structures

    @Test
    fun `nested object with array round-trip`() = roundTrip(
        JsonValue.Obj(
            mapOf(
                "name" to JsonValue.Str("test"),
                "items" to JsonValue.Arr(
                    listOf(
                        JsonValue.Obj(mapOf("id" to JsonValue.Number("1"))),
                        JsonValue.Obj(mapOf("id" to JsonValue.Number("2")))
                    )
                ),
                "meta" to JsonValue.Null
            )
        )
    )

    @Test
    fun `array of objects round-trip`() = roundTrip(
        JsonValue.Arr(
            listOf(
                JsonValue.Obj(mapOf("x" to JsonValue.Number("1"), "y" to JsonValue.Number("2"))),
                JsonValue.Obj(mapOf("x" to JsonValue.Number("3"), "y" to JsonValue.Number("4")))
            )
        )
    )

    // Number type preservation

    @Test
    fun `integer 42 decodes as Integer not Decimal`() {
        val decoded = schema.decodeFromJsonString("42", Json.Default)
        assertEquals(Validation.valid(JsonValue.Number("42")), decoded)
    }

    @Test
    fun `decimal 3-14 decodes as Decimal`() {
        val decoded = schema.decodeFromJsonString("3.14", Json.Default)
        assertEquals(Validation.valid(JsonValue.Number("3.14")), decoded)
    }

    @Test
    fun `scientific notation decodes as Decimal`() {
        val decoded = schema.decodeFromJsonString("1e10", Json.Default)
        assertEquals(Validation.valid(JsonValue.Number("1e10")), decoded)
    }

    // Encoding output

    @Test
    fun `null encodes to null`() {
        assertEquals("null", schema.encodeToJsonString(JsonValue.Null))
    }

    @Test
    fun `integer encodes without decimal point`() {
        assertEquals("42", schema.encodeToJsonString(JsonValue.Number("42")))
    }

    @Test
    fun `decimal encodes with decimal point`() {
        assertEquals("3.14", schema.encodeToJsonString(JsonValue.Number("3.14")))
    }

    // Dynamic as record field

    data class Wrapper(val name: String, val data: JsonValue)

    private val wrapperSchema = with(Schema) {
        record(
            field(string(), "name") { name },
            field(dynamic(), "data") { data },
            ::Wrapper
        )
    }

    @Test
    fun `dynamic as record field round-trip`() {
        val value = Wrapper("test", JsonValue.Obj(mapOf("key" to JsonValue.Number("42"))))
        val encoded = wrapperSchema.encodeToJsonString(value)
        val decoded = wrapperSchema.decodeFromJsonString(encoded, Json.Default)
        assertEquals(Validation.valid(value), decoded)
    }

    // Dynamic in collections

    @Test
    fun `list of dynamic round-trip`() {
        val listSchema = Schema.list(Schema.dynamic())
        val value = listOf(JsonValue.Number("1"), JsonValue.Str("two"), JsonValue.Null)
        val encoded = listSchema.encodeToJsonString(value)
        val decoded = listSchema.decodeFromJsonString(encoded, Json.Default)
        assertEquals(Validation.valid(value), decoded)
    }

    // Optional dynamic

    @Test
    fun `optional dynamic present round-trip`() {
        val optSchema = Schema.dynamic().optional()
        val value = JsonValue.Str("hello")
        val encoded = optSchema.encodeToJsonString(value)
        val decoded = optSchema.decodeFromJsonString(encoded, Json.Default)
        assertEquals(Validation.valid(value), decoded)
    }

    @Test
    fun `optional dynamic null round-trip`() {
        val optSchema = Schema.dynamic().optional()
        val encoded = optSchema.encodeToJsonString(null)
        val decoded = optSchema.decodeFromJsonString(encoded, Json.Default)
        assertEquals(Validation.valid(null), decoded)
    }

    @Test
    fun `optional dynamic field omitted when null with explicitNulls false`() {
        data class OptWrapper(val name: String, val data: JsonValue?)

        val optWrapperSchema = with(Schema) {
            record(
                field(string(), "name") { name },
                field(dynamic().optional(), "data") { data },
                ::OptWrapper
            )
        }
        val config = JsonEncodingConfig(explicitNulls = false)

        assertEquals(
            """{"name":"test"}""",
            optWrapperSchema.encodeToJsonString(OptWrapper("test", null), config)
        )
        assertEquals(
            """{"name":"test","data":null}""",
            optWrapperSchema.encodeToJsonString(OptWrapper("test", JsonValue.Null), config)
        )
    }

    // Sink encoding matches element encoding

    @Test
    fun `sink encoding matches - null`() = assertEncodingsMatch(JsonValue.Null)

    @Test
    fun `sink encoding matches - bool`() = assertEncodingsMatch(JsonValue.Bool(true))

    @Test
    fun `sink encoding matches - integer`() = assertEncodingsMatch(JsonValue.Number("42"))

    @Test
    fun `sink encoding matches - decimal`() = assertEncodingsMatch(JsonValue.Number("3.14"))

    @Test
    fun `sink encoding matches - string`() = assertEncodingsMatch(JsonValue.Str("hello"))

    @Test
    fun `sink encoding matches - string with escapes`() = assertEncodingsMatch(JsonValue.Str("say \"hi\"\nand\\tab"))

    @Test
    fun `sink encoding matches - array`() = assertEncodingsMatch(
        JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2")))
    )

    @Test
    fun `sink encoding matches - object`() = assertEncodingsMatch(
        JsonValue.Obj(mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Str("two")))
    )

    @Test
    fun `sink encoding matches - nested`() = assertEncodingsMatch(
        JsonValue.Obj(
            mapOf(
                "arr" to JsonValue.Arr(listOf(JsonValue.Obj(mapOf("x" to JsonValue.Number("1"))))),
                "nil" to JsonValue.Null
            )
        )
    )

    // Pretty print

    private val pretty = JsonEncodingConfig(printConfig = JsonEncodingConfig.PrintConfig.pretty())

    private fun assertPrettyPrint(value: JsonValue, expected: String) {
        assertEquals(expected, schema.encodeToJsonString(value, pretty))
    }

    @Test
    fun `pretty - object`() = assertPrettyPrint(
        JsonValue.Obj(mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Str("two"))),
        "{\n  \"a\": 1,\n  \"b\": \"two\"\n}"
    )

    @Test
    fun `pretty - nested`() = assertPrettyPrint(
        JsonValue.Obj(
            mapOf(
                "items" to JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"))),
                "meta" to JsonValue.Obj(mapOf("key" to JsonValue.Str("val")))
            )
        ),
        "{\n  \"items\": [\n    1,\n    2\n  ],\n  \"meta\": {\n    \"key\": \"val\"\n  }\n}"
    )

    @Test
    fun `pretty - primitives unchanged`() {
        assertEquals("42", schema.encodeToJsonString(JsonValue.Number("42"), pretty))
        assertEquals("\"hello\"", schema.encodeToJsonString(JsonValue.Str("hello"), pretty))
        assertEquals("true", schema.encodeToJsonString(JsonValue.Bool(true), pretty))
        assertEquals("null", schema.encodeToJsonString(JsonValue.Null, pretty))
    }

    @Test
    fun `pretty - empty array`() = assertPrettyPrint(JsonValue.Arr(emptyList()), "[]")

    @Test
    fun `pretty - empty object`() = assertPrettyPrint(JsonValue.Obj(emptyMap()), "{}")

    @Test
    fun `pretty - nested object round-trips`() {
        val value = JsonValue.Obj(
            mapOf(
                "items" to JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"))),
                "meta" to JsonValue.Obj(mapOf("key" to JsonValue.Str("val")))
            )
        )
        val prettyJson = schema.encodeToJsonString(value, pretty)
        val decoded = schema.decodeFromJsonString(prettyJson, Json.Default)
        assertEquals(Validation.valid(value), decoded)
    }

    // Edge cases

    @Test
    fun `long max value round-trip`() = roundTrip(JsonValue.Number(Long.MAX_VALUE.toString()))

    @Test
    fun `long min value round-trip`() = roundTrip(JsonValue.Number(Long.MIN_VALUE.toString()))

    @Test
    fun `zero as integer`() {
        val decoded = schema.decodeFromJsonString("0", Json.Default)
        assertEquals(Validation.valid(JsonValue.Number("0")), decoded)
    }

    @Test
    fun `zero as decimal`() {
        val decoded = schema.decodeFromJsonString("0.0", Json.Default)
        assertEquals(Validation.valid(JsonValue.Number("0.0")), decoded)
    }

    @Test
    fun `null inside array round-trip`() = roundTrip(
        JsonValue.Arr(listOf(JsonValue.Null, JsonValue.Number("1")))
    )

    @Test
    fun `special characters in object keys round-trip`() = roundTrip(
        JsonValue.Obj(
            mapOf(
                "key with spaces" to JsonValue.Number("1"),
                "key\"with\"quotes" to JsonValue.Number("2"),
                "key\nwith\nnewlines" to JsonValue.Number("3"),
                "" to JsonValue.Number("4")
            )
        )
    )

    @Test
    fun `null as record field value round-trip`() {
        val value = Wrapper("test", JsonValue.Null)
        val encoded = wrapperSchema.encodeToJsonString(value)
        val decoded = wrapperSchema.decodeFromJsonString(encoded, Json.Default)
        assertEquals(Validation.valid(value), decoded)
    }

    @Test
    fun `non-finite doubles are not constructible as numbers`() {
        assertTrue(runCatching { JsonValue.Number.of(Double.NaN) }.isFailure)
        assertTrue(runCatching { JsonValue.Number.of(Double.POSITIVE_INFINITY) }.isFailure)
        assertTrue(runCatching { JsonValue.Number.of(Double.NEGATIVE_INFINITY) }.isFailure)
    }

    @Test
    fun `number literal round-trips digit-exact through JsonElement`() {
        val big = JsonValue.Number("12345678901234567890")
        assertEquals("12345678901234567890", schema.encodeToJsonString(big))
        assertEquals(Validation.valid(big), schema.decodeFromJsonString("12345678901234567890", Json.Default))

        val precise = JsonValue.Number("3.141592653589793238462643")
        assertEquals("3.141592653589793238462643", schema.encodeToJsonString(precise))
        assertEquals(Validation.valid(precise), schema.decodeFromJsonString("3.141592653589793238462643", Json.Default))
    }

}
