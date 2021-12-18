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
- [Known caveats](#known-caveats)
- [To-do](#to-do)

## Creating a new project

```shell
# Clone the archetype.
git clone https://github.com/andreblanke/mod-template

# Install it to the local Maven repository using the included Maven wrapper mvnw.
mvnw install

# Interactively generate a project based upon the archetype.
mvnw archetype:generate                     \
  -DarchetypeGroupId=dev.andreblanke.mcmods \
  -DarchetypeArtifactId=mod-archetype
```

The `archetype:generate` goal will prompt you for some information, most of which should be self-explanatory. Depending
on the chosen `language` property (valid values are `java` and `kotlin` currently), a mod project with the specified
language will be generated.

## Known caveats

This section mainly serves as a reminder of what could be approved regarding the archetype but may also be interesting
for some potential users.

### Lacking validation of some required properties

TL;DR: one can specify a `modName` property containing spaces which will cause a project to be generated that does not
       compile without requiring (albeit few) manual edits.

Maven archetypes currently do not seem to support proper validation of required properties (and their post-processing,
e.g. editing of invalid values) such as `modName` per se, which prevents us from being able to handle mod names
containing spaces correctly (such as in the case of `modName = "Mod Name"`). `archetype:generate` will thus happily
generate projects with files  like `Mod Name.java` containing source code like
`public abstract class Mod Name { [...] }` which, of course, is neither valid Kotlin nor Java code.

While the `validationRegex` element has been added as part of Maven 3.0.0, [it seems to only support the interactive
generation of projects from this archetype](https://issues.apache.org/jira/browse/ARCHETYPE-532).

One could work around this by renaming files like `Mod Name.java` and similar ones to `ModName.java` using
`archetype-post-generate.groovy`, a script executed as part of the `archetype:generate` goal, which, however, would
still leave us with the need to handle substitutions within files, i.e. ones of the form `${modName}`, correctly.
That is hard because we cannot do more complex operations (such as throwing exceptions) using just the Apache Velocity
template engine language.

## To-do

- [ ] Add `org.apache.logging.log4j:logj4-api-kotlin:1.1.0` dependency. This is currently not possible because Forge
      is broken. While a [fork](https://github.com/OrionDevelopment/MinecraftForge/tree/fix/1.17.x%2Flibrary-loading)
      of Forge where the problems seem to have been resolved exists the changes have not been upstreamed yet.
      See the `1.17.X/log4-api-kotlin` branch of this repository for a start of the changes.
- [ ] Centralize dependency version declaration by making use of Gradle property expansion and only declaring versions
      inside of `Mod.Dependencies`. The challenge here is that only declarations of the form `object <name> { }` can be
      accessed without reflection but cannot directly be used with property expansion (because `<name>` is a class and
      not a property of `Mod`), while declarations such as `val <name> = object { }` can be used with property
      expansion but also only accessed via reflection because the type of `<name>` is `Any`.
