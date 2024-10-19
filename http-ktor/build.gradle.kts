plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

val ktorVersion: String = "2.3.12"

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.ktor:ktor-server-core:$ktorVersion")

                api(project(":http"))
                api(project(":http-openapi"))
                api(project(":schema-json-kotlinx"))
                api(project(":tuple"))

                implementation(project(":validation"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)

                implementation("io.ktor:ktor-server-cio:$ktorVersion")
            }
        }
    }
}
