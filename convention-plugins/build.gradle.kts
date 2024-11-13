plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.vanniktech.maven.publish)
    implementation(libs.kotlin.multiplatform)
    implementation(libs.kotlin.serialization)
}
