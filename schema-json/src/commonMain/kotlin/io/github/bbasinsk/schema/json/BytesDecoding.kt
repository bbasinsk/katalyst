package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.SchemaValue

fun decodeSchemaValueFromBytes(bytes: ByteArray, config: JsonDecodingConfig = JsonDecodingConfig()): SchemaValue {
    val parser = JsonParser(bytes, config)
    val value = parser.parseValue()
    parser.expectEndOfInput()
    return value
}

private class JsonParser(private val buffer: ByteArray, private val config: JsonDecodingConfig) {
    private var pos: Int = 0

    fun expectEndOfInput() {
        skipWhitespaceAndComments()
        require(pos == buffer.size) { "Unexpected trailing content at position $pos" }
    }

    fun parseValue(): SchemaValue {
        skipWhitespaceAndComments()
        require(pos < buffer.size) { "Unexpected end of input" }
        return when (buffer[pos]) {
            QUOTE -> parseString()
            B_t, B_f -> parseBoolean()
            B_n -> parseNull()
            LBRACE -> parseObject()
            LBRACKET -> parseArray()
            MINUS, B_0, B_1, B_2, B_3, B_4, B_5, B_6, B_7, B_8, B_9 -> parseNumber()
            else -> throw IllegalArgumentException("Unexpected character '${buffer[pos].toInt().toChar()}'")
        }
    }

    private fun parseString(): SchemaValue.Str =
        SchemaValue.Str(readJsonString())

    private fun readJsonString(): String {
        pos++ // consume opening quote
        val start = pos
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
        val sb = StringBuilder(pos - start + 16)
        sb.append(buffer.decodeToString(start, pos))
        var segmentStart = pos
        while (true) {
            require(pos < buffer.size) { "Unterminated string" }
            when (buffer[pos]) {
                QUOTE -> {
                    if (segmentStart < pos) sb.append(buffer.decodeToString(segmentStart, pos))
                    pos++
                    return sb.toString()
                }
                BACKSLASH -> {
                    if (segmentStart < pos) sb.append(buffer.decodeToString(segmentStart, pos))
                    pos++
                    sb.append(readEscaped())
                    segmentStart = pos
                }
                else -> pos++
            }
        }
    }

    private fun readEscaped(): Char {
        require(pos < buffer.size) { "Unterminated escape sequence" }
        return when (buffer[pos++]) {
            QUOTE -> '"'
            BACKSLASH -> '\\'
            B_SLASH -> '/'
            B_b -> '\b'
            B_f -> '\u000C'
            B_n -> '\n'
            B_r -> '\r'
            B_t -> '\t'
            B_u -> {
                require(pos + 4 <= buffer.size) { "Unterminated unicode escape" }
                val hex = buffer.decodeToString(pos, pos + 4)
                pos += 4
                hex.toInt(16).toChar()
            }
            else -> throw IllegalArgumentException("Invalid escape character '\\${buffer[pos - 1].toInt().toChar()}'")
        }
    }

    private fun parseBoolean(): SchemaValue.Bool =
        if (buffer[pos] == B_t) {
            require(pos + 4 <= buffer.size && buffer[pos + 1] == B_r && buffer[pos + 2] == B_u && buffer[pos + 3] == B_e) {
                "Expected 'true', got '${readLiteralForError()}'"
            }
            pos += 4
            SchemaValue.Bool(true)
        } else {
            require(pos + 5 <= buffer.size && buffer[pos + 1] == B_a && buffer[pos + 2] == B_l && buffer[pos + 3] == B_s && buffer[pos + 4] == B_e) {
                "Expected 'false', got '${readLiteralForError()}'"
            }
            pos += 5
            SchemaValue.Bool(false)
        }

    private fun parseNull(): SchemaValue.Null {
        require(pos + 4 <= buffer.size && buffer[pos + 1] == B_u && buffer[pos + 2] == B_l && buffer[pos + 3] == B_l) {
            "Expected 'null', got '${readLiteralForError()}'"
        }
        pos += 4
        return SchemaValue.Null
    }

    private fun readLiteralForError(): String {
        val start = pos
        while (pos < buffer.size) {
            val b = buffer[pos]
            if (b in B_a..B_z || b in B_A..B_Z) pos++ else break
        }
        return buffer.decodeToString(start, pos)
    }

    private fun parseNumber(): SchemaValue {
        val start = pos
        var isDecimal = false
        if (pos < buffer.size && buffer[pos] == MINUS) pos++
        while (pos < buffer.size && buffer[pos] in B_0..B_9) pos++
        if (pos < buffer.size && buffer[pos] == DOT) {
            isDecimal = true
            pos++
            while (pos < buffer.size && buffer[pos] in B_0..B_9) pos++
        }
        if (pos < buffer.size && (buffer[pos] == B_e || buffer[pos] == B_E)) {
            isDecimal = true
            pos++
            if (pos < buffer.size && (buffer[pos] == PLUS || buffer[pos] == MINUS)) pos++
            while (pos < buffer.size && buffer[pos] in B_0..B_9) pos++
        }
        require(pos > start) { "Expected number" }
        if (!isDecimal) {
            val longValue = parseLongDirect(start, pos)
            if (longValue != null) return SchemaValue.Integer(longValue)
        }
        val token = buffer.decodeToString(start, pos)
        val doubleValue = token.toDoubleOrNull()
            ?: throw IllegalArgumentException("Invalid number: '$token'")
        return SchemaValue.Decimal(doubleValue)
    }

    private fun parseLongDirect(start: Int, end: Int): Long? {
        var i = start
        val negative = buffer[i] == MINUS
        if (negative) i++
        if (i == end) return null
        var result = 0L
        while (i < end) {
            val digit = buffer[i] - B_0
            if (digit < 0 || digit > 9) return null
            if (result < Long.MIN_VALUE / 10) return null
            result = result * 10 - digit
            i++
        }
        return if (negative) result else if (result == Long.MIN_VALUE) null else -result
    }

    private fun parseObject(): SchemaValue.Obj {
        pos++ // consume '{'
        skipWhitespaceAndComments()
        val entries = linkedMapOf<String, SchemaValue>()
        if (pos < buffer.size && buffer[pos] == RBRACE) {
            pos++
            return SchemaValue.Obj(entries)
        }
        while (true) {
            skipWhitespaceAndComments()
            val key = readJsonString()
            skipWhitespaceAndComments()
            expect(COLON)
            val value = parseValue()
            entries[key] = value
            skipWhitespaceAndComments()
            when {
                pos < buffer.size && buffer[pos] == COMMA -> {
                    pos++
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && pos < buffer.size && buffer[pos] == RBRACE) {
                        pos++
                        return SchemaValue.Obj(entries)
                    }
                }
                pos < buffer.size && buffer[pos] == RBRACE -> {
                    pos++
                    return SchemaValue.Obj(entries)
                }
                else -> throw IllegalArgumentException("Expected ',' or '}' in object")
            }
        }
    }

    private fun parseArray(): SchemaValue.Arr {
        pos++ // consume '['
        skipWhitespaceAndComments()
        val items = mutableListOf<SchemaValue>()
        if (pos < buffer.size && buffer[pos] == RBRACKET) {
            pos++
            return SchemaValue.Arr(items)
        }
        while (true) {
            items.add(parseValue())
            skipWhitespaceAndComments()
            when {
                pos < buffer.size && buffer[pos] == COMMA -> {
                    pos++
                    skipWhitespaceAndComments()
                    if (config.allowTrailingCommas && pos < buffer.size && buffer[pos] == RBRACKET) {
                        pos++
                        return SchemaValue.Arr(items)
                    }
                }
                pos < buffer.size && buffer[pos] == RBRACKET -> {
                    pos++
                    return SchemaValue.Arr(items)
                }
                else -> throw IllegalArgumentException("Expected ',' or ']' in array")
            }
        }
    }

    private fun skipWhitespaceAndComments() {
        while (pos < buffer.size) {
            when (buffer[pos]) {
                SPACE, TAB, LF, CR -> pos++
                B_SLASH -> if (config.allowComments && pos + 1 < buffer.size && buffer[pos + 1] == B_SLASH) {
                    pos += 2
                    while (pos < buffer.size) { if (buffer[pos++] == LF) break }
                } else return
                else -> return
            }
        }
    }

    private fun expect(expected: Byte) {
        require(pos < buffer.size) { "Expected '${expected.toInt().toChar()}' but reached end of input" }
        val b = buffer[pos++]
        require(b == expected) { "Expected '${expected.toInt().toChar()}' but got '${b.toInt().toChar()}'" }
    }

    companion object {
        private const val QUOTE: Byte = '"'.code.toByte()
        private const val BACKSLASH: Byte = '\\'.code.toByte()
        private const val B_SLASH: Byte = '/'.code.toByte()
        private const val LBRACE: Byte = '{'.code.toByte()
        private const val RBRACE: Byte = '}'.code.toByte()
        private const val LBRACKET: Byte = '['.code.toByte()
        private const val RBRACKET: Byte = ']'.code.toByte()
        private const val COMMA: Byte = ','.code.toByte()
        private const val COLON: Byte = ':'.code.toByte()
        private const val MINUS: Byte = '-'.code.toByte()
        private const val PLUS: Byte = '+'.code.toByte()
        private const val DOT: Byte = '.'.code.toByte()
        private const val SPACE: Byte = ' '.code.toByte()
        private const val TAB: Byte = '\t'.code.toByte()
        private const val LF: Byte = '\n'.code.toByte()
        private const val CR: Byte = '\r'.code.toByte()
        private const val B_0: Byte = '0'.code.toByte()
        private const val B_1: Byte = '1'.code.toByte()
        private const val B_2: Byte = '2'.code.toByte()
        private const val B_3: Byte = '3'.code.toByte()
        private const val B_4: Byte = '4'.code.toByte()
        private const val B_5: Byte = '5'.code.toByte()
        private const val B_6: Byte = '6'.code.toByte()
        private const val B_7: Byte = '7'.code.toByte()
        private const val B_8: Byte = '8'.code.toByte()
        private const val B_9: Byte = '9'.code.toByte()
        private const val B_a: Byte = 'a'.code.toByte()
        private const val B_b: Byte = 'b'.code.toByte()
        private const val B_e: Byte = 'e'.code.toByte()
        private const val B_f: Byte = 'f'.code.toByte()
        private const val B_l: Byte = 'l'.code.toByte()
        private const val B_n: Byte = 'n'.code.toByte()
        private const val B_r: Byte = 'r'.code.toByte()
        private const val B_s: Byte = 's'.code.toByte()
        private const val B_t: Byte = 't'.code.toByte()
        private const val B_u: Byte = 'u'.code.toByte()
        private const val B_z: Byte = 'z'.code.toByte()
        private const val B_A: Byte = 'A'.code.toByte()
        private const val B_E: Byte = 'E'.code.toByte()
        private const val B_Z: Byte = 'Z'.code.toByte()
    }
}
