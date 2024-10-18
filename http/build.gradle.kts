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
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
