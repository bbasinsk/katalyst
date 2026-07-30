package io.github.bbasinsk.schema

sealed interface JsonValue {
    data object Null : JsonValue
    data class Bool(val value: Boolean) : JsonValue

    /** A JSON number held as its source literal, so decode → encode is digit-exact. */
    data class Number(val literal: String) : JsonValue {
        init {
            require(isJsonNumberLiteral(literal)) { "Not an RFC 8259 JSON number: '$literal'" }
        }

        fun toIntOrNull(): Int? = literal.toIntOrNull()
        fun toLongOrNull(): Long? = literal.toLongOrNull()
        fun toDoubleOrNull(): Double? = literal.toDoubleOrNull()
        fun toFloatOrNull(): Float? = literal.toFloatOrNull()

        companion object {
            fun of(value: Int): Number = Number(value.toString())
            fun of(value: Long): Number = Number(value.toString())

            /** Non-finite values are not JSON numbers and are rejected. */
            fun of(value: Double): Number {
                require(!value.isNaN() && !value.isInfinite()) { "Non-finite value is not a JSON number: $value" }
                return Number(value.toString())
            }

            /** Non-finite values are not JSON numbers and are rejected. */
            fun of(value: Float): Number {
                require(!value.isNaN() && !value.isInfinite()) { "Non-finite value is not a JSON number: $value" }
                return Number(value.toString())
            }

            fun parseOrNull(literal: String): Number? =
                if (isJsonNumberLiteral(literal)) Number(literal) else null
        }
    }

    data class Str(val value: String) : JsonValue
    data class Arr(val values: List<JsonValue>) : JsonValue
    data class Obj(val entries: Map<String, JsonValue>) : JsonValue
}

private fun isJsonNumberLiteral(s: String): Boolean {
    var i = 0
    val n = s.length
    if (n == 0) return false
    if (s[i] == '-') i++
    if (i >= n) return false
    if (s[i] == '0') i++
    else if (s[i] in '1'..'9') while (i < n && s[i] in '0'..'9') i++
    else return false
    if (i < n && s[i] == '.') {
        i++
        if (i >= n || s[i] !in '0'..'9') return false
        while (i < n && s[i] in '0'..'9') i++
    }
    if (i < n && (s[i] == 'e' || s[i] == 'E')) {
        i++
        if (i < n && (s[i] == '+' || s[i] == '-')) i++
        if (i >= n || s[i] !in '0'..'9') return false
        while (i < n && s[i] in '0'..'9') i++
    }
    return i == n
}

@Deprecated("Renamed to JsonValue", ReplaceWith("JsonValue"))
typealias SchemaValue = JsonValue
