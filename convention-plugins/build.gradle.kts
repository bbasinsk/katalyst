plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.vanniktech.maven.publish)
    implementation(libs.kotlin.multiplatform)
    implementation(libs.kotlin.serialization)
}
