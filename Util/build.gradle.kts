import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

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
}
