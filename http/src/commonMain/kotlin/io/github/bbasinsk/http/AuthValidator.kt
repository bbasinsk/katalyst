package io.github.bbasinsk.http

/**
 * Validates authentication credentials and returns the principal.
 *
 * Provided at handler registration time to validate bearer tokens.
 * Returns null if credentials are invalid.
 */
fun interface AuthValidator<A> {
    suspend fun validate(token: String): A?
}

fun <A> AuthValidator<A>.optional(): AuthValidator<A?> =
    AuthValidator { token -> validate(token) }
