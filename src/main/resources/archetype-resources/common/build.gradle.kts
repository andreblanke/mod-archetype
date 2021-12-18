import ${package}.build.Mod

plugins {
#if ($language == "kotlin")
    kotlin("jvm")

#end
    // https://github.com/SpongePowered/VanillaGradle
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

minecraft {
    version(Mod.Dependencies.Minecraft.version)
}

repositories {
    maven {
        name = "shedaniel"
        url  = uri("https://maven.shedaniel.me/")
    }
}

dependencies {
    compileOnly(
        group   = "me.shedaniel.cloth",
        name    = "cloth-config",
        version = Mod.Dependencies.ClothConfigApi.version)
}
