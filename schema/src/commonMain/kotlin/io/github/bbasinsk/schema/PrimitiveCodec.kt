package io.github.bbasinsk.schema


// TODO: Test this!

@Suppress("UNCHECKED_CAST")
fun <A> Schema<A>.decodePrimitiveString(str: String?): Result<A> =
    when (this) {
        is Schema.Default -> str?.let { schema.decodePrimitiveString(it).recoverCatching { default } } ?: Result.success(default)
        is Schema.Optional<*> -> str?.let { schema.decodePrimitiveString(it).recoverCatching { null } as Result<A> } ?: Result.success(null as A)
        is Schema.Empty -> Result.success(null as A)
        is Schema.Metadata -> schema.decodePrimitiveString(str)
        is Schema.Lazy -> schema().decodePrimitiveString(str)
        is Schema.OrElse<A, *> -> str?.let { s -> preferred.decodePrimitiveString(s).recoverCatching { unsafeDecode(fallback.decodePrimitiveString(s).getOrThrow()).getOrThrow() } } ?: preferred.decodePrimitiveString(null)
        is Schema.Transform<A, *> -> schema.decodePrimitiveString(str).mapCatching { (this as Schema.Transform<A, Any?>).decode(it).getOrThrow() }
        is Schema.Primitive.String -> str?.let { Result.success(it as A) } ?: Result.failure(IllegalArgumentException("Expected non-null string"))
        is Schema.Primitive.Int -> str?.runCatching { toInt() as A } ?: Result.failure(IllegalArgumentException("Expected non-null int"))
        is Schema.Primitive.Long -> str?.runCatching { toLong() as A } ?: Result.failure(IllegalArgumentException("Expected non-null long"))
        is Schema.Primitive.Float -> str?.runCatching { toFloat() as A } ?: Result.failure(IllegalArgumentException("Expected non-null float"))
        is Schema.Primitive.Double -> str?.runCatching { toDouble() as A } ?: Result.failure(IllegalArgumentException("Expected non-null double"))
        is Schema.Primitive.Boolean -> str?.runCatching { toBooleanStrict() as A } ?: Result.failure(IllegalArgumentException("Expected non-null boolean"))
        is Schema.Primitive.Enumeration -> str?.runCatching { values.first { it.toString() == str } } ?: Result.failure(IllegalArgumentException("Expected non-null enum value"))
        is Schema.StringMap<*> -> Result.failure(IllegalStateException("Cannot decode StringMap from String"))
        is Schema.Record -> Result.failure(IllegalStateException("Cannot decode Record from String"))
        is Schema.Union -> Result.failure(IllegalStateException("Cannot decode Union from String"))
        is Schema.Bytes -> Result.failure(IllegalStateException("Cannot decode Bytes from String"))
        is Schema.Collection<*> -> Result.failure(IllegalStateException("Cannot decode Collection from String"))
    }

@Suppress("UNCHECKED_CAST")
fun <A> Schema<A>.encodePrimitiveString(value: A): Result<String> =
    when (this) {
        is Schema.Primitive -> Result.success(value.toString())
        is Schema.Default -> schema.encodePrimitiveString(value)
        is Schema.Empty -> Result.success("")
        is Schema.Lazy -> schema().encodePrimitiveString(value)
        is Schema.Metadata -> schema.encodePrimitiveString(value)
        is Schema.Optional<*> -> (schema as Schema<Any?>).encodePrimitiveString(value).recoverCatching { "" }
        is Schema.OrElse<A, *> -> preferred.encodePrimitiveString(value)
        is Schema.Transform<A, *> -> runCatching { (this as Schema.Transform<A, Any?>).encode(value).let { schema.encodePrimitiveString(it).getOrThrow() } }
        is Schema.StringMap<*> -> Result.failure(IllegalStateException("Cannot encode StringMap to String"))
        is Schema.Record -> Result.failure(IllegalStateException("Cannot encode Record to String"))
        is Schema.Union -> Result.failure(IllegalStateException("Cannot encode Union to String"))
        is Schema.Bytes -> Result.failure(IllegalStateException("Cannot encode Bytes to String"))
        is Schema.Collection<*> -> Result.failure(IllegalStateException("Cannot encode Collection to String"))
    }
