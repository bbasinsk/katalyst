package io.github.bbasinsk.http.gradle

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.HttpEndpointGroup
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.OpenApiJson
import io.github.bbasinsk.http.openapi.Server
import io.github.bbasinsk.http.openapi.toOpenApiSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.net.URLClassLoader

abstract class GenerateOpenApiTask : DefaultTask() {

    @get:InputFiles
    @get:Classpath
    abstract val classpath: ConfigurableFileCollection

    @get:Input
    abstract val info: Property<Info>

    @get:Input
    abstract val servers: ListProperty<Server>

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val urls = classpath.files.map { it.toURI().toURL() }.toTypedArray()
        val classLoader = URLClassLoader(urls, HttpEndpointGroup::class.java.classLoader)

        val endpointGroups = discoverEndpointGroups(classLoader)
        val apis = endpointGroups.flatMap { it.apis }

        val openApiSpec = apis.toOpenApiSpec(
            info = info.get(),
            servers = servers.get()
        )

        val jsonSpec = OpenApiJson.encodeToString(
            io.github.bbasinsk.http.openapi.OpenAPI.serializer(),
            openApiSpec
        )

        outputFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(jsonSpec)
        }

        logger.lifecycle("Generated OpenAPI spec at ${outputFile.get().asFile.absolutePath}")
        logger.lifecycle("Discovered ${endpointGroups.size} endpoint groups with ${apis.size} total endpoints")
    }

    private fun discoverEndpointGroups(classLoader: URLClassLoader): List<HttpEndpointGroup> {
        val endpointGroups = mutableListOf<HttpEndpointGroup>()

        // Scan for classes that extend HttpEndpointGroup
        classpath.files.forEach { file ->
            if (file.extension == "jar") {
                // Scan JAR file
                java.util.jar.JarFile(file).use { jar ->
                    jar.entries().asSequence()
                        .filter { it.name.endsWith(".class") }
                        .forEach { entry ->
                            val className = entry.name
                                .replace('/', '.')
                                .removeSuffix(".class")

                            try {
                                val clazz = classLoader.loadClass(className)
                                if (HttpEndpointGroup::class.java.isAssignableFrom(clazz) &&
                                    clazz.kotlin.objectInstance != null) {
                                    val instance = clazz.kotlin.objectInstance as HttpEndpointGroup
                                    endpointGroups.add(instance)
                                }
                            } catch (e: Throwable) {
                                // Skip classes that can't be loaded
                            }
                        }
                }
            } else if (file.isDirectory) {
                // Scan directory
                file.walkTopDown()
                    .filter { it.extension == "class" }
                    .forEach { classFile ->
                        val className = classFile.relativeTo(file).path
                            .replace('/', '.')
                            .removeSuffix(".class")

                        try {
                            val clazz = classLoader.loadClass(className)
                            if (HttpEndpointGroup::class.java.isAssignableFrom(clazz) &&
                                clazz.kotlin.objectInstance != null) {
                                val instance = clazz.kotlin.objectInstance as HttpEndpointGroup
                                endpointGroups.add(instance)
                            }
                        } catch (e: Throwable) {
                            // Skip classes that can't be loaded
                        }
                    }
            }
        }

        return endpointGroups
    }
}
