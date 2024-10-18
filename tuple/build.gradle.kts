plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
