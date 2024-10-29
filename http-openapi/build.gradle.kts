import org.gradle.kotlin.dsl.jvm

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
                implementation(project(":schema-json-kotlinx"))
                api(project(":http"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
