plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":schema-json"))
                implementation("com.github.erosb:everit-json-schema:1.14.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)

                implementation(project(":schema-json-kotlinx"))
            }
        }
    }
}
