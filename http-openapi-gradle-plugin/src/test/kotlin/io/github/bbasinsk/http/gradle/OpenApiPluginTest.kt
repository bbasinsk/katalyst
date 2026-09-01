package io.github.bbasinsk.http.gradle

import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createTempDirectory
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.gradle.testkit.runner.GradleRunner

class OpenApiPluginTest {
    @Test
    fun `endpoint discovery loads project classes with runtime dependencies`() {
        val root = createTempDirectory("openapi-runtime-classpath")
        val kotlinVersion = requireNotNull(System.getProperty("kotlinVersion"))
        root.write(
            "settings.gradle.kts",
            """
            pluginManagement {
                repositories {
                    gradlePluginPortal()
                    mavenCentral()
                }
            }
            dependencyResolutionManagement {
                repositories { mavenCentral() }
            }
            rootProject.name = "openapi-runtime-classpath"
            include("app", "feature", "runtime")
            """.trimIndent(),
        )
        root.write("build.gradle.kts", "")
        root.write(
            "runtime/build.gradle.kts",
            """
            plugins { `java-library` }
            """.trimIndent(),
        )
        root.write(
            "runtime/src/main/java/example/runtime/RuntimeBase.java",
            """
            package example.runtime;
            public class RuntimeBase {}
            """.trimIndent(),
        )
        root.write(
            "feature/build.gradle.kts",
            """
            plugins { `java-library` }
            dependencies { implementation(project(":runtime")) }
            """.trimIndent(),
        )
        root.write(
            "feature/src/main/java/example/feature/RuntimeLinked.java",
            """
            package example.feature;
            public class RuntimeLinked extends example.runtime.RuntimeBase {}
            """.trimIndent(),
        )
        root.write(
            "app/build.gradle.kts",
            """
            plugins {
                kotlin("jvm") version "$kotlinVersion"
                id("io.github.bbasinsk.http-openapi")
            }
            dependencies { implementation(project(":feature")) }
            openApi {
                info {
                    title = "Test API"
                    version = "1.0.0"
                }
            }
            """.trimIndent(),
        )

        val result = GradleRunner.create()
            .withProjectDir(root.toFile())
            .withArguments(":app:generateOpenApi", "--stacktrace")
            .withPluginClasspath(
                System.getProperty("java.class.path")
                    .split(File.pathSeparator)
                    .map(::File)
            )
            .build()

        assertFalse(
            result.output.contains("Failed to load class example.feature.RuntimeLinked"),
            result.output,
        )
        assertTrue(root.resolve("app/build/generated/openapi/openapi.json").toFile().isFile)
    }

    private fun Path.write(relativePath: String, content: String) {
        resolve(relativePath).apply {
            parent.createDirectories()
            writeText(content)
        }
    }
}
