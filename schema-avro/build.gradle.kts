import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.kotlin.dsl.libs

plugins {
    id("module.publication")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":schema"))
                implementation(project(":validation"))
                implementation(libs.avro)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)

                implementation(project(":schema-json-kotlinx")) // for verifying avro schemas
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "schema-avro", version.toString())

    pom {
        name = "Katalyst"
        description = "Accelerating development with powerful Kotlin libraries"
        url = "https://github.com/benbasinski/katalyst"
        inceptionYear = "2024"

        developers {
            developer {
                id = "bbasinsk"
                name = "Ben Basinski"
            }
        }

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        scm {
            url = "https://github.com/benbasinski/katalyst"
            connection = "scm:git:git://github.com/benbasinski/katalyst.git"
            developerConnection = "scm:git:ssh://github.com/benbasinski/katalyst.git"
        }
    }
}
