package io.github.bbasinsk.schema

fun Schema<*>.isRequired(): Boolean =
    when(this) {
        is Schema.Empty -> false
        is Schema.Optional<*> -> false
        is Schema.Default<*> -> false
        is Schema.Bytes -> true
        is Schema.Primitive -> true
        is Schema.Record -> true
        is Schema.Union -> true
        is Schema.StringMap<*> -> true
        is Schema.Collection<*> -> true
        is Schema.Lazy<*> -> schema().isRequired()
        is Schema.OrElse<*, *> -> preferred.isRequired()
        is Schema.Transform<*, *> -> schema.isRequired()
    }
