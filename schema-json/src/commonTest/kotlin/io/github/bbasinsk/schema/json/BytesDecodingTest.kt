package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BytesDecodingTest {

    private fun parse(json: String, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
        decodeSchemaValueFromString(json, config)

    // Primitives

    @Test
    fun `null`() = assertEquals(SchemaValue.Null, parse("null"))

    @Test
    fun `true`() = assertEquals(SchemaValue.Bool(true), parse("true"))

    @Test
    fun `false`() = assertEquals(SchemaValue.Bool(false), parse("false"))

    @Test
    fun `positive integer`() = assertEquals(SchemaValue.Integer(42), parse("42"))

    @Test
    fun `negative integer`() = assertEquals(SchemaValue.Integer(-7), parse("-7"))

    @Test
    fun `zero integer`() = assertEquals(SchemaValue.Integer(0), parse("0"))

    @Test
    fun `Long MAX_VALUE`() = assertEquals(SchemaValue.Integer(Long.MAX_VALUE), parse("${Long.MAX_VALUE}"))

    @Test
    fun `Long MIN_VALUE`() = assertEquals(SchemaValue.Integer(Long.MIN_VALUE), parse("${Long.MIN_VALUE}"))

    @Test
    fun `positive decimal`() = assertEquals(SchemaValue.Decimal(3.14), parse("3.14"))

    @Test
    fun `negative decimal`() = assertEquals(SchemaValue.Decimal(-2.5), parse("-2.5"))

    @Test
    fun `zero decimal`() = assertEquals(SchemaValue.Decimal(0.0), parse("0.0"))

    @Test
    fun `scientific notation lowercase e`() = assertEquals(SchemaValue.Decimal(1e10), parse("1e10"))

    @Test
    fun `scientific notation uppercase E`() = assertEquals(SchemaValue.Decimal(1E10), parse("1E10"))

    @Test
    fun `scientific notation negative exponent`() = assertEquals(SchemaValue.Decimal(1.5e-3), parse("1.5e-3"))

    @Test
    fun `simple string`() = assertEquals(SchemaValue.Str("hello"), parse("\"hello\""))

    @Test
    fun `empty string`() = assertEquals(SchemaValue.Str(""), parse("\"\""))

    // String escaping

    @Test
    fun `escaped quote`() = assertEquals(SchemaValue.Str("say \"hi\""), parse("""  "say \"hi\""  """))

    @Test
    fun `escaped backslash`() = assertEquals(SchemaValue.Str("back\\slash"), parse(""""back\\slash""""))

    @Test
    fun `escaped newline`() = assertEquals(SchemaValue.Str("a\nb"), parse(""""a\nb""""))

    @Test
    fun `escaped tab`() = assertEquals(SchemaValue.Str("a\tb"), parse(""""a\tb""""))

    @Test
    fun `escaped carriage return`() = assertEquals(SchemaValue.Str("a\rb"), parse(""""a\rb""""))

    @Test
    fun `escaped backspace`() = assertEquals(SchemaValue.Str("a\bb"), parse(""""a\bb""""))

    @Test
    fun `escaped form feed`() = assertEquals(SchemaValue.Str("a\u000Cb"), parse(""""a\fb""""))

    @Test
    fun `escaped forward slash`() = assertEquals(SchemaValue.Str("a/b"), parse(""""a\/b""""))

    @Test
    fun `unicode escape`() = assertEquals(SchemaValue.Str("\u00e9"), parse(""""\\u00e9"""".replace("\\\\u", "\\u")))

    @Test
    fun `unicode escape null char`() = assertEquals(SchemaValue.Str("\u0000"), parse(""""\\u0000"""".replace("\\\\u", "\\u")))

    @Test
    fun `non-ascii string`() = assertEquals(SchemaValue.Str("café"), parse("\"café\""))

    @Test
    fun `emoji string`() = assertEquals(SchemaValue.Str("\uD83C\uDF89"), parse("\"\uD83C\uDF89\""))

    @Test
    fun `non-ascii after escape sequence`() = assertEquals(SchemaValue.Str("caf\né"), parse("\"caf\\né\""))

    @Test
    fun `non-ascii mixed with escapes`() = assertEquals(SchemaValue.Str("héllo\twörld"), parse("\"héllo\\twörld\""))

    // Arrays

    @Test
    fun `empty array`() = assertEquals(SchemaValue.Arr(emptyList()), parse("[]"))

    @Test
    fun `array of integers`() = assertEquals(
        SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2), SchemaValue.Integer(3))),
        parse("[1,2,3]")
    )

    @Test
    fun `nested array`() = assertEquals(
        SchemaValue.Arr(
            listOf(
                SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
                SchemaValue.Arr(listOf(SchemaValue.Integer(3)))
            )
        ),
        parse("[[1,2],[3]]")
    )

    @Test
    fun `mixed type array`() = assertEquals(
        SchemaValue.Arr(
            listOf(
                SchemaValue.Integer(1),
                SchemaValue.Str("two"),
                SchemaValue.Bool(true),
                SchemaValue.Null
            )
        ),
        parse("""[1,"two",true,null]""")
    )

    // Objects

    @Test
    fun `empty object`() = assertEquals(SchemaValue.Obj(emptyMap()), parse("{}"))

    @Test
    fun `simple object`() = assertEquals(
        SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1), "y" to SchemaValue.Integer(2))),
        parse("""{"x":1,"y":2}""")
    )

    @Test
    fun `nested object`() = assertEquals(
        SchemaValue.Obj(
            mapOf(
                "point" to SchemaValue.Obj(
                    mapOf("x" to SchemaValue.Integer(1), "y" to SchemaValue.Integer(2))
                )
            )
        ),
        parse("""{"point":{"x":1,"y":2}}""")
    )

    @Test
    fun `object with array field`() = assertEquals(
        SchemaValue.Obj(
            mapOf(
                "items" to SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2)))
            )
        ),
        parse("""{"items":[1,2]}""")
    )

    // Whitespace

    @Test
    fun `leading whitespace`() = assertEquals(SchemaValue.Integer(42), parse("  42"))

    @Test
    fun `trailing whitespace`() = assertEquals(SchemaValue.Integer(42), parse("42  "))

    @Test
    fun `newlines in object`() = assertEquals(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Integer(2))),
        parse("{\n  \"a\": 1,\n  \"b\": 2\n}")
    )

    // Lenient trailing commas

    @Test
    fun `trailing comma in array`() {
        val config = JsonDecodingConfig(allowTrailingCommas = true)
        assertEquals(
            SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
            parse("[1,2,]", config)
        )
    }

    @Test
    fun `trailing comma in object`() {
        val config = JsonDecodingConfig(allowTrailingCommas = true)
        assertEquals(
            SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1))),
            parse("""{"a":1,}""", config)
        )
    }

    @Test
    fun `trailing comma rejected in strict mode`() {
        assertFailsWith<IllegalArgumentException> {
            parse("[1,2,]")
        }
    }

    // Lenient comments

    @Test
    fun `comment before value`() {
        val config = JsonDecodingConfig(allowComments = true)
        assertEquals(SchemaValue.Integer(42), parse("// comment\n42", config))
    }

    @Test
    fun `comment after value in object`() {
        val config = JsonDecodingConfig(allowComments = true)
        assertEquals(
            SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1))),
            parse("""{"a":1}// trailing""", config)
        )
    }

    @Test
    fun `comment rejected in strict mode`() {
        assertFailsWith<IllegalArgumentException> {
            parse("// comment\n42")
        }
    }

    // Errors

    @Test
    fun `empty input`() {
        assertFailsWith<IllegalArgumentException> {
            parse("")
        }
    }

    @Test
    fun `whitespace only`() {
        assertFailsWith<IllegalArgumentException> {
            parse("   ")
        }
    }

    @Test
    fun `unterminated string`() {
        assertFailsWith<IllegalArgumentException> {
            parse("\"hello")
        }
    }

    @Test
    fun `unterminated array`() {
        assertFailsWith<IllegalArgumentException> {
            parse("[1,2")
        }
    }

    @Test
    fun `unterminated object`() {
        assertFailsWith<IllegalArgumentException> {
            parse("""{"a":1""")
        }
    }

    @Test
    fun `invalid token`() {
        assertFailsWith<IllegalArgumentException> {
            parse("undefined")
        }
    }

    @Test
    fun `trailing content rejected`() {
        assertFailsWith<IllegalArgumentException> {
            parse("42 garbage")
        }
    }

    @Test
    fun `multiple values rejected`() {
        assertFailsWith<IllegalArgumentException> {
            parse("null null")
        }
    }

    // Round-trip

    @Test
    fun `nested structure round-trip`() {
        val json = """{"type":"Branch","left":{"type":"Leaf","value":1},"right":{"type":"Branch","left":{"type":"Leaf","value":2},"right":{"type":"Leaf","value":3}}}"""
        val parsed = parse(json)
        val expected = SchemaValue.Obj(
            mapOf(
                "type" to SchemaValue.Str("Branch"),
                "left" to SchemaValue.Obj(
                    mapOf("type" to SchemaValue.Str("Leaf"), "value" to SchemaValue.Integer(1))
                ),
                "right" to SchemaValue.Obj(
                    mapOf(
                        "type" to SchemaValue.Str("Branch"),
                        "left" to SchemaValue.Obj(
                            mapOf("type" to SchemaValue.Str("Leaf"), "value" to SchemaValue.Integer(2))
                        ),
                        "right" to SchemaValue.Obj(
                            mapOf("type" to SchemaValue.Str("Leaf"), "value" to SchemaValue.Integer(3))
                        )
                    )
                )
            )
        )
        assertEquals(expected, parsed)
    }
}
