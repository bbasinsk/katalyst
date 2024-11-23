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
include(":schema-avro")
include(":schema-json")
include(":schema-json-kotlinx")
include(":schema-json-schema")
include(":schema-kotlinx-datetime")

include(":http")
include(":http-ktor-2")
include(":http-openapi")

include(":tuple")
include(":validation")
