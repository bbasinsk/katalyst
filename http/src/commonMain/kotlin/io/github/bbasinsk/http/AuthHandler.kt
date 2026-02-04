package io.github.bbasinsk.http

sealed interface AuthResult<out A> {
    data class Success<A>(val principal: A) : AuthResult<A>
    data object Unauthorized : AuthResult<Nothing>
    data class Redirect(val location: String) : AuthResult<Nothing>
}

fun interface AuthHandler<A> {
    suspend fun handle(token: String?): AuthResult<A>

    companion object {
        private suspend fun <A> validateToken(
            token: String?,
            validate: suspend (String) -> A?,
            onFailure: AuthResult<Nothing>
        ): AuthResult<A> = when (val principal = token?.let { runCatching { validate(it) }.getOrNull() }) {
            null -> onFailure
            else -> AuthResult.Success(principal)
        }

        fun <A> standard(validate: suspend (String) -> A?): AuthHandler<A> =
            AuthHandler { token -> validateToken(token, validate, AuthResult.Unauthorized) }

        fun <A> withRedirect(location: String, validate: suspend (String) -> A?): AuthHandler<A> =
            AuthHandler { token -> validateToken(token, validate, AuthResult.Redirect(location)) }

        /**
         * Creates an AuthHandler that always succeeds with the given principal, ignoring any provided token.
         * WARNING: This bypasses authentication entirely. Only use for local development or testing.
         */
        fun <A> static(principal: A): AuthHandler<A> = AuthHandler { AuthResult.Success(principal) }
    }
}

fun <Auth> AuthHandler<Auth>.optional(): AuthHandler<Auth?> = AuthHandler { token ->
    when (val result = this@optional.handle(token)) {
        is AuthResult.Success -> AuthResult.Success(result.principal)
        is AuthResult.Unauthorized, is AuthResult.Redirect -> AuthResult.Success(null)
    }
}
