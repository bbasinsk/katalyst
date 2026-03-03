package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue
import kotlinx.io.Source

fun decodeSchemaValueFromSource(source: Source, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    JsonParser(source, config).parseValue()

private class JsonParser(private val source: Source, private val config: JsonDecodingConfig) {

    fun parseValue(): SchemaValue {
        skipWhitespaceAndComments()
        require(!source.exhausted()) { "Unexpected end of input" }
        val ch = peekChar()
        return when {
            ch == '"' -> parseString()
            ch == 't' || ch == 'f' -> parseBoolean()
            ch == 'n' -> parseNull()
            ch == '{' -> parseObject()
            ch == '[' -> parseArray()
            ch == '-' || ch.isDigit() -> parseNumber()
            else -> throw IllegalArgumentException("Unexpected character '$ch'")
        }
    }

    private fun parseString(): SchemaValue.Str =
        SchemaValue.Str(readJsonString())

    private fun readJsonString(): String {
        expectChar('"')
        val sb = StringBuilder()
        while (true) {
            require(!source.exhausted()) { "Unterminated string" }
            val ch = readChar()
            when {
                ch == '"' -> return sb.toString()
                ch == '\\' -> sb.append(readEscaped())
                else -> sb.append(ch)
            }
        }
    }

    private fun readEscaped(): Char {
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
            'u' -> {
                val hex = buildString(4) {
                    repeat(4) {
                        require(!source.exhausted()) { "Unterminated unicode escape" }
                        append(readChar())
                    }
                }
                hex.toInt(16).toChar()
            }
            else -> throw IllegalArgumentException("Invalid escape character '\\$ch'")
        }
    }

    private fun parseBoolean(): SchemaValue.Bool {
        val word = readLiteral()
        return when (word) {
            "true" -> SchemaValue.Bool(true)
            "false" -> SchemaValue.Bool(false)
            else -> throw IllegalArgumentException("Expected 'true' or 'false', got '$word'")
        }
    }

    private fun parseNull(): SchemaValue.Null {
        val word = readLiteral()
        require(word == "null") { "Expected 'null', got '$word'" }
        return SchemaValue.Null
    }

    private fun parseNumber(): SchemaValue {
        val token = readNumberToken()
        val longValue = token.toLongOrNull()
        if (longValue != null) return SchemaValue.Integer(longValue)
        val doubleValue = token.toDoubleOrNull()
            ?: throw IllegalArgumentException("Invalid number: '$token'")
        return SchemaValue.Decimal(doubleValue)
    }

    private fun readNumberToken(): String {
        val sb = StringBuilder()
        while (!source.exhausted()) {
            val ch = peekChar()
            if (ch.isDigit() || ch == '-' || ch == '+' || ch == '.' || ch == 'e' || ch == 'E') {
                sb.append(readChar())
            } else {
                break
            }
        }
        require(sb.isNotEmpty()) { "Expected number" }
        return sb.toString()
    }

    private fun parseObject(): SchemaValue.Obj {
        expectChar('{')
        skipWhitespaceAndComments()
        val entries = linkedMapOf<String, SchemaValue>()
        if (peekCharOrNull() == '}') {
            readChar()
            return SchemaValue.Obj(entries)
        }
        while (true) {
            skipWhitespaceAndComments()
            val key = readJsonString()
            skipWhitespaceAndComments()
            expectChar(':')
            val value = parseValue()
            entries[key] = value
            skipWhitespaceAndComments()
            when (peekCharOrNull()) {
                ',' -> {
                    readChar()
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && peekCharOrNull() == '}') {
                        readChar()
                        return SchemaValue.Obj(entries)
                    }
                }
                '}' -> {
                    readChar()
                    return SchemaValue.Obj(entries)
                }
                else -> throw IllegalArgumentException("Expected ',' or '}' in object")
            }
        }
    }

    private fun parseArray(): SchemaValue.Arr {
        expectChar('[')
        skipWhitespaceAndComments()
        val items = mutableListOf<SchemaValue>()
        if (peekCharOrNull() == ']') {
            readChar()
            return SchemaValue.Arr(items)
        }
        while (true) {
            items.add(parseValue())
            skipWhitespaceAndComments()
            when (peekCharOrNull()) {
                ',' -> {
                    readChar()
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && peekCharOrNull() == ']') {
                        readChar()
                        return SchemaValue.Arr(items)
                    }
                }
                ']' -> {
                    readChar()
                    return SchemaValue.Arr(items)
                }
                else -> throw IllegalArgumentException("Expected ',' or ']' in array")
            }
        }
    }

    private fun readLiteral(): String {
        val sb = StringBuilder()
        while (!source.exhausted()) {
            val ch = peekChar()
            if (ch.isLetter()) {
                sb.append(readChar())
            } else {
                break
            }
        }
        return sb.toString()
    }

    private fun skipWhitespaceAndComments() {
        while (!source.exhausted()) {
            val ch = peekChar()
            when {
                ch.isWhitespace() -> readChar()
                config.allowComments && ch == '/' -> {
                    val peek = source.peek()
                    peek.readByte() // consume '/'
                    if (!peek.exhausted() && peek.readByte().toInt().toChar() == '/') {
                        // Line comment: consume '//' and everything until newline
                        readChar() // '/'
                        readChar() // '/'
                        while (!source.exhausted()) {
                            val c = readChar()
                            if (c == '\n') break
                        }
                    } else {
                        return
                    }
                }
                else -> return
            }
        }
    }

    private fun readChar(): Char =
        source.readByte().toInt().toChar()

    private fun peekChar(): Char =
        source.peek().readByte().toInt().toChar()

    private fun peekCharOrNull(): Char? =
        if (source.exhausted()) null else peekChar()

    private fun expectChar(expected: Char) {
        require(!source.exhausted()) { "Expected '$expected' but reached end of input" }
        val ch = readChar()
        require(ch == expected) { "Expected '$expected' but got '$ch'" }
    }

    private fun Char.isDigit(): Boolean = this in '0'..'9'
    private fun Char.isLetter(): Boolean = this in 'a'..'z' || this in 'A'..'Z'
    private fun Char.isWhitespace(): Boolean = this == ' ' || this == '\t' || this == '\n' || this == '\r'
}
