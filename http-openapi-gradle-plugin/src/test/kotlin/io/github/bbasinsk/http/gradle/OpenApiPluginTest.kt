package io.github.bbasinsk.http.gradle

import io.github.bbasinsk.http.HttpEndpointGroup
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createTempDirectory
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.gradle.testkit.runner.GradleRunner

class OpenApiPluginTest {
    @Test
    fun `endpoint discovery loads project classes with runtime dependencies`() = withTempProject { root ->
        val kotlinVersion = requireNotNull(System.getProperty("kotlinVersion"))
        val httpClasspath = File(HttpEndpointGroup::class.java.protectionDomain.codeSource.location.toURI()).absolutePath
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
            dependencies {
                implementation(project(":feature"))
                implementation(files("${httpClasspath.replace("\\", "\\\\")}"))
            }
            openApi {
                info {
                    title = "Test API"
                    version = "1.0.0"
                }
            }
            """.trimIndent(),
        )
        root.write(
            "app/src/main/kotlin/example/app/PingEndpoints.kt",
            """
            package example.app

            import io.github.bbasinsk.http.*

            object PingEndpoints : HttpEndpointGroup("Ping") {
                val ping = http { get { Root / "ping" } }
            }
            """.trimIndent(),
        )

        val result = GradleRunner.create()
            .withProjectDir(root.toFile())
            .withArguments(":app:generateOpenApi", "--no-configuration-cache", "--stacktrace")
            .withPluginClasspath()
            .build()

        assertFalse(
            result.output.contains("Failed to load class example.feature.RuntimeLinked"),
            result.output,
        )
        val spec = root.resolve("app/build/generated/openapi/openapi.json")
        assertTrue(spec.toFile().isFile)
        assertTrue(spec.readText().contains("\"/ping\""))
    }

    @Test
    fun `build generates inside a custom build directory without overwriting the committed spec`() = withTempProject { root ->
        root.write("src/main/resources/openapi.json", "committed spec")
        root.writePluginProject(
            """
            layout.buildDirectory.set(layout.projectDirectory.dir("generated-build"))
            openApi {
                outputFile.set(layout.buildDirectory.file("api/openapi.json"))
            }
            """.trimIndent(),
        )

        GradleRunner.create()
            .withProjectDir(root.toFile())
            .withArguments("build", "--no-configuration-cache", "--stacktrace")
            .withPluginClasspath()
            .build()

        assertEquals("committed spec", root.resolve("src/main/resources/openapi.json").readText())
        assertTrue(root.resolve("generated-build/api/openapi.json").readText().contains("\"Fixture API\""))
    }

    @Test
    fun `build rejects output paths outside the normalized build directory`() {
        listOf(
            """layout.projectDirectory.file("src/main/resources/openapi.json")""",
            """layout.buildDirectory.file("../src/main/resources/openapi.json")""",
            """layout.projectDirectory.file("build-sibling/openapi.json")""",
        ).forEach { output ->
            withTempProject { root ->
                root.write("src/main/resources/openapi.json", "committed spec")
                root.writePluginProject("openApi { outputFile.set($output) }")

                val result = GradleRunner.create()
                    .withProjectDir(root.toFile())
                    .withArguments("build", "--no-configuration-cache", "--stacktrace")
                    .withPluginClasspath()
                    .buildAndFail()

                assertTrue(result.output.contains("generateOpenApi must write inside"), result.output)
                assertEquals("committed spec", root.resolve("src/main/resources/openapi.json").readText())
            }
        }
    }

    @Test
    fun `a late task override cannot redirect generation into the committed spec`() = withTempProject { root ->
        root.write("src/main/resources/openapi.json", "committed spec")
        root.writePluginProject(
            """
            gradle.projectsEvaluated {
                tasks.named<io.github.bbasinsk.http.gradle.GenerateOpenApiTask>("generateOpenApi") {
                    outputFile.set(layout.projectDirectory.file("src/main/resources/openapi.json"))
                }
            }
            """.trimIndent(),
        )

        val result = GradleRunner.create()
            .withProjectDir(root.toFile())
            .withArguments("generateOpenApi", "--no-configuration-cache", "--stacktrace")
            .withPluginClasspath()
            .buildAndFail()

        assertTrue(result.output.contains("is final and cannot be changed"), result.output)
        assertEquals("committed spec", root.resolve("src/main/resources/openapi.json").readText())
    }

    private fun Path.writePluginProject(configuration: String) {
        val kotlinVersion = requireNotNull(System.getProperty("kotlinVersion"))
        write(
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
            rootProject.name = "openapi-output"
            """.trimIndent(),
        )
        write(
            "build.gradle.kts",
            """
            plugins {
                kotlin("jvm") version "$kotlinVersion"
                id("io.github.bbasinsk.http-openapi")
            }
            openApi {
                info {
                    title = "Fixture API"
                    version = "1.0.0"
                }
            }
            $configuration
            """.trimIndent(),
        )
    }

    private fun withTempProject(block: (Path) -> Unit) {
        val root = createTempDirectory("openapi-runtime-classpath")
        try {
            block(root)
        } finally {
            root.toFile().deleteRecursively()
        }
    }

    private fun Path.write(relativePath: String, content: String) {
        resolve(relativePath).apply {
            parent.createDirectories()
            writeText(content)
        }
    }
}
