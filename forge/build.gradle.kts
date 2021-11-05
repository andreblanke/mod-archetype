import net.minecraftforge.gradle.common.util.RunConfig
import net.minecraftforge.gradle.userdev.UserDevExtension
import net.minecraftforge.gradle.userdev.DependencyManagementExtension

import dev.andreblanke.mcmods.modid.build.Mod

buildscript {
    dependencies {
        classpath(group = "net.minecraftforge.gradle", name = "ForgeGradle", version = "5.1.+")
        classpath(group = "org.spongepowered",         name = "mixingradle", version = "0.7-SNAPSHOT")
    }
}
apply(plugin = "net.minecraftforge.gradle")
apply(plugin = "org.spongepowered.mixin")

// Required for genVSCodeRuns task.
apply(plugin = "eclipse")

plugins {
    `java-library`
}

sourceSets {
    main {
        java.srcDir("../common-forge/src/main/java")
        resources.srcDir("../common-forge/src/main/resources")
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

dependencies {
    "minecraft"(
        group   = "net.minecraftforge",
        name    = "forge",
        version = Mod.Dependencies.Forge.version)

    val fg = project.the<DependencyManagementExtension>()
    api(fg.deobf("me.shedaniel.cloth:cloth-config-forge:${Mod.Dependencies.ClothConfigApi.version}"))
}

tasks {
    processResources {
        filesMatching("mods.toml") {
            // expand(Mod.properties)
        }
    }
}
