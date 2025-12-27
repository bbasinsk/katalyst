package io.github.bbasinsk.http

/**
 * Describes authentication requirements for an endpoint.
 * Uses phantom type [A] to carry the principal type without needing a validator instance.
 *
 * The actual validation logic is provided via [AuthValidator] at handler registration time.
 */
sealed interface AuthSchema<A> {
    data object None : AuthSchema<Unit>

    class Bearer<A> private constructor(val format: String?) : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(format: String? = null): Bearer<A> = Bearer(format)
        }
    }

    class Basic<A> private constructor() : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(): Basic<A> = Basic()
        }
    }

    class ApiKeyHeader<A> private constructor(val name: String) : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(name: String): ApiKeyHeader<A> = ApiKeyHeader(name)
        }
    }

    data class Optional<A>(val inner: AuthSchema<A>) : AuthSchema<A?>

    fun isOptional(): Boolean = when (this) {
        is None -> false
        is Bearer<*> -> false
        is Basic<*> -> false
        is ApiKeyHeader<*> -> false
        is Optional<*> -> true
    }

    companion object {
        fun <A> bearer(format: String? = null): Bearer<A> = Bearer(format)
        fun <A> basic(): Basic<A> = Basic()
        fun <A> apiKeyHeader(name: String): ApiKeyHeader<A> = ApiKeyHeader(name)
    }
}

fun <A> AuthSchema<A>.optional(): AuthSchema<A?> = AuthSchema.Optional(this)
