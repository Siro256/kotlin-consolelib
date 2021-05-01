import java.net.URL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jetbrains.dokka") version "1.4.32"
    `maven-publish`
    signing
}

group = "dev.siro256"
version = "1.1.0-SNAPSHOT"
description = "A console library for Kotlin(JVM language)"
val projectLocation = "Sirrrrrro/kotlin-consolelib"

repositories {
    maven { url = uri("https://maven.siro256.dev/repository/maven-public/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0-M1")
    implementation("dev.siro256:kotlin-eventlib:1.0.0-SNAPSHOT")
    dokkaJavadocPlugin("org.jetbrains.dokka:javadoc-plugin:1.4.32")
    dokkaHtmlPlugin("org.jetbrains.dokka:dokka-base:1.4.32")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

tasks{
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<ProcessResources> {
        filteringCharset = "UTF-8"
        from(projectDir) { include("LICENSE") }
    }

    dokkaJavadoc {
        outputDirectory.set(buildDir.resolve("dokkaJavadoc"))
        dokkaSourceSets.configureEach {
            includeNonPublic.set(true)
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL(
                    if (version.toString().endsWith("SNAPSHOT", true))
                        "https://github.com/$projectLocation/blob/develop/src/main/kotlin/" else
                        "https://github.com/$projectLocation/blob/master/src/main/kotlin/"
                )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }

    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokkaHtml"))
        dokkaSourceSets.configureEach {
            includeNonPublic.set(true)
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL(
                    if (version.toString().endsWith("SNAPSHOT", true))
                        "https://github.com/$projectLocation/blob/develop/src/main/kotlin/" else
                        "https://github.com/$projectLocation/blob/master/src/main/kotlin/"
                )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn(dokkaJavadoc)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(buildDir.resolve("dokkaJavadoc"))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = rootProject.group.toString()
            artifactId = rootProject.name
            version = rootProject.version.toString()

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set(rootProject.name)
                description.set(rootProject.description)
                url.set("https://github.com/$projectLocation")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/mit-license.php")
                    }
                }

                developers {
                    developer {
                        id.set("Siro256")
                        name.set("Siro_256")
                        email.set("siro@siro256.dev")
                        url.set("https://github.com/Siro256")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/$projectLocation.git")
                    developerConnection.set("scm:git:ssh://github.com/$projectLocation.git")
                    url.set("https://github.com/$projectLocation")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri(
                if (version.toString().endsWith("SNAPSHOT", true))
                    "https://maven.siro256.dev/repository/maven-snapshots/" else
                    "https://maven.siro256.dev/repository/maven-releases/"
            )

            credentials {
                username = System.getProperty("username")
                password = System.getProperty("password")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
