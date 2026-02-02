package io.github.bbasinsk.http

data class AuthConfig<A>(
    val schema: AuthSchema<A>,
    val onFailure: AuthFailure
)
