package io.github.bbasinsk.schema


@Suppress("UNCHECKED_CAST")
fun <A> Schema<A>.decodeString(str: kotlin.String): Result<A> =
    when (this) {
        is Schema.Primitive.String -> Result.success(str as A)
        is Schema.Primitive.Int -> runCatching { str.toInt() as A }
        is Schema.Primitive.Long -> runCatching { str.toLong() as A }
        is Schema.Primitive.Float -> runCatching { str.toFloat() as A }
        is Schema.Primitive.Double -> runCatching { str.toDouble() as A }
        is Schema.Primitive.Boolean -> runCatching { str.toBooleanStrict() as A }
        is Schema.Primitive.Enumeration<*> -> runCatching { values.first { it.toString() == str } as A }
        is Schema.Default -> schema.decodeString(str).recoverCatching { default as A }
        is Schema.Empty -> Result.success(null as A)
        is Schema.Lazy -> schema().decodeString(str)
        is Schema.Optional<*> -> schema.decodeString(str).recoverCatching { null } as Result<A>
        is Schema.OrElse -> preferred.decodeString(str).recoverCatching { fallback.decodeString(str).getOrThrow() }
        is Schema.Transform<A, *> -> runCatching {
            (this as Schema.Transform<A, Any?>).decode(schema.decodeString(str).getOrThrow())
        } as Result<A>

        is Schema.StringMap<*> -> Result.failure(IllegalStateException("Cannot decode StringMap from String"))
        is Schema.Record -> Result.failure(IllegalStateException("Cannot decode Record from String"))
        is Schema.Union -> Result.failure(IllegalStateException("Cannot decode Union from String"))
        is Schema.Bytes -> Result.failure(IllegalStateException("Cannot decode Bytes from String"))
        is Schema.Collection<*> -> Result.failure(IllegalStateException("Cannot decode Collection from String"))
    }
