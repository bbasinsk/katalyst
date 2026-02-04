package io.github.bbasinsk.http

/**
 * Describes authentication requirements for an endpoint.
 * Uses phantom type [A] to carry the principal type without needing a handler instance.
 *
 * The actual validation logic is provided via [AuthHandler] at handler registration time.
 *
 * Each auth type includes a [schemeName] for OpenAPI security scheme generation.
 * Use unique names when multiple auth configurations of the same type exist.
 */
sealed interface AuthSchema<A> {
    val schemeName: String?

    data object None : AuthSchema<Unit> {
        override val schemeName: String? = null
    }

    class Bearer<A> private constructor(val format: String?, override val schemeName: String) : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(format: String? = null, schemeName: String = "bearerAuth"): Bearer<A> =
                Bearer(format, schemeName)
        }
    }

    class Basic<A> private constructor(override val schemeName: String) : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(schemeName: String = "basicAuth"): Basic<A> = Basic(schemeName)
        }
    }

    class ApiKeyHeader<A> private constructor(val headerName: String, override val schemeName: String) : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(headerName: String, schemeName: String = "apiKeyAuth"): ApiKeyHeader<A> =
                ApiKeyHeader(headerName, schemeName)
        }
    }

    class Cookie<A> private constructor(val cookieName: String, override val schemeName: String) : AuthSchema<A> {
        companion object {
            operator fun <A> invoke(cookieName: String, schemeName: String = "cookieAuth"): Cookie<A> =
                Cookie(cookieName, schemeName)
        }
    }

    data class Optional<A>(val inner: AuthSchema<A>) : AuthSchema<A?> {
        override val schemeName: String? = inner.schemeName
    }

    fun isOptional(): Boolean = when (this) {
        is None -> false
        is Bearer<*> -> false
        is Basic<*> -> false
        is ApiKeyHeader<*> -> false
        is Cookie<*> -> false
        is Optional<*> -> true
    }

    companion object {
        fun <A> bearer(format: String? = null, schemeName: String = "bearerAuth"): Bearer<A> =
            Bearer(format, schemeName)
        fun <A> basic(schemeName: String = "basicAuth"): Basic<A> = Basic(schemeName)
        fun <A> apiKeyHeader(headerName: String, schemeName: String = "apiKeyAuth"): ApiKeyHeader<A> =
            ApiKeyHeader(headerName, schemeName)
        fun <A> cookie(cookieName: String, schemeName: String = "cookieAuth"): Cookie<A> =
            Cookie(cookieName, schemeName)
    }
}

fun <A> AuthSchema<A>.optional(): AuthSchema<A?> = AuthSchema.Optional(this)
