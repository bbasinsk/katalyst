plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.ktor.server.core)

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
                implementation(libs.ktor.server.cio)
            }
        }
    }
}
