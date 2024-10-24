plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":schema"))
                implementation(project(":validation"))
                implementation("org.apache.avro:avro:1.11.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)

                implementation(project(":schema-json-kotlinx")) // for verifying avro schemas
            }
        }
    }
}
