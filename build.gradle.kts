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

    // basic utils
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:31.0.1-jre")

    // to parse command line
    implementation("info.picocli:picocli:4.6.1")
    kapt("info.picocli:picocli-codegen:4.6.1")
}

application {
    mainClass.set("MainKt")
}

// picocli annotation
kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}