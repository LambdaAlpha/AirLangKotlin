import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.5.31"
    application
}

group = "airacle.air"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // big math
    implementation("ch.obermuhlner:big-math:2.3.0")
}

application {
    mainClass.set("MainKt")
}
