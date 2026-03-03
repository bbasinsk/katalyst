# Source-based JSON Decoder Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a two-phase JSON decoder (`Source → SchemaValue → A`) in the `schema-json` module, eliminating the kotlinx.serialization dependency for decoding.

**Architecture:** A streaming JSON parser produces `SchemaValue` from `kotlinx.io.Source`. A separate schema decoder converts `SchemaValue` to domain types via `Schema<A>`, returning `Validation<InvalidJson, A>`. These compose into convenience functions (`decodeFromJsonString`, `decodeFromJsonBytes`, `decodeFromSource`).

**Tech Stack:** Kotlin Multiplatform, kotlinx.io 0.8.2, existing `Schema<A>` / `SchemaValue` / `Validation` types

**Key paths:**
- Source code: `schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/`
- Tests: `schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/`
- Kotlinx decoder (reference): `schema-json-kotlinx/src/commonMain/kotlin/io/github/bbasinsk/schema/json/kotlinx/Decoding.kt`
- Kotlinx tests (reference): `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/`
- Sink encoder (structural reference): `schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/SinkEncoding.kt`

**Build/test commands:**
- Build: `./gradlew :schema-json:build`
- Test schema-json: `./gradlew :schema-json:allTests`
- Test schema-json-kotlinx: `./gradlew :schema-json-kotlinx:allTests`
- Test all: `./gradlew allTests`
- Single test class (JVM): `./gradlew :schema-json:jvmTest --tests "io.github.bbasinsk.schema.json.SourceDecodingTest"`

---

### Task 1: Add `validation` dependency to `schema-json` and create `JsonDecodingConfig`

The `schema-json` module currently only depends on `:schema` and `kotlinx.io`. The schema decoder needs `Validation`, so we must add the `:validation` dependency. Also create the config data class.

**Files:**
- Modify: `schema-json/build.gradle.kts`
- Create: `schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/JsonDecodingConfig.kt`

**Step 1: Add validation dependency**

In `schema-json/build.gradle.kts`, add `api(project(":validation"))` to commonMain dependencies:

```kotlin
val commonMain by getting {
    dependencies {
        api(project(":schema"))
        api(project(":validation"))
        api(libs.kotlinx.io)
    }
}
```

**Step 2: Create `JsonDecodingConfig.kt`**

```kotlin
package io.github.bbasinsk.schema.json

data class JsonDecodingConfig(
    val allowTrailingCommas: Boolean = false,
    val allowComments: Boolean = false,
)
```

**Step 3: Verify it compiles**

Run: `./gradlew :schema-json:compileKotlinJvm`
Expected: BUILD SUCCESSFUL

**Step 4: Commit**

```bash
git add schema-json/build.gradle.kts schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/JsonDecodingConfig.kt
git commit -m "feat: add validation dependency and JsonDecodingConfig to schema-json"
```

---

### Task 2: Create the JSON parser — `SourceDecoding.kt`

Implement the streaming JSON parser that reads from `kotlinx.io.Source` and produces `SchemaValue`. This is a standalone component with no Schema dependency.

**Files:**
- Create: `schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/SourceDecoding.kt`

**Reference:** The parser mirrors the inverse of `SinkEncoding.kt`'s `encodeDynamicToSink` function. For each `SchemaValue` variant that the encoder writes, the parser reads and reconstructs.

**Step 1: Create `SourceDecoding.kt`**

The parser is a private class that wraps a `Source` and tracks position for error messages. Public entry point is a top-level function.

```kotlin
package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue
import kotlinx.io.Source
import kotlinx.io.readString

fun decodeSchemaValueFromSource(source: Source, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    JsonParser(source, config).parseValue()

private class JsonParser(private val source: Source, private val config: JsonDecodingConfig) {

    fun parseValue(): SchemaValue {
        skipWhitespaceAndComments()
        require(!source.exhausted()) { "Unexpected end of input" }
        return when (val b = source.peek().readByte()) {
            '"'.code.toByte() -> parseString()
            '{'.code.toByte() -> parseObject()
            '['.code.toByte() -> parseArray()
            't'.code.toByte(), 'f'.code.toByte() -> parseBoolean()
            'n'.code.toByte() -> parseNull()
            else -> {
                if (b == '-'.code.toByte() || b in '0'.code.toByte()..'9'.code.toByte()) {
                    parseNumber()
                } else {
                    error("Unexpected character: '${b.toInt().toChar()}'")
                }
            }
        }
    }

    private fun parseNull(): SchemaValue.Null {
        expectLiteral("null")
        return SchemaValue.Null
    }

    private fun parseBoolean(): SchemaValue.Bool {
        return if (source.peek().readByte() == 't'.code.toByte()) {
            expectLiteral("true")
            SchemaValue.Bool(true)
        } else {
            expectLiteral("false")
            SchemaValue.Bool(false)
        }
    }

    private fun parseString(): SchemaValue.Str =
        SchemaValue.Str(parseStringValue())

    private fun parseStringValue(): String {
        consume('"')
        val sb = StringBuilder()
        while (!source.exhausted()) {
            val ch = readChar()
            when (ch) {
                '"' -> return sb.toString()
                '\\' -> sb.append(parseEscape())
                else -> sb.append(ch)
            }
        }
        error("Unterminated string")
    }

    private fun parseEscape(): Char {
        require(!source.exhausted()) { "Unterminated escape sequence" }
        return when (val ch = readChar()) {
            '"' -> '"'
            '\\' -> '\\'
            '/' -> '/'
            'b' -> '\b'
            'f' -> '\u000C'
            'n' -> '\n'
            'r' -> '\r'
            't' -> '\t'
            'u' -> parseUnicodeEscape()
            else -> error("Invalid escape character: '\\$ch'")
        }
    }

    private fun parseUnicodeEscape(): Char {
        val hex = buildString(4) {
            repeat(4) {
                require(!source.exhausted()) { "Unterminated unicode escape" }
                append(readChar())
            }
        }
        return hex.toInt(16).toChar()
    }

    private fun parseNumber(): SchemaValue {
        val sb = StringBuilder()
        var isDecimal = false
        while (!source.exhausted()) {
            val b = source.peek().readByte()
            val ch = b.toInt().toChar()
            when {
                ch in '0'..'9' || ch == '-' || ch == '+' -> { source.readByte(); sb.append(ch) }
                ch == '.' || ch == 'e' || ch == 'E' -> { source.readByte(); sb.append(ch); isDecimal = true }
                else -> break
            }
        }
        val text = sb.toString()
        return if (isDecimal) {
            SchemaValue.Decimal(text.toDouble())
        } else {
            text.toLongOrNull()?.let { SchemaValue.Integer(it) }
                ?: SchemaValue.Decimal(text.toDouble())
        }
    }

    private fun parseArray(): SchemaValue.Arr {
        consume('[')
        skipWhitespaceAndComments()
        if (!source.exhausted() && source.peek().readByte() == ']'.code.toByte()) {
            source.readByte()
            return SchemaValue.Arr(emptyList())
        }
        val items = mutableListOf<SchemaValue>()
        while (true) {
            items.add(parseValue())
            skipWhitespaceAndComments()
            require(!source.exhausted()) { "Unterminated array" }
            when (source.peek().readByte()) {
                ','.code.toByte() -> {
                    source.readByte()
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && !source.exhausted() && source.peek().readByte() == ']'.code.toByte()) {
                        source.readByte()
                        return SchemaValue.Arr(items)
                    }
                }
                ']'.code.toByte() -> {
                    source.readByte()
                    return SchemaValue.Arr(items)
                }
                else -> error("Expected ',' or ']' in array")
            }
        }
    }

    private fun parseObject(): SchemaValue.Obj {
        consume('{')
        skipWhitespaceAndComments()
        if (!source.exhausted() && source.peek().readByte() == '}'.code.toByte()) {
            source.readByte()
            return SchemaValue.Obj(emptyMap())
        }
        val entries = mutableMapOf<String, SchemaValue>()
        while (true) {
            skipWhitespaceAndComments()
            val key = parseStringValue()
            skipWhitespaceAndComments()
            consume(':')
            val value = parseValue()
            entries[key] = value
            skipWhitespaceAndComments()
            require(!source.exhausted()) { "Unterminated object" }
            when (source.peek().readByte()) {
                ','.code.toByte() -> {
                    source.readByte()
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && !source.exhausted() && source.peek().readByte() == '}'.code.toByte()) {
                        source.readByte()
                        return SchemaValue.Obj(entries)
                    }
                }
                '}'.code.toByte() -> {
                    source.readByte()
                    return SchemaValue.Obj(entries)
                }
                else -> error("Expected ',' or '}' in object")
            }
        }
    }

    private fun skipWhitespaceAndComments() {
        while (!source.exhausted()) {
            val b = source.peek().readByte()
            when {
                b.isWhitespace() -> source.readByte()
                b == '/'.code.toByte() && config.allowComments -> {
                    source.readByte()
                    require(!source.exhausted() && source.readByte() == '/'.code.toByte()) {
                        "Expected '//' for comment"
                    }
                    // Skip to end of line
                    while (!source.exhausted()) {
                        val ch = source.readByte()
                        if (ch == '\n'.code.toByte() || ch == '\r'.code.toByte()) break
                    }
                }
                else -> return
            }
        }
    }

    private fun consume(expected: Char) {
        require(!source.exhausted()) { "Expected '$expected' but reached end of input" }
        val actual = readChar()
        require(actual == expected) { "Expected '$expected' but found '$actual'" }
    }

    private fun expectLiteral(literal: String) {
        val actual = source.readString(literal.length.toLong())
        require(actual == literal) { "Expected '$literal' but found '$actual'" }
    }

    private fun readChar(): Char = source.readByte().toInt().toChar()

    private fun Byte.isWhitespace(): Boolean =
        this == ' '.code.toByte() || this == '\t'.code.toByte() ||
            this == '\n'.code.toByte() || this == '\r'.code.toByte()
}
```

**Step 2: Verify it compiles**

Run: `./gradlew :schema-json:compileKotlinJvm`
Expected: BUILD SUCCESSFUL

**Step 3: Commit**

```bash
git add schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/SourceDecoding.kt
git commit -m "feat: add JSON parser (Source -> SchemaValue)"
```

---

### Task 3: Test the JSON parser

Write tests for `SourceDecoding.kt` covering all value types, string escaping, nested structures, lenient mode, and error cases.

**Files:**
- Create: `schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/SourceDecodingTest.kt`

**Step 1: Write `SourceDecodingTest.kt`**

```kotlin
package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SourceDecodingTest {

    private fun parse(json: String, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
        decodeSchemaValueFromString(json, config)

    // Primitives

    @Test fun `null`() = assertEquals(SchemaValue.Null, parse("null"))
    @Test fun `true`() = assertEquals(SchemaValue.Bool(true), parse("true"))
    @Test fun `false`() = assertEquals(SchemaValue.Bool(false), parse("false"))
    @Test fun `integer`() = assertEquals(SchemaValue.Integer(42), parse("42"))
    @Test fun `negative integer`() = assertEquals(SchemaValue.Integer(-7), parse("-7"))
    @Test fun `zero`() = assertEquals(SchemaValue.Integer(0), parse("0"))
    @Test fun `long max`() = assertEquals(SchemaValue.Integer(Long.MAX_VALUE), parse(Long.MAX_VALUE.toString()))
    @Test fun `long min`() = assertEquals(SchemaValue.Integer(Long.MIN_VALUE), parse(Long.MIN_VALUE.toString()))
    @Test fun `decimal`() = assertEquals(SchemaValue.Decimal(3.14), parse("3.14"))
    @Test fun `negative decimal`() = assertEquals(SchemaValue.Decimal(-0.5), parse("-0.5"))
    @Test fun `zero decimal`() = assertEquals(SchemaValue.Decimal(0.0), parse("0.0"))
    @Test fun `scientific notation`() = assertEquals(SchemaValue.Decimal(1e10), parse("1e10"))
    @Test fun `scientific notation uppercase`() = assertEquals(SchemaValue.Decimal(1E10), parse("1E10"))
    @Test fun `scientific notation negative exponent`() = assertEquals(SchemaValue.Decimal(1e-5), parse("1e-5"))
    @Test fun `string`() = assertEquals(SchemaValue.Str("hello"), parse("\"hello\""))
    @Test fun `empty string`() = assertEquals(SchemaValue.Str(""), parse("\"\""))

    // String escaping

    @Test fun `string with escaped quote`() = assertEquals(SchemaValue.Str("say \"hi\""), parse(""""say \"hi\"""""))
    @Test fun `string with backslash`() = assertEquals(SchemaValue.Str("a\\b"), parse(""""a\\b""""))
    @Test fun `string with newline`() = assertEquals(SchemaValue.Str("a\nb"), parse(""""a\nb""""))
    @Test fun `string with tab`() = assertEquals(SchemaValue.Str("a\tb"), parse(""""a\tb""""))
    @Test fun `string with carriage return`() = assertEquals(SchemaValue.Str("a\rb"), parse(""""a\rb""""))
    @Test fun `string with backspace`() = assertEquals(SchemaValue.Str("a\bb"), parse(""""a\bb""""))
    @Test fun `string with form feed`() = assertEquals(SchemaValue.Str("a\u000Cb"), parse(""""a\fb""""))
    @Test fun `string with forward slash`() = assertEquals(SchemaValue.Str("a/b"), parse(""""a\/b""""))
    @Test fun `string with unicode escape`() = assertEquals(SchemaValue.Str("\u00e9"), parse(""""\\u00e9""""))
    @Test fun `string with null char unicode`() = assertEquals(SchemaValue.Str("\u0000"), parse(""""\\u0000""""))

    // Arrays

    @Test fun `empty array`() = assertEquals(SchemaValue.Arr(emptyList()), parse("[]"))
    @Test fun `array of ints`() = assertEquals(
        SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2), SchemaValue.Integer(3))),
        parse("[1, 2, 3]")
    )
    @Test fun `nested array`() = assertEquals(
        SchemaValue.Arr(listOf(SchemaValue.Arr(listOf(SchemaValue.Integer(1))), SchemaValue.Arr(listOf(SchemaValue.Integer(2))))),
        parse("[[1],[2]]")
    )
    @Test fun `mixed type array`() = assertEquals(
        SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Str("two"), SchemaValue.Bool(true), SchemaValue.Null)),
        parse("""[1, "two", true, null]""")
    )

    // Objects

    @Test fun `empty object`() = assertEquals(SchemaValue.Obj(emptyMap()), parse("{}"))
    @Test fun `simple object`() = assertEquals(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1), "b" to SchemaValue.Str("two"))),
        parse("""{"a": 1, "b": "two"}""")
    )
    @Test fun `nested object`() = assertEquals(
        SchemaValue.Obj(mapOf("inner" to SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1))))),
        parse("""{"inner": {"x": 1}}""")
    )
    @Test fun `object with array field`() = assertEquals(
        SchemaValue.Obj(mapOf("items" to SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))))),
        parse("""{"items": [1, 2]}""")
    )

    // Whitespace tolerance

    @Test fun `leading whitespace`() = assertEquals(SchemaValue.Integer(42), parse("  42"))
    @Test fun `trailing whitespace`() = assertEquals(SchemaValue.Integer(42), parse("42  "))
    @Test fun `newlines in object`() = assertEquals(
        SchemaValue.Obj(mapOf("x" to SchemaValue.Integer(1))),
        parse("{\n  \"x\": 1\n}")
    )

    // Lenient: trailing commas

    private val lenient = JsonDecodingConfig(allowTrailingCommas = true, allowComments = true)

    @Test fun `trailing comma in array`() = assertEquals(
        SchemaValue.Arr(listOf(SchemaValue.Integer(1), SchemaValue.Integer(2))),
        parse("[1, 2,]", lenient)
    )
    @Test fun `trailing comma in object`() = assertEquals(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1))),
        parse("""{"a": 1,}""", lenient)
    )
    @Test fun `trailing comma rejected in strict mode`() {
        assertFailsWith<IllegalArgumentException> { parse("[1,]") }
    }

    // Lenient: comments

    @Test fun `comment before value`() = assertEquals(
        SchemaValue.Integer(42),
        parse("// a comment\n42", lenient)
    )
    @Test fun `comment in object`() = assertEquals(
        SchemaValue.Obj(mapOf("a" to SchemaValue.Integer(1))),
        parse("""{"a": 1 // inline comment\n}""", lenient)
    )
    @Test fun `comment rejected in strict mode`() {
        assertFailsWith<IllegalArgumentException> { parse("// comment\n42") }
    }

    // Error cases

    @Test fun `empty input`() { assertFailsWith<IllegalArgumentException> { parse("") } }
    @Test fun `whitespace only input`() { assertFailsWith<IllegalArgumentException> { parse("   ") } }
    @Test fun `unterminated string`() { assertFailsWith<Exception> { parse("\"hello") } }
    @Test fun `unterminated array`() { assertFailsWith<Exception> { parse("[1, 2") } }
    @Test fun `unterminated object`() { assertFailsWith<Exception> { parse("""{"a": 1""") } }
    @Test fun `invalid token`() { assertFailsWith<Exception> { parse("xyz") } }

    // Round-trip with SinkEncoder: parse what the encoder produces
    // (These validate that our parser reads the same format SinkEncoding writes)

    @Test fun `round-trip nested structure`() {
        val json = """{"name":"test","items":[{"id":1},{"id":2}],"meta":null}"""
        val expected = SchemaValue.Obj(mapOf(
            "name" to SchemaValue.Str("test"),
            "items" to SchemaValue.Arr(listOf(
                SchemaValue.Obj(mapOf("id" to SchemaValue.Integer(1))),
                SchemaValue.Obj(mapOf("id" to SchemaValue.Integer(2)))
            )),
            "meta" to SchemaValue.Null
        ))
        assertEquals(expected, parse(json))
    }
}
```

**Step 2: Run tests**

Run: `./gradlew :schema-json:allTests`
Expected: ALL PASS. If any fail, fix the parser in `SourceDecoding.kt` and re-run.

**Step 3: Commit**

```bash
git add schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/SourceDecodingTest.kt
git commit -m "test: add JSON parser tests (SourceDecodingTest)"
```

---

### Task 4: Create the public API — `Decoding.kt`

Add convenience functions that wrap the parser and compose with the schema decoder (which we'll build next). The `decodeFromSchemaValue` call will be added in Task 5. For now, create the SchemaValue-level convenience functions.

**Files:**
- Create: `schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/Decoding.kt`

**Step 1: Create `Decoding.kt`**

```kotlin
package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.validation.Validation
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString

fun decodeSchemaValueFromString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    decodeSchemaValueFromSource(Buffer().apply { writeString(str) }, config)

fun decodeSchemaValueFromBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    decodeSchemaValueFromSource(Buffer().apply { write(bytes) }, config)

fun <A> Schema<A>.decodeFromSource(source: Source, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromSource(source, config))

fun <A> Schema<A>.decodeFromJsonString(str: String, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromString(str, config))

fun <A> Schema<A>.decodeFromJsonBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): Validation<InvalidJson, A> =
    decodeFromSchemaValue(decodeSchemaValueFromBytes(bytes, config))
```

**Step 2: Verify it compiles**

Run: `./gradlew :schema-json:compileKotlinJvm`
Expected: FAIL — `decodeFromSchemaValue` doesn't exist yet. This is expected; it will compile after Task 5.

**Step 3: Commit (WIP)**

Skip commit until Task 5 completes.

---

### Task 5: Create the schema decoder — `SchemaValueDecoding.kt`

Implement `Schema<A>.decodeFromSchemaValue(SchemaValue) → Validation<InvalidJson, A>`. This mirrors the structure of `schema-json-kotlinx/Decoding.kt` but replaces `JsonElement` with `SchemaValue`.

**Files:**
- Create: `schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/SchemaValueDecoding.kt`

**Reference:** Follow the exact same dispatch structure as `schema-json-kotlinx/src/commonMain/kotlin/io/github/bbasinsk/schema/json/kotlinx/Decoding.kt`. Each function maps from `JsonElement` operations to `SchemaValue` pattern matching.

**Step 1: Create `SchemaValueDecoding.kt`**

```kotlin
@file:OptIn(ExperimentalEncodingApi::class)

package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.schema.decodePrimitiveString
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.Validation.Companion.invalid
import io.github.bbasinsk.validation.Validation.Companion.valid
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <A> Schema<A>.decodeFromSchemaValue(value: SchemaValue): Validation<InvalidJson, A> =
    Validation.decode(schema = this, value = value, path = listOf())

@Suppress("UNCHECKED_CAST")
private fun <A> Validation.Companion.decode(
    schema: Schema<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A> =
    when (schema) {
        is Schema.Empty -> valid(null as A)
        is Schema.Dynamic -> valid(value as A)
        is Schema.Bytes -> decodeBytes(value, path) as Validation<InvalidJson, A>
        is Schema.Lazy<A> -> decode(schema.schema(), value, path)
        is Schema.Metadata -> decode(schema.schema, value, path)
        is Schema.Primitive -> decodePrimitive(schema, value, path)
        is Schema.Default -> decodeDefault(schema, value, path)
        is Schema.Optional<*> -> decodeOptional(schema, value, path) as Validation<InvalidJson, A>
        is Schema.OrElse<A, *> -> decode(schema.preferred, value, path).orElse { preferredErrors ->
            decode(schema.fallback, value, path).andThen { b ->
                fromResult(schema.unsafeDecode(b)) { e ->
                    InvalidJson.FieldError(schema.metadata.name, """"${e.message ?: "unknown error"}"""", path)
                }
            }.orElse { fallbackErrors ->
                invalid(InvalidJson.Or(preferredErrors, fallbackErrors))
            }
        }
        is Schema.Transform<A, *> -> decodeTransform(schema, value, path)
        is Schema.Collection<*> -> decodeList(schema, value, path) as Validation<InvalidJson, A>
        is Schema.StringMap<*> -> decodeStringMap(schema, value, path) as Validation<InvalidJson, A>
        is Schema.Record<*> -> decodeRecord(schema as Schema.Record<A>, value, path)
        is Schema.Union<*> -> decodeUnion(schema as Schema.Union<A>, value, path)
    }

private fun <A> Validation.Companion.decodePrimitive(
    schema: Schema.Primitive<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A> {
    val error = InvalidJson.FieldError(schema.name, value.toFoundString(), path)
    if (value is SchemaValue.Null) return invalid(error)
    return decodePrimitiveFromSchemaValue(schema, value, error)
}

@Suppress("UNCHECKED_CAST")
private fun <A> Validation.Companion.decodePrimitiveFromSchemaValue(
    schema: Schema.Primitive<A>,
    value: SchemaValue,
    error: InvalidJson.FieldError
): Validation<InvalidJson, A> =
    when (schema) {
        is Schema.Primitive.Boolean -> when (value) {
            is SchemaValue.Bool -> valid(value.value as A)
            else -> invalid(error)
        }
        is Schema.Primitive.Int -> when (value) {
            is SchemaValue.Integer -> valid(value.value.toInt() as A)
            else -> invalid(error)
        }
        is Schema.Primitive.Long -> when (value) {
            is SchemaValue.Integer -> valid(value.value as A)
            else -> invalid(error)
        }
        is Schema.Primitive.Double -> when (value) {
            is SchemaValue.Decimal -> valid(value.value as A)
            is SchemaValue.Integer -> valid(value.value.toDouble() as A)
            else -> invalid(error)
        }
        is Schema.Primitive.Float -> when (value) {
            is SchemaValue.Decimal -> valid(value.value.toFloat() as A)
            is SchemaValue.Integer -> valid(value.value.toFloat() as A)
            else -> invalid(error)
        }
        is Schema.Primitive.String -> when (value) {
            is SchemaValue.Str -> valid(value.value as A)
            else -> invalid(error)
        }
        is Schema.Primitive.Enumeration<*> -> when (value) {
            is SchemaValue.Str -> fromResult(schema.decodePrimitiveString(value.value)) { error }
            else -> invalid(error)
        }
    }

private fun Validation.Companion.decodeBytes(
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, ByteArray> =
    when (value) {
        is SchemaValue.Str -> runCatching { Base64.decode(value.value) }
            .mapInvalid { InvalidJson.FieldError("base64 encoded", value.toFoundString(), path) }
        else -> invalid(InvalidJson.FieldError("base64 encoded", value.toFoundString(), path))
    }

private fun <A> Validation.Companion.decodeOptional(
    schema: Schema.Optional<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A?> =
    if (value is SchemaValue.Null) valid(null) else decode(schema.schema, value, path)

private fun <A> Validation.Companion.decodeDefault(
    schema: Schema.Default<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A> =
    if (value is SchemaValue.Null) valid(schema.default) else decode(schema.schema, value, path)

private fun <A, B> Validation.Companion.decodeTransform(
    schema: Schema.Transform<A, B>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A> =
    decode(schema.schema, value, path)
        .andThen { b ->
            schema.decode(b).fold(
                { valid(it) },
                { e -> invalid(InvalidJson.FieldError(schema.metadata.name, """"${e.message ?: "unknown error"}"""", path)) }
            )
        }

private fun <A> Validation.Companion.decodeList(
    schema: Schema.Collection<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, List<A>> =
    when (value) {
        is SchemaValue.Arr -> sequence(
            value.values.mapIndexed { i, item -> decode(schema.itemSchema, item, path + Segment.Index(i)) }
        )
        else -> invalid(InvalidJson.FieldError("Array", value.toFoundString(), path))
    }

private fun <V> Validation.Companion.decodeStringMap(
    schema: Schema.StringMap<V>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, Map<String, V>> =
    when (value) {
        is SchemaValue.Obj -> sequence(
            value.entries.map { (key, v) ->
                decode(schema.valueSchema, v, path + Segment.Field(key)).mapValid { key to it }
            }
        ).mapValid { it.toMap() }
        else -> invalid(InvalidJson.FieldError("Object", value.toFoundString(), path))
    }

private fun <A> Validation.Companion.decodeRecord(
    schema: Schema.Record<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A> =
    when (value) {
        is SchemaValue.Obj -> sequence(
            schema.unsafeFields().map {
                decode(it.schema, value.entries[it.name] ?: SchemaValue.Null, path + Segment.Field(it.name))
            }
        ).mapValid { schema.unsafeConstruct(it) }
        else -> invalid(InvalidJson.FieldError("Object", value.toFoundString(), path))
    }

private fun <A> Validation.Companion.decodeUnion(
    schema: Schema.Union<A>,
    value: SchemaValue,
    path: List<Segment>
): Validation<InvalidJson, A> =
    @Suppress("UNCHECKED_CAST")
    when (value) {
        is SchemaValue.Obj -> {
            val discriminator = value.entries[schema.key]
            when (discriminator) {
                is SchemaValue.Str -> {
                    val identifier = discriminator.value
                    val case = schema.unsafeCases().find { it.name == identifier }
                    if (case != null) {
                        decode(case.schema as Schema<A>, value, path)
                    } else {
                        invalid(InvalidJson.FieldError(
                            expected = schema.unsafeCases().map { it.name }.joinToString(", ", "[", "]"),
                            found = identifier,
                            path = path + Segment.Field(schema.key)
                        ))
                    }
                }
                null -> invalid(InvalidJson.FieldError("String", "null", path + Segment.Field(schema.key)))
                else -> invalid(InvalidJson.FieldError("String", discriminator.toFoundString(), path + Segment.Field(schema.key)))
            }
        }
        else -> invalid(InvalidJson.FieldError("Object", value.toFoundString(), path))
    }

private fun SchemaValue.toFoundString(): String = when (this) {
    is SchemaValue.Null -> "null"
    is SchemaValue.Bool -> value.toString()
    is SchemaValue.Integer -> value.toString()
    is SchemaValue.Decimal -> value.toString()
    is SchemaValue.Str -> "\"$value\""
    is SchemaValue.Arr -> "Array"
    is SchemaValue.Obj -> "Object"
}
```

**Step 2: Verify everything compiles (including `Decoding.kt` from Task 4)**

Run: `./gradlew :schema-json:compileKotlinJvm`
Expected: BUILD SUCCESSFUL (both `Decoding.kt` and `SchemaValueDecoding.kt` compile together)

**Step 3: Commit**

```bash
git add schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/SchemaValueDecoding.kt schema-json/src/commonMain/kotlin/io/github/bbasinsk/schema/json/Decoding.kt
git commit -m "feat: add schema decoder (SchemaValue -> A) and public API"
```

---

### Task 6: Test the schema decoder and cross-decoder equivalence

Write tests that verify `Schema<A>.decodeFromSchemaValue` works correctly, round-trips with encoding, and produces the same results as the kotlinx decoder.

**Files:**
- Create: `schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/SchemaValueDecodingTest.kt`

**Step 1: Write `SchemaValueDecodingTest.kt`**

This test file serves double duty: it tests decoding behavior AND asserts equivalence with the kotlinx decoder. It reuses the same schemas from `SinkEncodingTest.kt`.

```kotlin
package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.SchemaValue
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonElement
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString as kotlinxDecodeFromJsonString
import io.github.bbasinsk.schema.orElse
import io.github.bbasinsk.schema.transform
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SchemaValueDecodingTest {

    // Helper: assert decode(encode(value)) == value
    private fun <A> assertRoundTrip(schema: Schema<A>, value: A) {
        val json = schema.encodeToJsonString(value)
        assertEquals(Validation.valid(value), schema.decodeFromJsonString(json))
    }

    // Helper: assert new decoder matches kotlinx decoder for the same JSON string
    private fun <A> assertMatchesKotlinx(schema: Schema<A>, json: String) {
        val newResult = schema.decodeFromJsonString(json)
        val kotlinxResult = schema.kotlinxDecodeFromJsonString(json, Json.Default)
        assertEquals(kotlinxResult, newResult)
    }

    // ── Primitives ──

    @Test fun `int round-trip`() = assertRoundTrip(Schema.int(), 42)
    @Test fun `negative int round-trip`() = assertRoundTrip(Schema.int(), -7)
    @Test fun `string round-trip`() = assertRoundTrip(Schema.string(), "hello")
    @Test fun `empty string round-trip`() = assertRoundTrip(Schema.string(), "")
    @Test fun `boolean true round-trip`() = assertRoundTrip(Schema.boolean(), true)
    @Test fun `boolean false round-trip`() = assertRoundTrip(Schema.boolean(), false)
    @Test fun `double round-trip`() = assertRoundTrip(Schema.double(), 3.14)
    @Test fun `float round-trip`() = assertRoundTrip(Schema.float(), 2.5f)
    @Test fun `long round-trip`() = assertRoundTrip(Schema.long(), 30000000000L)

    enum class Color { RED, GREEN, BLUE }
    @Test fun `enum round-trip`() = assertRoundTrip(Schema.enumeration<Color>(), Color.GREEN)

    // ── Optional / Default ──

    @Test fun `optional null round-trip`() = assertRoundTrip(Schema.int().optional(), null)
    @Test fun `optional present round-trip`() = assertRoundTrip(Schema.int().optional(), 42)
    @Test fun `default round-trip`() = assertRoundTrip(Schema.int().default(0), 99)
    @Test fun `default from null`() = assertEquals(Validation.valid(42), Schema.int().default(42).decodeFromJsonString("null"))

    // ── ByteArray ──

    @Test fun `byteArray round-trip`() = assertRoundTrip(Schema.byteArray(), "hello bytes".encodeToByteArray())

    // ── Collections ──

    @Test fun `empty list round-trip`() = assertRoundTrip(Schema.list(Schema.int()), emptyList())
    @Test fun `list round-trip`() = assertRoundTrip(Schema.list(Schema.int()), listOf(1, 2, 3))
    @Test fun `nested list round-trip`() = assertRoundTrip(
        Schema.list(Schema.list(Schema.string())),
        listOf(listOf("a", "b"), listOf("c"))
    )
    @Test fun `empty stringMap round-trip`() = assertRoundTrip(Schema.stringMap(Schema.int()), emptyMap())
    @Test fun `stringMap round-trip`() = assertRoundTrip(Schema.stringMap(Schema.int()), mapOf("a" to 1, "b" to 2))

    // ── Records ──

    data class Point(val x: Int, val y: Int)
    private fun Schema.Companion.point(): Schema<Point> = record(
        field(int(), "x") { x }, field(int(), "y") { y }, ::Point
    )

    data class Line(val start: Point, val end: Point)
    private fun Schema.Companion.line(): Schema<Line> = record(
        field(point(), "start") { start }, field(point(), "end") { end }, ::Line
    )

    data class Named(val name: String, val age: Int?)
    private fun Schema.Companion.named(): Schema<Named> = record(
        field(string(), "name") { name }, field(int().optional(), "age") { age }, ::Named
    )

    @Test fun `simple record round-trip`() = assertRoundTrip(Schema.point(), Point(1, 2))
    @Test fun `nested record round-trip`() = assertRoundTrip(Schema.line(), Line(Point(0, 0), Point(3, 4)))
    @Test fun `record with null optional`() = assertRoundTrip(Schema.named(), Named("Alice", null))
    @Test fun `record with present optional`() = assertRoundTrip(Schema.named(), Named("Bob", 30))

    @Test fun `record missing field`() = assertMatchesKotlinx(Schema.point(), """{"x": 1}""")
    @Test fun `record wrong type`() = assertMatchesKotlinx(Schema.point(), """{"x": "bad", "y": "bad"}""")

    // ── Unions ──

    sealed interface Shape {
        data class Circle(val radius: Int) : Shape
        data class Rect(val w: Int, val h: Int) : Shape
    }

    private fun Schema.Companion.circle(): Schema<Shape.Circle> = record(
        field(int(), "radius") { radius }, Shape::Circle
    )
    private fun Schema.Companion.rect(): Schema<Shape.Rect> = record(
        field(int(), "w") { w }, field(int(), "h") { h }, Shape::Rect
    )
    private fun Schema.Companion.shape(): Schema<Shape> = union(case(circle()), case(rect()))

    @Test fun `union first case round-trip`() = assertRoundTrip(Schema.shape(), Shape.Circle(5))
    @Test fun `union second case round-trip`() = assertRoundTrip(Schema.shape(), Shape.Rect(3, 4))
    @Test fun `union unknown type`() = assertMatchesKotlinx(Schema.shape(), """{"type": "Unknown", "x": 1}""")

    // ── Transform ──

    data class UserId(val value: Int)
    private fun Schema.Companion.userId(): Schema<UserId> = int().transform(::UserId) { it.value }

    @Test fun `transform round-trip`() = assertRoundTrip(Schema.userId(), UserId(42))

    // ── Lazy / recursive ──

    sealed interface Tree {
        data class Leaf(val value: Int) : Tree
        data class Branch(val left: Tree, val right: Tree) : Tree
    }

    private fun Schema.Companion.leaf(): Schema<Tree.Leaf> = record(field(int(), "value") { value }, Tree::Leaf)
    private fun Schema.Companion.branch(): Schema<Tree.Branch> = record(
        field(lazy { tree() }, "left") { left }, field(lazy { tree() }, "right") { right }, Tree::Branch
    )
    private fun Schema.Companion.tree(): Schema<Tree> = union(case(leaf()), case(branch()))

    @Test fun `recursive round-trip`() = assertRoundTrip(
        Schema.tree(),
        Tree.Branch(Tree.Leaf(1), Tree.Branch(Tree.Leaf(2), Tree.Leaf(3)))
    )

    // ── OrElse ──

    @Test fun `orElse preferred path`() {
        val schema = Schema.double().orElse(Schema.string()) { it.toDouble() }
        assertEquals(Validation.valid(12.123), schema.decodeFromJsonString("12.123"))
    }

    @Test fun `orElse fallback path`() {
        val schema = Schema.double().orElse(Schema.string()) { it.toDouble() }
        assertEquals(Validation.valid(12.123), schema.decodeFromJsonString("\"12.123\""))
    }

    // ── Dynamic ──

    @Test fun `dynamic round-trip`() {
        val schema = Schema.dynamic()
        val value = SchemaValue.Obj(mapOf("key" to SchemaValue.Integer(42), "arr" to SchemaValue.Arr(listOf(SchemaValue.Bool(true)))))
        assertRoundTrip(schema, value)
    }

    // ── Error accumulation ──

    @Test fun `multiple field errors accumulated`() {
        val result = Schema.point().decodeFromJsonString("""{"x": "bad", "y": "bad"}""")
        val errors = (result as Validation.Invalid).errors
        assertEquals(2, errors.size)
    }

    @Test fun `list errors accumulated`() {
        val result = Schema.list(Schema.int()).decodeFromJsonString("""["bad", 42, null]""")
        val errors = (result as Validation.Invalid).errors
        assertEquals(2, errors.size)
    }

    // ── Equivalence with null/empty handling ──

    @Test fun `empty schema matches kotlinx`() = assertMatchesKotlinx(Schema.empty(), "null")
    @Test fun `optional null matches kotlinx`() = assertMatchesKotlinx(Schema.int().optional(), "null")
    @Test fun `default from null matches kotlinx`() = assertMatchesKotlinx(Schema.int().default(42), "null")
}
```

**Step 2: Run all tests**

Run: `./gradlew :schema-json:allTests`
Expected: ALL PASS. If any fail, fix the decoder and re-run.

**Step 3: Commit**

```bash
git add schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/SchemaValueDecodingTest.kt
git commit -m "test: add schema decoder tests and cross-decoder equivalence"
```

---

### Task 7: Update `schema-json-kotlinx` — remove default, fix callers

Remove the `Json = Json.Default` default parameter from `decodeFromJsonString` in `schema-json-kotlinx`. Update all test files in that module to pass `Json.Default` explicitly.

**Files:**
- Modify: `schema-json-kotlinx/src/commonMain/kotlin/io/github/bbasinsk/schema/json/kotlinx/Decoding.kt`
- Modify: `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/PrimitiveSerdeTest.kt`
- Modify: `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/RecordSerdeTest.kt`
- Modify: `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/UnionSerdeTest.kt`
- Modify: `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/CollectionSerdeTest.kt`
- Modify: `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/LenientSerdeTest.kt`
- Modify: `schema-json-kotlinx/src/commonTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/DynamicSerdeTest.kt`
- Check: `schema-json-kotlinx/src/jvmTest/kotlin/io/github/bbasinsk/schema/json/kotlinx/JvmSerdeTest.kt`
- Check: `http-client-ktor-3/src/commonMain/kotlin/io/github/bbasinsk/http/client/ktor3/KatalystClient.kt`
- Check: `http-server-ktor-3/src/commonMain/kotlin/io/github/bbasinsk/http/ktor3/KtorAdapter.kt`

**Step 1: Remove default from `Decoding.kt`**

In `schema-json-kotlinx/src/commonMain/kotlin/io/github/bbasinsk/schema/json/kotlinx/Decoding.kt` line 27, change:
```kotlin
fun <A> Schema<A>.decodeFromJsonString(str: String, json: Json = Json.Default): Validation<InvalidJson, A> =
```
to:
```kotlin
fun <A> Schema<A>.decodeFromJsonString(str: String, json: Json): Validation<InvalidJson, A> =
```

**Step 2: Update all test files**

In every test file under `schema-json-kotlinx/src/commonTest/`, find every call like `schema.decodeFromJsonString("...")` and change to `schema.decodeFromJsonString("...", Json.Default)`. Also add the import `import kotlinx.serialization.json.Json` if not already present.

Similarly check `schema-json-kotlinx/src/jvmTest/` files.

**Step 3: Check ktor callers**

`http-client-ktor-3/KatalystClient.kt` line 351 calls `bodySchema.schema.decodeFromJsonString(body)` with an explicit import of `io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString`. After removing the default, this call needs `Json.Default` added.

`http-server-ktor-3/KtorAdapter.kt` line 294 calls `schema.decodeFromJsonString(value)` with the same explicit import. Same fix.

**Step 4: Build all modules**

Run: `./gradlew build`
Expected: BUILD SUCCESSFUL (all callers updated)

**Step 5: Run all tests**

Run: `./gradlew allTests`
Expected: ALL PASS

**Step 6: Commit**

```bash
git add -u
git commit -m "refactor: remove default Json param from kotlinx decodeFromJsonString"
```

---

### Task 8: Benchmark — performance comparison

Create a benchmark test that compares the new decoder against the kotlinx decoder using a large DTO.

**Files:**
- Create: `schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/DecodingBenchmarkTest.kt`

**Step 1: Write `DecodingBenchmarkTest.kt`**

```kotlin
package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString as kotlinxDecodeFromJsonString
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.measureTime

class DecodingBenchmarkTest {

    // ── DTOs ──

    data class Address(val street: String, val city: String, val state: String, val zip: String, val country: String)
    data class Customer(val name: String, val email: String, val age: Int, val verified: Boolean, val tags: List<String>)
    data class LineItem(val sku: String, val name: String, val qty: Int, val price: Double, val discount: Double?)

    sealed interface OrderStatus {
        data object Pending : OrderStatus
        data class Shipped(val trackingId: String) : OrderStatus
        data class Delivered(val date: String) : OrderStatus
    }

    data class Order(
        val id: String,
        val amount: Double,
        val currency: String,
        val customer: Customer,
        val items: List<LineItem>,
        val shipping: Address,
        val billing: Address,
        val status: OrderStatus,
        val notes: String?,
        val metadata: Map<String, String>
    )

    // ── Schemas ──

    private fun Schema.Companion.address(): Schema<Address> = record(
        field(string(), "street") { street },
        field(string(), "city") { city },
        field(string(), "state") { state },
        field(string(), "zip") { zip },
        field(string(), "country") { country },
        ::Address
    )

    private fun Schema.Companion.customer(): Schema<Customer> = record(
        field(string(), "name") { name },
        field(string(), "email") { email },
        field(int(), "age") { age },
        field(boolean(), "verified") { verified },
        field(list(string()), "tags") { tags },
        ::Customer
    )

    private fun Schema.Companion.lineItem(): Schema<LineItem> = record(
        field(string(), "sku") { sku },
        field(string(), "name") { name },
        field(int(), "qty") { qty },
        field(double(), "price") { price },
        field(double().optional(), "discount") { discount },
        ::LineItem
    )

    private fun Schema.Companion.pending(): Schema<OrderStatus.Pending> = record(
        { OrderStatus.Pending }
    )

    private fun Schema.Companion.shipped(): Schema<OrderStatus.Shipped> = record(
        field(string(), "trackingId") { trackingId },
        OrderStatus::Shipped
    )

    private fun Schema.Companion.delivered(): Schema<OrderStatus.Delivered> = record(
        field(string(), "date") { date },
        OrderStatus::Delivered
    )

    private fun Schema.Companion.orderStatus(): Schema<OrderStatus> = union(
        case(pending()),
        case(shipped()),
        case(delivered())
    )

    private fun Schema.Companion.order(): Schema<Order> = record(
        field(string(), "id") { id },
        field(double(), "amount") { amount },
        field(string(), "currency") { currency },
        field(customer(), "customer") { customer },
        field(list(lineItem()), "items") { items },
        field(address(), "shipping") { shipping },
        field(address(), "billing") { billing },
        field(orderStatus(), "status") { status },
        field(string().optional(), "notes") { notes },
        field(stringMap(string()), "metadata") { metadata },
        ::Order
    )

    private val schema = Schema.order()

    private val testOrder = Order(
        id = "ord-12345",
        amount = 299.99,
        currency = "USD",
        customer = Customer(
            name = "Jane Doe",
            email = "jane@example.com",
            age = 34,
            verified = true,
            tags = listOf("premium", "early-adopter", "beta-tester")
        ),
        items = listOf(
            LineItem("SKU-001", "Widget Pro", 2, 49.99, 5.0),
            LineItem("SKU-002", "Gadget Plus", 1, 149.99, null),
            LineItem("SKU-003", "Accessory Pack", 3, 9.99, 1.0),
        ),
        shipping = Address("123 Main St", "Springfield", "IL", "62701", "US"),
        billing = Address("456 Oak Ave", "Shelbyville", "IL", "62702", "US"),
        status = OrderStatus.Shipped("TRACK-98765"),
        notes = "Please leave at the front door",
        metadata = mapOf("source" to "web", "campaign" to "summer-sale", "ref" to "abc123")
    )

    private val jsonString = schema.encodeToJsonString(testOrder)

    @Test
    fun `benchmark source decoder vs kotlinx decoder`() {
        val warmup = 1_000
        val iterations = 10_000

        // Verify both decoders produce the same result
        val sourceResult = schema.decodeFromJsonString(jsonString)
        val kotlinxResult = schema.kotlinxDecodeFromJsonString(jsonString, Json.Default)
        assertTrue(sourceResult is Validation.Valid, "Source decoder should succeed")
        assertTrue(kotlinxResult is Validation.Valid, "Kotlinx decoder should succeed")
        assertTrue(
            (sourceResult as Validation.Valid).value == (kotlinxResult as Validation.Valid).value,
            "Both decoders should produce the same result"
        )

        // Warmup
        repeat(warmup) {
            schema.decodeFromJsonString(jsonString)
            schema.kotlinxDecodeFromJsonString(jsonString, Json.Default)
        }

        // Measure Source decoder
        val sourceTimes = LongArray(iterations)
        repeat(iterations) { i ->
            sourceTimes[i] = measureTime { schema.decodeFromJsonString(jsonString) }.inWholeNanoseconds
        }

        // Measure Kotlinx decoder
        val kotlinxTimes = LongArray(iterations)
        repeat(iterations) { i ->
            kotlinxTimes[i] = measureTime { schema.kotlinxDecodeFromJsonString(jsonString, Json.Default) }.inWholeNanoseconds
        }

        val sourceAvg = sourceTimes.average() / 1_000
        val sourceMin = sourceTimes.min() / 1_000
        val sourceMax = sourceTimes.max() / 1_000
        val sourceP50 = sourceTimes.sorted()[iterations / 2] / 1_000

        val kotlinxAvg = kotlinxTimes.average() / 1_000
        val kotlinxMin = kotlinxTimes.min() / 1_000
        val kotlinxMax = kotlinxTimes.max() / 1_000
        val kotlinxP50 = kotlinxTimes.sorted()[iterations / 2] / 1_000

        println("=== Decoding Benchmark ($iterations iterations) ===")
        println("JSON length: ${jsonString.length} chars")
        println()
        println("Source decoder (Source -> SchemaValue -> A):")
        println("  avg: ${"%.1f".format(sourceAvg)} µs | p50: ${"%.1f".format(sourceP50)} µs | min: ${"%.1f".format(sourceMin)} µs | max: ${"%.1f".format(sourceMax)} µs")
        println()
        println("Kotlinx decoder (String -> JsonElement -> A):")
        println("  avg: ${"%.1f".format(kotlinxAvg)} µs | p50: ${"%.1f".format(kotlinxP50)} µs | min: ${"%.1f".format(kotlinxMin)} µs | max: ${"%.1f".format(kotlinxMax)} µs")
        println()
        println("Ratio (source/kotlinx): ${"%.2f".format(sourceAvg / kotlinxAvg)}x")
    }
}
```

**Step 2: Run the benchmark**

Run: `./gradlew :schema-json:jvmTest --tests "io.github.bbasinsk.schema.json.DecodingBenchmarkTest"`
Expected: PASS, with benchmark results printed to stdout.

**Step 3: Commit**

```bash
git add schema-json/src/commonTest/kotlin/io/github/bbasinsk/schema/json/DecodingBenchmarkTest.kt
git commit -m "test: add decoding benchmark (source vs kotlinx)"
```

---

### Task 9: Final verification

Run the full test suite across all modules to verify nothing is broken.

**Step 1: Run all tests**

Run: `./gradlew allTests`
Expected: ALL PASS

**Step 2: Verify the benchmark runs and prints results**

Run: `./gradlew :schema-json:jvmTest --tests "io.github.bbasinsk.schema.json.DecodingBenchmarkTest" --info 2>&1 | grep -A 10 "=== Decoding"`
Expected: Benchmark results visible in output.

**Step 3: Review git log**

Run: `git log --oneline -10`
Expected: Clean sequence of commits matching each task.
