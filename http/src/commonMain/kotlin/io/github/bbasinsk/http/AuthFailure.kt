package io.github.bbasinsk.http

sealed interface AuthFailure {
    data object Unauthorized : AuthFailure
    data class Redirect(val location: String) : AuthFailure {
        companion object {
            operator fun invoke(endpoint: Http<Unit, *, *, *, *>): Redirect =
                Redirect(endpoint.params.renderPath())
        }
    }
}
