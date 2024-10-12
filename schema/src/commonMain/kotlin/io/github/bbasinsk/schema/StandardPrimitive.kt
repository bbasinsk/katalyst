package io.github.bbasinsk.schema

sealed class StandardPrimitive<A>(val parse: (kotlin.String) -> A?) {
    data object Boolean : StandardPrimitive<kotlin.Boolean>({ it.lowercase().toBooleanStrictOrNull() })
    data object String : StandardPrimitive<kotlin.String>({ it })
    data object Int : StandardPrimitive<kotlin.Int>({ it.toIntOrNull() })
    data object Long : StandardPrimitive<kotlin.Long>({ it.toLongOrNull() })
    data object Double : StandardPrimitive<kotlin.Double>({ it.toDoubleOrNull() })
    data object Float : StandardPrimitive<kotlin.Float>({ it.toFloatOrNull() })
}
