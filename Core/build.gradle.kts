import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "airacle.air.core"
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
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // big math
    implementation("ch.obermuhlner:big-math:2.3.0")

    implementation(project(":Util"))
}
