import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("katalyst.library")
}

kotlin {
    jvm()
//    macosArm64()
//    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.ktor3.server.core)
                api(libs.ktor3.server.sse)
                api(libs.ktor3.server.websockets)

                api(project(":http"))
                api(project(":http-openapi"))
                api(project(":schema-json-kotlinx"))
                api(project(":schema-avro"))
                api(project(":tuple"))

                implementation(project(":validation"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:1.5.12")
                implementation(libs.ktor3.server.cio) // For example / main
                implementation(libs.ktor3.server.call.logging) // For example / main
                implementation(libs.ktor3.client.content.negotiation) // For example / main
                implementation(libs.ktor3.server.content.negotiation) // For example / main
                implementation(libs.ktor3.serialization.kotlinx.json) // For example / main
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor3.server.test.host)
                implementation(libs.ktor3.client.websockets)
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "http-ktor-3", version.toString())

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
