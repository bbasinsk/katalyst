package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.JsonValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BytesDecodingTest {

    private fun parse(json: String, config: JsonDecodingConfig = JsonDecodingConfig()): JsonValue =
        decodeJsonValueFromString(json, config)

    // Primitives

    @Test
    fun `null`() = assertEquals(JsonValue.Null, parse("null"))

    @Test
    fun `true`() = assertEquals(JsonValue.Bool(true), parse("true"))

    @Test
    fun `false`() = assertEquals(JsonValue.Bool(false), parse("false"))

    @Test
    fun `positive integer`() = assertEquals(JsonValue.Number("42"), parse("42"))

    @Test
    fun `negative integer`() = assertEquals(JsonValue.Number("-7"), parse("-7"))

    @Test
    fun `zero integer`() = assertEquals(JsonValue.Number("0"), parse("0"))

    @Test
    fun `Long MAX_VALUE`() = assertEquals(JsonValue.Number(Long.MAX_VALUE.toString()), parse("${Long.MAX_VALUE}"))

    @Test
    fun `Long MIN_VALUE`() = assertEquals(JsonValue.Number(Long.MIN_VALUE.toString()), parse("${Long.MIN_VALUE}"))

    @Test
    fun `positive decimal`() = assertEquals(JsonValue.Number("3.14"), parse("3.14"))

    @Test
    fun `negative decimal`() = assertEquals(JsonValue.Number("-2.5"), parse("-2.5"))

    @Test
    fun `zero decimal`() = assertEquals(JsonValue.Number("0.0"), parse("0.0"))

    @Test
    fun `scientific notation lowercase e`() = assertEquals(JsonValue.Number("1e10"), parse("1e10"))

    @Test
    fun `scientific notation uppercase E`() = assertEquals(JsonValue.Number("1E10"), parse("1E10"))

    @Test
    fun `scientific notation negative exponent`() = assertEquals(JsonValue.Number("1.5e-3"), parse("1.5e-3"))

    @Test
    fun `integer beyond Long range keeps its digits`() =
        assertEquals(JsonValue.Number("12345678901234567890"), parse("12345678901234567890"))

    @Test
    fun `high-precision decimal keeps its digits`() =
        assertEquals(JsonValue.Number("3.141592653589793238462643"), parse("3.141592653589793238462643"))

    @Test
    fun `leading zeros are rejected`() {
        assertFailsWith<IllegalArgumentException> { parse("007") }
    }

    @Test
    fun `trailing dot is rejected`() {
        assertFailsWith<IllegalArgumentException> { parse("1.") }
    }

    @Test
    fun `bare minus is rejected`() {
        assertFailsWith<IllegalArgumentException> { parse("-") }
    }

    @Test
    fun `empty exponent is rejected`() {
        assertFailsWith<IllegalArgumentException> { parse("1e") }
    }

    @Test
    fun `simple string`() = assertEquals(JsonValue.Str("hello"), parse("\"hello\""))

    @Test
    fun `empty string`() = assertEquals(JsonValue.Str(""), parse("\"\""))

    // String escaping

    @Test
    fun `escaped quote`() = assertEquals(JsonValue.Str("say \"hi\""), parse("""  "say \"hi\""  """))

    @Test
    fun `escaped backslash`() = assertEquals(JsonValue.Str("back\\slash"), parse(""""back\\slash""""))

    @Test
    fun `escaped newline`() = assertEquals(JsonValue.Str("a\nb"), parse(""""a\nb""""))

    @Test
    fun `escaped tab`() = assertEquals(JsonValue.Str("a\tb"), parse(""""a\tb""""))

    @Test
    fun `escaped carriage return`() = assertEquals(JsonValue.Str("a\rb"), parse(""""a\rb""""))

    @Test
    fun `escaped backspace`() = assertEquals(JsonValue.Str("a\bb"), parse(""""a\bb""""))

    @Test
    fun `escaped form feed`() = assertEquals(JsonValue.Str("a\u000Cb"), parse(""""a\fb""""))

    @Test
    fun `escaped forward slash`() = assertEquals(JsonValue.Str("a/b"), parse(""""a\/b""""))

    @Test
    fun `unicode escape`() = assertEquals(JsonValue.Str("\u00e9"), parse(""""\\u00e9"""".replace("\\\\u", "\\u")))

    @Test
    fun `unicode escape null char`() = assertEquals(JsonValue.Str("\u0000"), parse(""""\\u0000"""".replace("\\\\u", "\\u")))

    @Test
    fun `non-ascii string`() = assertEquals(JsonValue.Str("café"), parse("\"café\""))

    @Test
    fun `emoji string`() = assertEquals(JsonValue.Str("\uD83C\uDF89"), parse("\"\uD83C\uDF89\""))

    @Test
    fun `non-ascii after escape sequence`() = assertEquals(JsonValue.Str("caf\né"), parse("\"caf\\né\""))

    @Test
    fun `non-ascii mixed with escapes`() = assertEquals(JsonValue.Str("héllo\twörld"), parse("\"héllo\\twörld\""))

    // Arrays

    @Test
    fun `empty array`() = assertEquals(JsonValue.Arr(emptyList()), parse("[]"))

    @Test
    fun `array of integers`() = assertEquals(
        JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"), JsonValue.Number("3"))),
        parse("[1,2,3]")
    )

    @Test
    fun `nested array`() = assertEquals(
        JsonValue.Arr(
            listOf(
                JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"))),
                JsonValue.Arr(listOf(JsonValue.Number("3")))
            )
        ),
        parse("[[1,2],[3]]")
    )

    @Test
    fun `mixed type array`() = assertEquals(
        JsonValue.Arr(
            listOf(
                JsonValue.Number("1"),
                JsonValue.Str("two"),
                JsonValue.Bool(true),
                JsonValue.Null
            )
        ),
        parse("""[1,"two",true,null]""")
    )

    // Objects

    @Test
    fun `empty object`() = assertEquals(JsonValue.Obj(emptyMap()), parse("{}"))

    @Test
    fun `simple object`() = assertEquals(
        JsonValue.Obj(mapOf("x" to JsonValue.Number("1"), "y" to JsonValue.Number("2"))),
        parse("""{"x":1,"y":2}""")
    )

    @Test
    fun `nested object`() = assertEquals(
        JsonValue.Obj(
            mapOf(
                "point" to JsonValue.Obj(
                    mapOf("x" to JsonValue.Number("1"), "y" to JsonValue.Number("2"))
                )
            )
        ),
        parse("""{"point":{"x":1,"y":2}}""")
    )

    @Test
    fun `object with array field`() = assertEquals(
        JsonValue.Obj(
            mapOf(
                "items" to JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2")))
            )
        ),
        parse("""{"items":[1,2]}""")
    )

    // Whitespace

    @Test
    fun `leading whitespace`() = assertEquals(JsonValue.Number("42"), parse("  42"))

    @Test
    fun `trailing whitespace`() = assertEquals(JsonValue.Number("42"), parse("42  "))

    @Test
    fun `newlines in object`() = assertEquals(
        JsonValue.Obj(mapOf("a" to JsonValue.Number("1"), "b" to JsonValue.Number("2"))),
        parse("{\n  \"a\": 1,\n  \"b\": 2\n}")
    )

    // Lenient trailing commas

    @Test
    fun `trailing comma in array`() {
        val config = JsonDecodingConfig(allowTrailingCommas = true)
        assertEquals(
            JsonValue.Arr(listOf(JsonValue.Number("1"), JsonValue.Number("2"))),
            parse("[1,2,]", config)
        )
    }

    @Test
    fun `trailing comma in object`() {
        val config = JsonDecodingConfig(allowTrailingCommas = true)
        assertEquals(
            JsonValue.Obj(mapOf("a" to JsonValue.Number("1"))),
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
        assertEquals(JsonValue.Number("42"), parse("// comment\n42", config))
    }

    @Test
    fun `comment after value in object`() {
        val config = JsonDecodingConfig(allowComments = true)
        assertEquals(
            JsonValue.Obj(mapOf("a" to JsonValue.Number("1"))),
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
        val expected = JsonValue.Obj(
            mapOf(
                "type" to JsonValue.Str("Branch"),
                "left" to JsonValue.Obj(
                    mapOf("type" to JsonValue.Str("Leaf"), "value" to JsonValue.Number("1"))
                ),
                "right" to JsonValue.Obj(
                    mapOf(
                        "type" to JsonValue.Str("Branch"),
                        "left" to JsonValue.Obj(
                            mapOf("type" to JsonValue.Str("Leaf"), "value" to JsonValue.Number("2"))
                        ),
                        "right" to JsonValue.Obj(
                            mapOf("type" to JsonValue.Str("Leaf"), "value" to JsonValue.Number("3"))
                        )
                    )
                )
            )
        )
        assertEquals(expected, parsed)
    }
}
