import dev.andreblanke.mcmods.modid.build.Mod

plugins {
    kotlin("jvm")
    `java-library`

    id("fabric-loom") version "0.9-SNAPSHOT"
}

sourceSets {
    main {
        java.srcDir("../common-fabric/src/main/java")
        resources.srcDir("../common-fabric/src/main/resources")
    }
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDir("../common-fabric/src/main/kotlin")
    }
}

repositories {
    maven {
        name = "ParchmentMC"
        url  = uri("https://maven.parchmentmc.org/")
    }
    maven {
        name = "Jitpack"
        url  = uri("https://jitpack.io/")
    }
    maven {
        name = "TerraformersMC"
        url  = uri("https://maven.terraformersmc.com/")
    }
    maven {
        name = "shedaniel"
        url  = uri("https://maven.shedaniel.me/")
    }
    mavenCentral()
}

dependencies {
    minecraft(
        group   = "com.mojang",
        name    = "minecraft",
        version = Mod.Dependencies.Minecraft.version)

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.17.1:2021.10.17@zip")
    })

    modImplementation(
        group   = "net.fabricmc",
        name    = "fabric-loader",
        version = Mod.Dependencies.Fabric.Loader.version)
    modImplementation(
        group   = "net.fabricmc",
        name    = "fabric-language-kotlin",
        version = Mod.Dependencies.Fabric.Language.Kotlin.version)
    modImplementation(
        group   = "net.fabricmc.fabric-api",
        name    = "fabric-api",
        version = Mod.Dependencies.Fabric.Api.version)

    modImplementation(
        group   = "com.github.Chocohead",
        name    = "Fabric-ASM",
        version = Mod.Dependencies.Fabric.Asm.version)

    modImplementation(
        group   = "com.terraformersmc",
        name    = "modmenu",
        version = Mod.Dependencies.ModMenu.version)
    modApi(
        group   = "me.shedaniel.cloth",
        name    = "cloth-config-fabric",
        version = Mod.Dependencies.ClothConfigApi.version
    ) {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

tasks {
    processResources {
        // Always forces the processResource task to run. TODO: Check if this is really necessary.
        outputs.upToDateWhen { false }

        filesMatching("fabric.mod.json") {
            expand("Mod" to Mod)
        }
    }
}
