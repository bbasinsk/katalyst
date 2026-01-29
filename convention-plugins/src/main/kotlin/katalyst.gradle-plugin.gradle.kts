plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.vanniktech.maven.publish")
}

kotlin {
    jvmToolchain(17)
}
