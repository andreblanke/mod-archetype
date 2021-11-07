import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0-RC2"

    id("com.github.johnrengelman.shadow") version "7.1.0"
}

subprojects {
    tasks {
        withType<KotlinCompile>().all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

repositories {
    mavenCentral()
}
