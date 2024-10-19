package io.github.bbasinsk.http.ktor

import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server

data class OpenApiBuilder(
    var jsonSpecPath: String = "/openapi.json",
    var info: Info = Info(),
    var servers: List<Server> = emptyList()
)
