plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
}

version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}
