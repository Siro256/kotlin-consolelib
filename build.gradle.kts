import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.dokka") version "1.4.10.2"
    `maven-publish`
    signing
}

group = "dev.siro256.kotlin.consolelib"
version = "1.0.0-SNAPSHOT"
description = "A console library for Kotlin(JVM language)"
val projectLocation = "Sirrrrrro/kotlin-consolelib"

repositories {
    maven { url = uri("https://maven.siro256.dev/repository/maven-public/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0-M1")
    implementation("dev.siro256:kotlin-eventlib:1.0.0-SNAPSHOT")
    dokkaJavadocPlugin("org.jetbrains.dokka:javadoc-plugin:1.4.10.2")
    dokkaHtmlPlugin("org.jetbrains.dokka:dokka-base:1.4.10.2")
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
    }

    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokkaHtml"))
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
                    "https://maven.siro256.dev/repository/maven-releases/" else
                    "https://maven.siro256.dev/repository/maven-snapshots/"
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
