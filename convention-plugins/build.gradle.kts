plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.vanniktech.maven.publish)
    implementation(libs.kotlin.multiplatform)
    implementation(libs.kotlin.serialization)
}
