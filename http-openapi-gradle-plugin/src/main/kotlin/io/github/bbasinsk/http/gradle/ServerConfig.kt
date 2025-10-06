package io.github.bbasinsk.http.gradle

import io.github.bbasinsk.http.openapi.Server
import java.io.Serializable

data class ServerConfig(
    val url: String,
    val description: String? = null
) : Serializable

class ServerConfigBuilder(var url: String) {
    var description: String? = null

    internal fun build(): ServerConfig {
        require(url.isNotEmpty()) { "Server url is required" }
        return ServerConfig(url = url, description = description)
    }
}

internal fun ServerConfig.toLibraryServer() = Server(
    url = url,
)
