package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue

fun decodeSchemaValueFromBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue =
    JsonParser(bytes, config).parseValue()

private class JsonParser(private val buffer: ByteArray, private val config: JsonDecodingConfig) {
    private var pos: Int = 0

    companion object {
        private const val QUOTE: Byte = '"'.code.toByte()
        private const val BACKSLASH: Byte = '\\'.code.toByte()
    }

    fun parseValue(): SchemaValue {
        skipWhitespaceAndComments()
        require(pos < buffer.size) { "Unexpected end of input" }
        val ch = buffer[pos].toInt().toChar()
        return when {
            ch == '"' -> parseString()
            ch == 't' || ch == 'f' -> parseBoolean()
            ch == 'n' -> parseNull()
            ch == '{' -> parseObject()
            ch == '[' -> parseArray()
            ch == '-' || ch in '0'..'9' -> parseNumber()
            else -> throw IllegalArgumentException("Unexpected character '$ch'")
        }
    }

    private fun parseString(): SchemaValue.Str =
        SchemaValue.Str(readJsonString())

    private fun readJsonString(): String {
        expectChar('"')
        val start = pos
        // Fast path: scan for closing quote with no escapes
        while (pos < buffer.size) {
            when (buffer[pos]) {
                QUOTE -> return buffer.decodeToString(start, pos++)
                BACKSLASH -> return readJsonStringSlow(start)
                else -> pos++
            }
        }
        throw IllegalArgumentException("Unterminated string")
    }

    private fun readJsonStringSlow(start: Int): String {
        // Copy the unescaped prefix we already scanned
        val sb = StringBuilder(pos - start + 16)
        sb.append(buffer.decodeToString(start, pos))
        while (true) {
            require(pos < buffer.size) { "Unterminated string" }
            val b = buffer[pos++]
            when (b) {
                QUOTE -> return sb.toString()
                BACKSLASH -> sb.append(readEscaped())
                else -> sb.append(b.toInt().toChar())
            }
        }
    }

    private fun readEscaped(): Char {
        require(pos < buffer.size) { "Unterminated escape sequence" }
        val ch = buffer[pos++].toInt().toChar()
        return when (ch) {
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
                        require(pos < buffer.size) { "Unterminated unicode escape" }
                        append(buffer[pos++].toInt().toChar())
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
        val start = pos
        while (pos < buffer.size) {
            val ch = buffer[pos].toInt().toChar()
            if (ch in '0'..'9' || ch == '-' || ch == '+' || ch == '.' || ch == 'e' || ch == 'E') {
                pos++
            } else {
                break
            }
        }
        require(pos > start) { "Expected number" }
        return buffer.decodeToString(start, pos)
    }

    private fun parseObject(): SchemaValue.Obj {
        expectChar('{')
        skipWhitespaceAndComments()
        val entries = linkedMapOf<String, SchemaValue>()
        if (pos < buffer.size && buffer[pos].toInt().toChar() == '}') {
            pos++
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
            val next = if (pos < buffer.size) buffer[pos].toInt().toChar() else null
            when (next) {
                ',' -> {
                    pos++
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && pos < buffer.size && buffer[pos].toInt().toChar() == '}') {
                        pos++
                        return SchemaValue.Obj(entries)
                    }
                }
                '}' -> {
                    pos++
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
        if (pos < buffer.size && buffer[pos].toInt().toChar() == ']') {
            pos++
            return SchemaValue.Arr(items)
        }
        while (true) {
            items.add(parseValue())
            skipWhitespaceAndComments()
            val next = if (pos < buffer.size) buffer[pos].toInt().toChar() else null
            when (next) {
                ',' -> {
                    pos++
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && pos < buffer.size && buffer[pos].toInt().toChar() == ']') {
                        pos++
                        return SchemaValue.Arr(items)
                    }
                }
                ']' -> {
                    pos++
                    return SchemaValue.Arr(items)
                }
                else -> throw IllegalArgumentException("Expected ',' or ']' in array")
            }
        }
    }

    private fun readLiteral(): String {
        val start = pos
        while (pos < buffer.size) {
            val ch = buffer[pos].toInt().toChar()
            if (ch in 'a'..'z' || ch in 'A'..'Z') {
                pos++
            } else {
                break
            }
        }
        return buffer.decodeToString(start, pos)
    }

    private fun skipWhitespaceAndComments() {
        while (pos < buffer.size) {
            val ch = buffer[pos].toInt().toChar()
            when {
                ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r' -> pos++
                config.allowComments && ch == '/' -> {
                    if (pos + 1 < buffer.size && buffer[pos + 1].toInt().toChar() == '/') {
                        pos += 2
                        while (pos < buffer.size) {
                            if (buffer[pos++].toInt().toChar() == '\n') break
                        }
                    } else {
                        return
                    }
                }
                else -> return
            }
        }
    }

    private fun expectChar(expected: Char) {
        require(pos < buffer.size) { "Expected '$expected' but reached end of input" }
        val ch = buffer[pos++].toInt().toChar()
        require(ch == expected) { "Expected '$expected' but got '$ch'" }
    }
}
