import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("katalyst.library")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":websocket"))
                api(project(":http-ktor-3"))
                api(project(":schema-json-kotlinx"))
                api(project(":schema-avro"))
                api(libs.ktor3.server.websockets)
                implementation(project(":validation"))
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

    coordinates(group.toString(), "websocket-ktor-3", version.toString())

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
