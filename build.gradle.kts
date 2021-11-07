import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0-RC2"
}

subprojects {
    tasks {
        withType(KotlinCompile::class.java).all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

repositories {
    mavenCentral()
}
