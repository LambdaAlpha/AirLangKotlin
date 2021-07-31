import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "airacle.air"
version = "1.0-SNAPSHOT"

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
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:30.1.1-jre")
}

application {
    mainClass.set("MainKt")
}