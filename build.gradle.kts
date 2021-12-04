import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0-RC2"
}

subprojects {
    afterEvaluate {
        java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    tasks {
        withType<KotlinCompile>().all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

repositories {
    mavenCentral()
}
