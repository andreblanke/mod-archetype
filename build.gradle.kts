import org.gradle.jvm.tasks.Jar

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import dev.andreblanke.mcmods.modid.build.Mod

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

        withType<Jar>().all {
            archiveBaseName.set(rootProject.name)
            archiveAppendix.set(this@subprojects.name)
            archiveVersion.set(Mod.VERSION)
        }
    }
}

repositories {
    mavenCentral()
}
