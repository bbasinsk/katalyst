import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.jreleaser.model.Active
import org.jreleaser.model.Http.Authorization.BEARER

plugins {
    `maven-publish`
    signing
    id("org.jreleaser")
}

publishing {

    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/bbasinsk/katalyst")
            credentials {
                username = System.getenv("MAVEN_GITHUB_USERNAME")
                password = System.getenv("MAVEN_GITHUB_PASSWORD")
            }
        }
    }

    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this@withType.name)
        })

        repositories {
            maven {
                name = "staging"
                url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
            }
        }

        // Provide artifacts information required by Maven Central
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
}

jreleaser {
    gitRootSearch = true
    signing {
        active = Active.ALWAYS
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    authorization = BEARER
                    username = System.getenv("JRELEASER_MAVENCENTRAL_SONATYPE_USERNAME")
                    password = System.getenv("JRELEASER_MAVENCENTRAL_SONATYPE_PASSWORD")
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}
