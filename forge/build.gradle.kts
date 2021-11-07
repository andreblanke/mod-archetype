import net.minecraftforge.gradle.common.util.RunConfig
import net.minecraftforge.gradle.userdev.UserDevExtension
import net.minecraftforge.gradle.userdev.DependencyManagementExtension

import dev.andreblanke.mcmods.modid.build.Mod

plugins {
    kotlin("jvm")
}

buildscript {
    dependencies {
        classpath(group = "net.minecraftforge.gradle", name = "ForgeGradle", version = "5.1.+")
        classpath(group = "org.spongepowered",         name = "mixingradle", version = "0.7-SNAPSHOT")
    }
}
// Required for genVSCodeRuns task. Should be applied before org.spongepowered.mixin to enable the integration.
apply(plugin = "eclipse")
apply(plugin = "net.minecraftforge.gradle")
apply(plugin = "org.spongepowered.mixin")

sourceSets {
    main {
        java.srcDir("../common-forge/src/main/java")
        resources.srcDir("../common-forge/src/main/resources")
    }
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDir("../common-forge/src/main/kotlin")
    }
}

configure<UserDevExtension> {
    mappings("official", Mod.Dependencies.Minecraft.version)

    runs {
        val commonRunConfig: RunConfig.() -> Unit = {
            workingDirectory(file("run"))

            arg("-mixin.config=${Mod.ID}.mixins.json")

            property("forge.logging.markers",       "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods.create(Mod.ID) {
                source(sourceSets["main"])
            }
        }

        create("client", commonRunConfig)
        create("server", commonRunConfig)
        create("data") {
            commonRunConfig()
            args(
                "--mod",      Mod.ID,
                "--all",
                "--output",   file("src/generated/resources/"),
                "--existing", file("src/main/resources/")
            )
        }
    }
}

repositories {
    maven {
        name = "kotlinforforge"
        url  = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
    maven {
        name = "shedaniel"
        url  = uri("https://maven.shedaniel.me/")
    }
}

dependencies {
    "minecraft"(
        group   = "net.minecraftforge",
        name    = "forge",
        version = Mod.Dependencies.Forge.version)

    implementation(
        group   = "thedarkcolour",
        name    = "kotlinforforge",
        version = Mod.Dependencies.Forge.Language.Kotlin.version)

    val fg = project.the<DependencyManagementExtension>()
    api(fg.deobf("me.shedaniel.cloth:cloth-config-forge:${Mod.Dependencies.ClothConfigApi.version}"))
}

tasks {
    processResources {
        // Always forces the processResource task to run. TODO: Check if this is really necessary.
        outputs.upToDateWhen { false }

        filesMatching("META-INF/mods.toml") {
            expand("Mod" to Mod)
        }
        filesMatching("pack.mcmeta") {
            expand("Mod" to Mod)
        }
    }
}
