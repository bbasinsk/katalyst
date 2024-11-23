package io.github.bbasinsk.http.ktor

import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server

data class OpenApiBuilder(
    val jsonSpecPath: String,
    val info: Info,
    val servers: List<Server>
)
