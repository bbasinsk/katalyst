package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SchemaValueJsonEncodingTest {

    // -- Primitives --

    @Test
    fun `null encodes to null`() {
        assertEquals("null", SchemaValue.Null.encodeToJsonString())
    }

    @Test
    fun `bool true encodes to true`() {
        assertEquals("true", SchemaValue.Bool(true).encodeToJsonString())
    }

    @Test
    fun `bool false encodes to false`() {
        assertEquals("false", SchemaValue.Bool(false).encodeToJsonString())
    }

    @Test
    fun `integer encodes to number`() {
        assertEquals("42", SchemaValue.Number("42").encodeToJsonString())
    }

    @Test
    fun `integer zero`() {
        assertEquals("0", SchemaValue.Number("0").encodeToJsonString())
    }

    @Test
    fun `integer negative`() {
        assertEquals("-1", SchemaValue.Number("-1").encodeToJsonString())
    }

    @Test
    fun `long max value`() {
        assertEquals(Long.MAX_VALUE.toString(), SchemaValue.Number(Long.MAX_VALUE.toString()).encodeToJsonString())
    }

    @Test
    fun `long min value`() {
        assertEquals(Long.MIN_VALUE.toString(), SchemaValue.Number(Long.MIN_VALUE.toString()).encodeToJsonString())
    }

    @Test
    fun `decimal encodes to number`() {
        assertEquals("3.14", SchemaValue.Number("3.14").encodeToJsonString())
    }

    @Test
    fun `string encodes with quotes`() {
        assertEquals("\"hello\"", SchemaValue.Str("hello").encodeToJsonString())
    }

    @Test
    fun `empty string`() {
        assertEquals("\"\"", SchemaValue.Str("").encodeToJsonString())
    }

    // -- String escaping --

    @Test
    fun `string escapes quotes`() {
        assertEquals("\"say \\\"hi\\\"\"", SchemaValue.Str("say \"hi\"").encodeToJsonString())
    }

    @Test
    fun `string escapes backslash`() {
        assertEquals("\"a\\\\b\"", SchemaValue.Str("a\\b").encodeToJsonString())
    }

    @Test
    fun `string escapes newline`() {
        assertEquals("\"a\\nb\"", SchemaValue.Str("a\nb").encodeToJsonString())
    }

    @Test
    fun `string escapes carriage return`() {
        assertEquals("\"a\\rb\"", SchemaValue.Str("a\rb").encodeToJsonString())
    }

    @Test
    fun `string escapes tab`() {
        assertEquals("\"a\\tb\"", SchemaValue.Str("a\tb").encodeToJsonString())
    }

    @Test
    fun `string escapes backspace`() {
        assertEquals("\"a\\bb\"", SchemaValue.Str("a\bb").encodeToJsonString())
    }

    @Test
    fun `string escapes form feed`() {
        assertEquals("\"a\\fb\"", SchemaValue.Str("a\u000Cb").encodeToJsonString())
    }

    @Test
    fun `string escapes control characters`() {
        assertEquals("\"\\u0000\"", SchemaValue.Str("\u0000").encodeToJsonString())
        assertEquals("\"\\u001f\"", SchemaValue.Str("\u001F").encodeToJsonString())
    }

    // -- Empty containers --

    @Test
    fun `empty array`() {
        assertEquals("[]", SchemaValue.Arr(emptyList()).encodeToJsonString())
    }

    @Test
    fun `empty object`() {
        assertEquals("{}", SchemaValue.Obj(emptyMap()).encodeToJsonString())
    }

    // -- Arrays --

    @Test
    fun `array of integers compact`() {
        assertEquals(
            "[1,2,3]",
            SchemaValue.Arr(
                listOf(SchemaValue.Number("1"), SchemaValue.Number("2"), SchemaValue.Number("3"))
            ).encodeToJsonString()
        )
    }

    // -- Objects --

    @Test
    fun `object compact`() {
        assertEquals(
            "{\"a\":1,\"b\":\"x\"}",
            SchemaValue.Obj(
                mapOf("a" to SchemaValue.Number("1"), "b" to SchemaValue.Str("x"))
            ).encodeToJsonString()
        )
    }

    // -- Nested structures --

    @Test
    fun `array of objects compact`() {
        assertEquals(
            "[{\"x\":1},{\"x\":2}]",
            SchemaValue.Arr(
                listOf(
                    SchemaValue.Obj(mapOf("x" to SchemaValue.Number("1"))),
                    SchemaValue.Obj(mapOf("x" to SchemaValue.Number("2")))
                )
            ).encodeToJsonString()
        )
    }

    @Test
    fun `object with nested array compact`() {
        assertEquals(
            "{\"items\":[1,2],\"name\":\"test\"}",
            SchemaValue.Obj(
                mapOf(
                    "items" to SchemaValue.Arr(listOf(SchemaValue.Number("1"), SchemaValue.Number("2"))),
                    "name" to SchemaValue.Str("test")
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
            SchemaValue.Obj(
                mapOf("a" to SchemaValue.Number("1"), "b" to SchemaValue.Str("x"))
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `array pretty`() {
        assertEquals(
            "[\n  1,\n  2,\n  3\n]",
            SchemaValue.Arr(
                listOf(SchemaValue.Number("1"), SchemaValue.Number("2"), SchemaValue.Number("3"))
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `nested structure pretty`() {
        assertEquals(
            "{\n  \"items\": [\n    1,\n    2\n  ],\n  \"name\": \"test\"\n}",
            SchemaValue.Obj(
                mapOf(
                    "items" to SchemaValue.Arr(listOf(SchemaValue.Number("1"), SchemaValue.Number("2"))),
                    "name" to SchemaValue.Str("test")
                )
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `empty containers pretty`() {
        assertEquals("[]", SchemaValue.Arr(emptyList()).encodeToJsonString(prettyConfig))
        assertEquals("{}", SchemaValue.Obj(emptyMap()).encodeToJsonString(prettyConfig))
    }

    // -- Special floats: not JSON numbers, unconstructible --

    @Test
    fun `NaN is not constructible`() {
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number.of(Double.NaN) }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number("NaN") }
    }

    @Test
    fun `Infinity is not constructible`() {
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number.of(Double.POSITIVE_INFINITY) }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number.of(Double.NEGATIVE_INFINITY) }
    }

    // -- Literal fidelity --

    @Test
    fun `number literal round-trips digit-exact`() {
        assertEquals("12345678901234567890", SchemaValue.Number("12345678901234567890").encodeToJsonString())
        assertEquals("3.141592653589793238462643", SchemaValue.Number("3.141592653589793238462643").encodeToJsonString())
        assertEquals("1E10", SchemaValue.Number("1E10").encodeToJsonString())
        assertEquals("-0.5", SchemaValue.Number("-0.5").encodeToJsonString())
    }

    @Test
    fun `invalid number literals are rejected at construction`() {
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number("007") }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number("1.") }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number(".5") }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number("-") }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number("1e") }
        assertFailsWith<IllegalArgumentException> { SchemaValue.Number("") }
    }
}
