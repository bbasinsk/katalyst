package io.github.bbasinsk.schema.json.kotlinx

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.validation.Validation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DynamicSerdeTest {

    private val schema = Schema.dynamic()

    private fun roundTrip(value: SchemaValue) {
        val encoded = schema.encodeToJsonString(value)
        val decoded = schema.decodeFromJsonString(encoded)
        assertEquals(Validation.valid(value), decoded)
    }

    private fun assertEncodingsMatch(value: SchemaValue, config: JsonEncodingConfig = JsonEncodingConfig()) {
        val fromElement = schema.encodeToJsonElement(value, config).toString()
        val fromSink = schema.encodeToJsonString(value, config)
        assertEquals(fromElement, fromSink)
    }

    // Round-trip all 7 variants

    @Test
    fun `null round-trip`() = roundTrip(SchemaValue.Null)

    @Test
    fun `bool true round-trip`() = roundTrip(SchemaValue.Bool(true))

    @Test
    fun `bool false round-trip`() = roundTrip(SchemaValue.Bool(false))

    @Test
    fun `integer round-trip`() = roundTrip(SchemaValue.Integer(42))

    @Test
    fun `negative integer round-trip`() = roundTrip(SchemaValue.Integer(-100))

    @Test
    fun `decimal round-trip`() = roundTrip(SchemaValue.Decimal(3.14))

    @Test
    fun `string round-trip`() = roundTrip(SchemaValue.Str("hello"))

    @Test
    fun `empty string round-trip`() = roundTrip(SchemaValue.Str(""))

    @Test
    fun `empty array round-trip`() = roundTrip(SchemaValue.Arr(emptyList()))

    @Test
    fun `array round-trip`() = roundTrip(
        SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Str("two"), SchemaValue.Bool(true)))
    )

    @Test
    fun `empty object round-trip`() = roundTrip(SchemaValue.Obj(emptyMap()))

    @Test
    fun `object round-trip`() = roundTrip(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Str("hello")))
    )

    // Nested structures

    @Test
    fun `nested object with array round-trip`() = roundTrip(
        SchemaValue.Obj(
            mapOf(
                "name" to SchemaValue.Str("test"),
                "items" to SchemaValue.Arr(
                    listOf(
                        SchemaValue.Obj(mapOf("id" to SchemaValue.Integer(1))),
                        SchemaValue.Obj(mapOf("id" to SchemaValue.Integer(2)))
                    )
                ),
                "meta" to SchemaValue.Null
            )
        )
    )

    @Test
    fun `array of objects round-trip`() = roundTrip(
        SchemaValue.Arr(
            listOf(
                SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1), "y" to SchemaValue.Integer(2))),
                SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(3), "y" to SchemaValue.Integer(4)))
            )
        )
    )

    // Number type preservation

    @Test
    fun `integer 42 decodes as Integer not Decimal`() {
        val decoded = schema.decodeFromJsonString("42")
        assertEquals(Validation.valid(SchemaValue.Integer(42)), decoded)
    }

    @Test
    fun `decimal 3-14 decodes as Decimal`() {
        val decoded = schema.decodeFromJsonString("3.14")
        assertEquals(Validation.valid(SchemaValue.Decimal(3.14)), decoded)
    }

    @Test
    fun `scientific notation decodes as Decimal`() {
        val decoded = schema.decodeFromJsonString("1e10")
        assertEquals(Validation.valid(SchemaValue.Decimal(1e10)), decoded)
    }

    // Encoding output

    @Test
    fun `null encodes to null`() {
        assertEquals("null", schema.encodeToJsonString(SchemaValue.Null))
    }

    @Test
    fun `integer encodes without decimal point`() {
        assertEquals("42", schema.encodeToJsonString(SchemaValue.Integer(42)))
    }

    @Test
    fun `decimal encodes with decimal point`() {
        assertEquals("3.14", schema.encodeToJsonString(SchemaValue.Decimal(3.14)))
    }

    // Dynamic as record field

    data class Wrapper(val name: String, val data: SchemaValue)

    private val wrapperSchema = with(Schema) {
        record(
            field(string(), "name") { name },
            field(dynamic(), "data") { data },
            ::Wrapper
        )
    }

    @Test
    fun `dynamic as record field round-trip`() {
        val value = Wrapper("test", SchemaValue.Obj(mapOf("key" to SchemaValue.Integer(42))))
        val encoded = wrapperSchema.encodeToJsonString(value)
        val decoded = wrapperSchema.decodeFromJsonString(encoded)
        assertEquals(Validation.valid(value), decoded)
    }

    // Dynamic in collections

    @Test
    fun `list of dynamic round-trip`() {
        val listSchema = Schema.list(Schema.dynamic())
        val value = listOf(SchemaValue.Integer(1), SchemaValue.Str("two"), SchemaValue.Null)
        val encoded = listSchema.encodeToJsonString(value)
        val decoded = listSchema.decodeFromJsonString(encoded)
        assertEquals(Validation.valid(value), decoded)
    }

    // Optional dynamic

    @Test
    fun `optional dynamic present round-trip`() {
        val optSchema = Schema.dynamic().optional()
        val value = SchemaValue.Str("hello")
        val encoded = optSchema.encodeToJsonString(value)
        val decoded = optSchema.decodeFromJsonString(encoded)
        assertEquals(Validation.valid(value), decoded)
    }

    @Test
    fun `optional dynamic null round-trip`() {
        val optSchema = Schema.dynamic().optional()
        val encoded = optSchema.encodeToJsonString(null)
        val decoded = optSchema.decodeFromJsonString(encoded)
        assertEquals(Validation.valid(null), decoded)
    }

    @Test
    fun `optional dynamic field omitted when null with explicitNulls false`() {
        data class OptWrapper(val name: String, val data: SchemaValue?)

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
            optWrapperSchema.encodeToJsonString(OptWrapper("test", SchemaValue.Null), config)
        )
    }

    // Sink encoding matches element encoding

    @Test
    fun `sink encoding matches - null`() = assertEncodingsMatch(SchemaValue.Null)

    @Test
    fun `sink encoding matches - bool`() = assertEncodingsMatch(SchemaValue.Bool(true))

    @Test
    fun `sink encoding matches - integer`() = assertEncodingsMatch(SchemaValue.Integer(42))

    @Test
    fun `sink encoding matches - decimal`() = assertEncodingsMatch(SchemaValue.Decimal(3.14))

    @Test
    fun `sink encoding matches - string`() = assertEncodingsMatch(SchemaValue.Str("hello"))

    @Test
    fun `sink encoding matches - string with escapes`() = assertEncodingsMatch(SchemaValue.Str("say \"hi\"\nand\\tab"))

    @Test
    fun `sink encoding matches - array`() = assertEncodingsMatch(
        SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2)))
    )

    @Test
    fun `sink encoding matches - object`() = assertEncodingsMatch(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Str("two")))
    )

    @Test
    fun `sink encoding matches - nested`() = assertEncodingsMatch(
        SchemaValue.Obj(
            mapOf(
                "arr" to SchemaValue.Arr(listOf(SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1))))),
                "nil" to SchemaValue.Null
            )
        )
    )

    // Pretty print

    private val pretty = JsonEncodingConfig(printConfig = JsonEncodingConfig.PrintConfig.pretty())

    private fun assertPrettyPrint(value: SchemaValue, expected: String) {
        assertEquals(expected, schema.encodeToJsonString(value, pretty))
    }

    @Test
    fun `pretty - object`() = assertPrettyPrint(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Str("two"))),
        "{\n  \"a\": 1,\n  \"b\": \"two\"\n}"
    )

    @Test
    fun `pretty - nested`() = assertPrettyPrint(
        SchemaValue.Obj(
            mapOf(
                "items" to SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
                "meta" to SchemaValue.Obj(mapOf("key" to SchemaValue.Str("val")))
            )
        ),
        "{\n  \"items\": [\n    1,\n    2\n  ],\n  \"meta\": {\n    \"key\": \"val\"\n  }\n}"
    )

    @Test
    fun `pretty - primitives unchanged`() {
        assertEquals("42", schema.encodeToJsonString(SchemaValue.Integer(42), pretty))
        assertEquals("\"hello\"", schema.encodeToJsonString(SchemaValue.Str("hello"), pretty))
        assertEquals("true", schema.encodeToJsonString(SchemaValue.Bool(true), pretty))
        assertEquals("null", schema.encodeToJsonString(SchemaValue.Null, pretty))
    }

    @Test
    fun `pretty - empty array`() = assertPrettyPrint(SchemaValue.Arr(emptyList()), "[]")

    @Test
    fun `pretty - empty object`() = assertPrettyPrint(SchemaValue.Obj(emptyMap()), "{}")

    @Test
    fun `pretty - nested object round-trips`() {
        val value = SchemaValue.Obj(
            mapOf(
                "items" to SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
                "meta" to SchemaValue.Obj(mapOf("key" to SchemaValue.Str("val")))
            )
        )
        val prettyJson = schema.encodeToJsonString(value, pretty)
        val decoded = schema.decodeFromJsonString(prettyJson)
        assertEquals(Validation.valid(value), decoded)
    }

    // Edge cases

    @Test
    fun `long max value round-trip`() = roundTrip(SchemaValue.Integer(Long.MAX_VALUE))

    @Test
    fun `long min value round-trip`() = roundTrip(SchemaValue.Integer(Long.MIN_VALUE))

    @Test
    fun `zero as integer`() {
        val decoded = schema.decodeFromJsonString("0")
        assertEquals(Validation.valid(SchemaValue.Integer(0)), decoded)
    }

    @Test
    fun `zero as decimal`() {
        val decoded = schema.decodeFromJsonString("0.0")
        assertEquals(Validation.valid(SchemaValue.Decimal(0.0)), decoded)
    }

    @Test
    fun `null inside array round-trip`() = roundTrip(
        SchemaValue.Arr(listOf(SchemaValue.Null, SchemaValue.Integer(1)))
    )

    @Test
    fun `special characters in object keys round-trip`() = roundTrip(
        SchemaValue.Obj(
            mapOf(
                "key with spaces" to SchemaValue.Integer(1),
                "key\"with\"quotes" to SchemaValue.Integer(2),
                "key\nwith\nnewlines" to SchemaValue.Integer(3),
                "" to SchemaValue.Integer(4)
            )
        )
    )

    @Test
    fun `null as record field value round-trip`() {
        val value = Wrapper("test", SchemaValue.Null)
        val encoded = wrapperSchema.encodeToJsonString(value)
        val decoded = wrapperSchema.decodeFromJsonString(encoded)
        assertEquals(Validation.valid(value), decoded)
    }

    @Test
    fun `NaN decimal encoding throws by default`() {
        val result = runCatching { schema.encodeToJsonString(SchemaValue.Decimal(Double.NaN)) }
        assertTrue(result.isFailure, "Expected encoding NaN to throw")
    }

    @Test
    fun `Positive Infinity decimal encoding throws by default`() {
        val result = runCatching { schema.encodeToJsonString(SchemaValue.Decimal(Double.POSITIVE_INFINITY)) }
        assertTrue(result.isFailure, "Expected encoding +Infinity to throw")
    }

    @Test
    fun `Negative Infinity decimal encoding throws by default`() {
        val result = runCatching { schema.encodeToJsonString(SchemaValue.Decimal(Double.NEGATIVE_INFINITY)) }
        assertTrue(result.isFailure, "Expected encoding -Infinity to throw")
    }

    @Test
    fun `NaN decimal encoding succeeds with allowSpecialFloatingPointValues`() {
        val config = JsonEncodingConfig(allowSpecialFloatingPointValues = true)
        val result = schema.encodeToJsonString(SchemaValue.Decimal(Double.NaN), config)
        assertEquals("NaN", result)
    }

    @Test
    fun `Infinity decimal encoding succeeds with allowSpecialFloatingPointValues`() {
        val config = JsonEncodingConfig(allowSpecialFloatingPointValues = true)
        assertEquals("Infinity", schema.encodeToJsonString(SchemaValue.Decimal(Double.POSITIVE_INFINITY), config))
        assertEquals("-Infinity", schema.encodeToJsonString(SchemaValue.Decimal(Double.NEGATIVE_INFINITY), config))
    }

}
