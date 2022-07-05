plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0" // ktlint
}

version = "0.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}
