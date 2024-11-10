import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    jvm()
    macosArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.ktor.server.core)

                api(project(":http"))
                api(project(":http-openapi"))
                api(project(":schema-json-kotlinx"))
                api(project(":tuple"))

                implementation(project(":validation"))

                implementation(libs.ktor.server.cio) // For example / main
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "http-ktor", version.toString())

    pom {
        name = "Katalyst"
        description = "Accelerating development with powerful Kotlin libraries"
        url = "https://github.com/benbasinski/katalyst"
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
            url = "https://github.com/benbasinski/katalyst"
            connection = "scm:git:git://github.com/benbasinski/katalyst.git"
            developerConnection = "scm:git:ssh://github.com/benbasinski/katalyst.git"
        }
    }
}
