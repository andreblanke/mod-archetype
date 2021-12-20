# Mod Archetype

This repository contains a Maven archetype that can generate a skeleton of a Minecraft mod targeting both the
Fabric and Forge mod loaders without the usage of a separate build system, relying only on
[Fabric Loom](https://github.com/FabricMC/fabric-loom), [ForgeGradle](https://github.com/MinecraftForge/ForgeGradle), 
and [VanillaGradle](https://github.com/SpongePowered/VanillaGradle) unlike mods developed against
[Architectury](https://github.com/architectury).

Maven is only used for the initial creation of the project, as Gradle currently does not offer an (official)
equivalent for Maven archetypes. The generated project skeleton is still based on Gradle.

The created project is already configured to support [Mixins](https://github.com/SpongePowered/Mixin),
[Cloth Config](https://github.com/shedaniel/cloth-config), and [Mod Menu](https://github.com/TerraformersMC/ModMenu).

Projects generated from this archetype make use of the fact that Mojang released official obfuscation mappings which
allows us to write mod loader-agnostic code that targets both Fabric and Forge in the `common` module.

## Remarks

The repository previously contained an actual template instead of a Maven archetype capable of generating such a
template. If you would like to such a template mod you can run the integration tests using `mvnw integration-test`,
which will create a Java-based and  a Kotlin-based project under `target/test-classes/projects/mod-template-java` and
`target/test-classes/projects/mod-template-kotlin` respectively.

To see the files used to generate these templates see
[src/main/resources/archetype-resources](src/main/resources/archetype-resources).

The repository is no longer marked as template repository because it is not sufficient anymore to simply press
"Use this template" on GitHub. See [Creating a new projects](#creating-a-new-projects) for the new workflow.

## Table of contents

- [Remarks](#remarks)
- [Creating a new project](#creating-a-new-project)
- [To-do](#to-do)

## Creating a new project

```shell
# Clone the archetype.
git clone https://github.com/andreblanke/mod-archetype

# Navigate into the downloaded repository.
cd mod-archetype

# Install the archetype to the local Maven repository and interactively generate a project based upon the archetype.
# The generated project will be placed in the "target" directory.
./mvnw install archetype:generate           \
  -DarchetypeGroupId=dev.andreblanke.mcmods \
  -DarchetypeArtifactId=mod-archetype       \
  -DoutputDirectory=target
```

The `archetype:generate` goal will prompt you for some information, most of which should be self-explanatory. Depending
on the chosen `language` property (valid values are `java` and `kotlin` currently), a mod project with the specified
language will be generated.

## To-do

- [ ] Replace [takari's Maven Wrapper](https://github.com/takari/maven-wrapper) with
      [the official Apache one](https://maven.apache.org/wrapper/) once this is possible.
- [ ] Add `org.apache.logging.log4j:logj4-api-kotlin:1.1.0` dependency. This is currently not possible because Forge
      is broken. While a [fork](https://github.com/OrionDevelopment/MinecraftForge/tree/fix/1.17.x%2Flibrary-loading)
      of Forge where the problems seem to have been resolved exists the changes have not been upstreamed yet.
      See the `1.17.X/log4-api-kotlin` branch of this repository for a start of the changes.
- [ ] Centralize dependency version declaration by making use of Gradle property expansion and only declaring versions
      inside of `Mod.Dependencies`. The challenge here is that only declarations of the form `object <name> { }` can be
      accessed without reflection but cannot directly be used with property expansion (because `<name>` is a class and
      not a property of `Mod`), while declarations such as `val <name> = object { }` can be used with property
      expansion but also only accessed via reflection because the type of `<name>` is `Any`.
