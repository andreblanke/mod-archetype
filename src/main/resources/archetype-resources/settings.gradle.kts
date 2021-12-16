rootProject.name = "mod-template"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "Fabric"
            url  = uri("https://maven.fabricmc.net")
        }
        maven {
            name = "Minecraft Forge"
            url  = uri("https://maven.minecraftforge.net")
        }
        maven {
            name = "SpongePowered"
            url  = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
    }
}

include("common", "fabric", "forge")
