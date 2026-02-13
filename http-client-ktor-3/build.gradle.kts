plugins {
    id("katalyst.library")
    id("katalyst.android-library")
}

kotlin {
    jvm()
    androidLibrary {
        namespace = "io.github.bbasinsk.http.client.ktor3"
        compileSdk = 36
        minSdk = 21
    }
    macosArm64()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.ktor3.client.core)
                api(project(":http"))
                api(project(":schema-json-kotlinx"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor3.client.cio)
                implementation(libs.ktor3.server.test.host)
                implementation(libs.ktor3.server.cio)
                implementation(libs.ktor3.server.content.negotiation)
                implementation(libs.ktor3.serialization.kotlinx.json)
                implementation(libs.ktor3.client.mock)
                implementation(project(":http-server-ktor-3"))
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "http-client-ktor-3", version.toString())

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
