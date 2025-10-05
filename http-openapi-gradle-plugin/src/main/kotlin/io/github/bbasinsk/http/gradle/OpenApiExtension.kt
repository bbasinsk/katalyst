package io.github.bbasinsk.http.gradle

import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.Server
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

abstract class OpenApiExtension {
    abstract val info: Property<Info>
    abstract val servers: ListProperty<Server>
    abstract val outputFile: RegularFileProperty
}
