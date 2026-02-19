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
        assertEquals("42", SchemaValue.Integer(42L).encodeToJsonString())
    }

    @Test
    fun `integer zero`() {
        assertEquals("0", SchemaValue.Integer(0L).encodeToJsonString())
    }

    @Test
    fun `integer negative`() {
        assertEquals("-1", SchemaValue.Integer(-1L).encodeToJsonString())
    }

    @Test
    fun `long max value`() {
        assertEquals(Long.MAX_VALUE.toString(), SchemaValue.Integer(Long.MAX_VALUE).encodeToJsonString())
    }

    @Test
    fun `long min value`() {
        assertEquals(Long.MIN_VALUE.toString(), SchemaValue.Integer(Long.MIN_VALUE).encodeToJsonString())
    }

    @Test
    fun `decimal encodes to number`() {
        assertEquals("3.14", SchemaValue.Decimal(3.14).encodeToJsonString())
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
                listOf(SchemaValue.Integer(1), SchemaValue.Integer(2), SchemaValue.Integer(3))
            ).encodeToJsonString()
        )
    }

    // -- Objects --

    @Test
    fun `object compact`() {
        assertEquals(
            "{\"a\":1,\"b\":\"x\"}",
            SchemaValue.Obj(
                mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Str("x"))
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
                    SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1))),
                    SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(2)))
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
                    "items" to SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
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
                mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Str("x"))
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `array pretty`() {
        assertEquals(
            "[\n  1,\n  2,\n  3\n]",
            SchemaValue.Arr(
                listOf(SchemaValue.Integer(1), SchemaValue.Integer(2), SchemaValue.Integer(3))
            ).encodeToJsonString(prettyConfig)
        )
    }

    @Test
    fun `nested structure pretty`() {
        assertEquals(
            "{\n  \"items\": [\n    1,\n    2\n  ],\n  \"name\": \"test\"\n}",
            SchemaValue.Obj(
                mapOf(
                    "items" to SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
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

    // -- Special floats --

    @Test
    fun `NaN throws by default`() {
        assertFailsWith<IllegalArgumentException> {
            SchemaValue.Decimal(Double.NaN).encodeToJsonString()
        }
    }

    @Test
    fun `Infinity throws by default`() {
        assertFailsWith<IllegalArgumentException> {
            SchemaValue.Decimal(Double.POSITIVE_INFINITY).encodeToJsonString()
        }
    }

    @Test
    fun `negative Infinity throws by default`() {
        assertFailsWith<IllegalArgumentException> {
            SchemaValue.Decimal(Double.NEGATIVE_INFINITY).encodeToJsonString()
        }
    }

    @Test
    fun `NaN allowed with config`() {
        val config = JsonEncodingConfig(allowSpecialFloatingPointValues = true)
        assertEquals("NaN", SchemaValue.Decimal(Double.NaN).encodeToJsonString(config))
    }

    @Test
    fun `Infinity allowed with config`() {
        val config = JsonEncodingConfig(allowSpecialFloatingPointValues = true)
        assertEquals("Infinity", SchemaValue.Decimal(Double.POSITIVE_INFINITY).encodeToJsonString(config))
    }

    @Test
    fun `negative Infinity allowed with config`() {
        val config = JsonEncodingConfig(allowSpecialFloatingPointValues = true)
        assertEquals("-Infinity", SchemaValue.Decimal(Double.NEGATIVE_INFINITY).encodeToJsonString(config))
    }
}
