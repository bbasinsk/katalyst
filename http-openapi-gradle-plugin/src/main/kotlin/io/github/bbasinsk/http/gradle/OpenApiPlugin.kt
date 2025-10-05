package io.github.bbasinsk.http.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class OpenApiPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("openApi", OpenApiExtension::class.java)

        // Set default output file location
        extension.outputFile.convention(
            project.layout.buildDirectory.file("generated/openapi/openapi.json")
        )

        // Set default empty servers list
        extension.servers.convention(emptyList())

        project.afterEvaluate {
            require(extension.info.isPresent) {
                "OpenAPI info must be configured. Add the following to your build.gradle.kts:\n" +
                "openApi {\n" +
                "    info.set(Info(title = \"Your API\", version = \"1.0.0\"))\n" +
                "}"
            }

            val generateTask = project.tasks.register("generateOpenApi", GenerateOpenApiTask::class.java) { task ->
                task.info.set(extension.info)
                task.servers.set(extension.servers)
                task.outputFile.set(extension.outputFile)

                // Configure classpath from Kotlin compilation
                project.extensions.findByType(KotlinJvmProjectExtension::class.java)?.let { kotlinExt ->
                    val mainSourceSet = kotlinExt.target.compilations.getByName("main")
                    task.classpath.from(mainSourceSet.output.classesDirs)
                    task.classpath.from(mainSourceSet.compileDependencyFiles)

                    // Depend on compilation
                    task.dependsOn(mainSourceSet.compileTaskProvider)
                } ?: run {
                    // Fallback to Java source sets if Kotlin is not available
                    val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
                    val mainSourceSet = sourceSets.getByName("main")
                    task.classpath.from(mainSourceSet.output.classesDirs)
                    task.classpath.from(mainSourceSet.runtimeClasspath)

                    // Depend on compilation
                    task.dependsOn(project.tasks.named("classes"))
                }

                task.group = "documentation"
                task.description = "Generates OpenAPI specification from HttpEndpointGroup objects"
            }

            // Optionally attach to build lifecycle
            project.tasks.named("build").configure {
                it.dependsOn(generateTask)
            }
        }
    }
}
