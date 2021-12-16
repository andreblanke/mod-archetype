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
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        withType<Jar> {
            archiveBaseName.set(rootProject.name)
            archiveAppendix.set(this@subprojects.name)
            archiveVersion.set(Mod.VERSION)
        }
    }
}

subprojects.filter { it.name != "common" }.forEach {
    it.afterEvaluate {
        tasks {
            processResources {
                from(project(":common").sourceSets["main"].resources)

                // Always forces the processResource task to run. TODO: Check if this is really necessary.
                outputs.upToDateWhen { false }

                filesMatching("fabric.mod.json") {
                    expand("Mod" to Mod)
                }
            }
        }
    }

    it.tasks {
        withType<JavaCompile> {
            /*
             * // source(project(":common").sourceSets["main"].java)
             *
             * The above snippet of code works but there seems to be no equivalent for the KotlinCompile task,
             * so prefer to keep the symmetry here until a better solution is found.
             */
            source("../common/src/main/java")
        }

        withType<KotlinCompile> {
            // source(project(":common").sourceSets["main"].kotlin)
            source("../common/src/main/kotlin")
        }
    }
}

repositories {
    mavenCentral()
}
