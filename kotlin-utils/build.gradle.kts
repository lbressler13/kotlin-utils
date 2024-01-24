plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" // ktlint
}

group = "xyz.lbres"
version = "1.3.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/lbressler13/kotlin-utils")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
