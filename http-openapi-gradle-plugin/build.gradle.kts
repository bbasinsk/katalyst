import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation(project(":http"))
    implementation(project(":http-openapi"))
    implementation(gradleApi())
    compileOnly(libs.kotlin.multiplatform)
}

gradlePlugin {
    plugins {
        create("openapi") {
            id = "io.github.bbasinsk.http-openapi"
            implementationClass = "io.github.bbasinsk.http.gradle.OpenApiPlugin"
            displayName = "Katalyst OpenAPI Generator"
            description = "Generate OpenAPI specs from Katalyst HTTP endpoints at build time"
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "http-openapi-gradle-plugin", version.toString())

    pom {
        name = "Katalyst OpenAPI Gradle Plugin"
        description = "Generate OpenAPI specs from Katalyst HTTP endpoints at build time"
        url = "https://github.com/bbasinsk/katalyst"
        inceptionYear = "2024"

        developers {
            developer {
                id = "bbasinsk"
                name = "Ben Basinski"
            }
        }

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        scm {
            url = "https://github.com/bbasinsk/katalyst"
            connection = "scm:git:git://github.com/bbasinsk/katalyst.git"
            developerConnection = "scm:git:ssh://github.com/bbasinsk/katalyst.git"
        }
    }
}
