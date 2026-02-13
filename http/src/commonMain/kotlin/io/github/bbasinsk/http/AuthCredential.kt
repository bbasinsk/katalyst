package io.github.bbasinsk.http

sealed interface AuthCredential {
    data class BearerToken(val token: String) : AuthCredential
    data class BasicCredentials(val encoded: String) : AuthCredential
    data class ApiKey(val key: String) : AuthCredential
    data class CookieValue(val value: String) : AuthCredential
}
