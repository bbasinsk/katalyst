import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("module.publication")
}

kotlin {
    jvm()
    macosArm64()
    linuxX64()

    compilerOptions {
        optIn = listOf("kotlin.time.ExperimentalTime")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":schema"))
                // TODO: make sure this blends with other versions of kotlinx-serialization
                api(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "schema-kotlinx-datetime", version.toString())

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
