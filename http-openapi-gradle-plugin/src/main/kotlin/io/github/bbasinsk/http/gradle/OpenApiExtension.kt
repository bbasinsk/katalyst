package io.github.bbasinsk.http.gradle

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

abstract class OpenApiExtension {
    abstract val info: Property<InfoConfig>
    abstract val servers: ListProperty<ServerConfig>
    abstract val outputFile: RegularFileProperty

    fun info(configure: InfoConfigBuilder.() -> Unit) {
        val builder = InfoConfigBuilder()
        builder.configure()
        info.set(builder.build())
    }

    fun server(url: String, configure: ServerConfigBuilder.() -> Unit = {}) {
        val builder = ServerConfigBuilder(url)
        builder.configure()
        servers.add(builder.build())
    }
}
