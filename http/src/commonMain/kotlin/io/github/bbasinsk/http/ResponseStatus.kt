package io.github.bbasinsk.http

data class ResponseStatus(val code: Int, val description: String) {
    fun description(description: String): ResponseStatus = copy(description = description)
}
