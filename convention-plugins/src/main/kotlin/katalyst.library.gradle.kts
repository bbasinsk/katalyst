plugins {
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvmToolchain(17)
}

// Disable browser tests — tests run via nodejs() instead
tasks.matching { it.name == "jsBrowserTest" }.configureEach {
    enabled = false
}
