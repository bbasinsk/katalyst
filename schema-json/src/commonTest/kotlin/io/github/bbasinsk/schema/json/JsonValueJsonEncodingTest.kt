package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.JsonValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class JsonValueJsonEncodingTest {

    // -- Primitives --

    @Test
    fun `null encodes to null`() {
        assertEquals("null", JsonValue.Null.encodeToJsonString())
    }

    @Test
    fun `bool true encodes to true`() {
        assertEquals("true", JsonValue.Bool(true).encodeToJsonString())
    }

    @Test
    fun `bool false encodes to false`() {
        assertEquals("false", JsonValue.Bool(false).encodeToJsonString())
    }

    @Test
    fun `integer encodes to number`() {
        assertEquals("42", JsonValue.Number("42").encodeToJsonString())
    }

    @Test
    fun `integer zero`() {
        assertEquals("0", JsonValue.Number("0").encodeToJsonString())
    }

    @Test
    fun `integer negative`() {
        assertEquals("-1", JsonValue.Number("-1").encodeToJsonString())
    }

    @Test
    fun `long max value`() {
        assertEquals(Long.MAX_VALUE.toString(), JsonValue.Number(Long.MAX_VALUE.toString()).encodeToJsonString())
    }

    @Test
    fun `long min value`() {
        assertEquals(Long.MIN_VALUE.toString(), JsonValue.Number(Long.MIN_VALUE.toString()).encodeToJsonString())
    }

    @Test
    fun `decimal encodes to number`() {
        assertEquals("3.14", JsonValue.Number("3.14").encodeToJsonString())
    }

    @Test
    fun `string encodes with quotes`() {
        assertEquals("\"hello\"", JsonValue.Str("hello").encodeToJsonString())
    }

    @Test
    fun `empty string`() {
        assertEquals("\"\"", JsonValue.Str("").encodeToJsonString())
    }

    // -- String escaping --

    @Test
    fun `string escapes quotes`() {
        assertEquals("\"say \\\"hi\\\"\"", JsonValue.Str("say \"hi\"").encodeToJsonString())
    }

    @Test
    fun `string escapes backslash`() {
        assertEquals("\"a\\\\b\"", JsonValue.Str("a\\b").encodeToJsonString())
    }

    @Test
    fun `string escapes newline`() {
        assertEquals("\"a\\nb\"", JsonValue.Str("a\nb").encodeToJsonString())
    }

    @Test
    fun `string escapes carriage return`() {
        assertEquals("\"a\\rb\"", JsonValue.Str("a\rb").encodeToJsonString())
    }

    @Test
    fun `string escapes tab`() {
        assertEquals("\"a\\tb\"", JsonValue.Str("a\tb").encodeToJsonString())
    }

    @Test
    fun `string escapes backspace`() {
        assertEquals("\"a\\bb\"", JsonValue.Str("a\bb").encodeToJsonString())
    }

    @Test
    fun `string escapes form feed`() {
        assertEquals("\"a\\fb\"", JsonValue.Str("a\u000Cb").encodeToJsonString())
    }

    @Test
    fun `string escapes control characters`() {
        assertEquals("\"\\u0000\"", JsonValue.Str("\u0000").encodeToJsonString())
        assertEquals("\"\\u001f\"", JsonValue.Str("\u001F").encodeToJsonString())
    }

    // -- Empty containers --

    @Test
    fun `empty array`() {
        assertEquals("[]", JsonValue.Arr(emptyList()).encodeToJsonString())
    }

    @Test
    fun `empty object`() {
        assertEquals("{}", JsonValue.Obj(emptyMap()).encodeToJsonString())
    }

    // -- Arrays --

    @Test
    fun `array of integers compact`() {
        assertEquals(
            "[1,2,3]",
            JsonValue.Arr(
                listOf(JsonValue.Number("1"), JsonValue.Number("2"), JsonValue.Number("3"))
            ).encodeToJsonString()
        )
    }

    // -- Objects --

    @Test
    fun `object compact`() {
        assertEquals(
            "{\"a\":1,\"b\":\"x\"}",
            JsonValue.Obj(
                mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Str("x"))
            ).encodeToJsonString()
        )
    }

    // -- Nested structures --

    @Test
    fun `array of objects compact`() {
        assertEquals(
            "[{\"x\":1},{\"x\":2}]",
            JsonValue.Arr(
                listOf(
                    JsonValue.Obj(mapOf("x" to JsonValue.Number("1"))),
                    JsonValue.Obj(mapOf("x" to JsonValue.Number("2")))
                )
            ).encodeToJsonString()
        )
    }

    @Test
    fun `object with nested array compact`() {
        assertEquals(
            "{\"items\":[1,2],\"name\":\"test\"}",
            JsonValue.Obj(
                mapOf(
                    "items" to JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"))),
                    "name" to JsonValue.Str("test")
                )
            ).encodeToJsonString()
        )
    }

    // -- Pretty printing --

    private val prettyConfig = JsonEncodingConfig(printConfig = JsonEncodingConfig.PrintConfig.pretty())

    @Test
    fun `object pretty`() {
        assertEquals(
            "{\n  \"a\": 1,\n  \"b\": \"x\"\n}",
            JsonValue.Obj(
                mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Str("x"))
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `array pretty`() {
        assertEquals(
            "[\n  1,\n  2,\n  3\n]",
            JsonValue.Arr(
                listOf(JsonValue.Number("1"), JsonValue.Number("2"), JsonValue.Number("3"))
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `nested structure pretty`() {
        assertEquals(
            "{\n  \"items\": [\n    1,\n    2\n  ],\n  \"name\": \"test\"\n}",
            JsonValue.Obj(
                mapOf(
                    "items" to JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"))),
                    "name" to JsonValue.Str("test")
                )
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `empty containers pretty`() {
        assertEquals("[]", JsonValue.Arr(emptyList()).encodeToJsonString(prettyConfig))
        assertEquals("{}", JsonValue.Obj(emptyMap()).encodeToJsonString(prettyConfig))
    }

    // -- Special floats: not JSON numbers, unconstructible --

    @Test
    fun `NaN is not constructible`() {
        assertFailsWith<IllegalArgumentException> { JsonValue.Number.of(Double.NaN) }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number("NaN") }
    }

    @Test
    fun `Infinity is not constructible`() {
        assertFailsWith<IllegalArgumentException> { JsonValue.Number.of(Double.POSITIVE_INFINITY) }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number.of(Double.NEGATIVE_INFINITY) }
    }

    // -- Literal fidelity --

    @Test
    fun `number literal round-trips digit-exact`() {
        assertEquals("12345678901234567890", JsonValue.Number("12345678901234567890").encodeToJsonString())
        assertEquals("3.141592653589793238462643", JsonValue.Number("3.141592653589793238462643").encodeToJsonString())
        assertEquals("1E10", JsonValue.Number("1E10").encodeToJsonString())
        assertEquals("-0.5", JsonValue.Number("-0.5").encodeToJsonString())
    }

    @Test
    fun `invalid number literals are rejected at construction`() {
        assertFailsWith<IllegalArgumentException> { JsonValue.Number("007") }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number("1.") }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number(".5") }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number("-") }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number("1e") }
        assertFailsWith<IllegalArgumentException> { JsonValue.Number("") }
    }
}
