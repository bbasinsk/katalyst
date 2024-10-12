pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Katalyst"

include(":schema")
include(":schema-json")
include(":schema-json-kotlinx")
include(":schema-kotlinx-datetime")

include(":validation")
