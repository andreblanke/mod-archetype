# Mod Template

This repository contains a template for a Minecraft mod targeting both the Fabric and Forge mod loaders, relying only
on [ForgeGradle](https://github.com/MinecraftForge/ForgeGradle) and
[Fabric Loom](https://github.com/FabricMC/fabric-loom) for its build system, as opposed to mods developed using
[Architectury](https://github.com/architectury).

## Table of contents

- [Project structure](#project-structure)
- [Using this template](#using-this-template)
- [Troubleshooting](#troubleshooting)
  - [Fabric IntelliJ IDEA run/debug configurations not generating](#fabric-intellij-idea-rundebug-configurations-not-generating)

## Project structure

```text
mod-template
 ├─ buildSrc/
 │   └─ src/main/kotlin/dev/andreblanke/mcmods/modid/build/
 │       │  # Common build-time properties used by the fabric/forge modules'
 │       │  # build.gradle.kts.
 │       └─ Mod.kt
 ├─ common/
 │   └─ src/main/
 │       │  # Package for mixins which must be written in Java.
 │       ├─ java/dev/andreblanke/mcmods/modid/mixin/
 │       └─ kotlin/dev/andreblanke/mcmods/modid/
 │           │  # Common main class inherited by ModFabric and ModForge.
 │           └─ Mod.java
 ├─ common-{fabric,forge}/
 │   │  # Symbolic link to ../common/src, required because IntelliJ IDEA does not support content
 │   │  # roots shared between two projects. See https://youtrack.jetbrains.com/issue/IDEA-210908.
 │   └─ src
 ├─ fabric/
 │   └─ src/main/kotlin/dev/andreblanke/mcmods/modid/
 │       │  # Entry point for Fabric.
 │       └─ ModFabric.java
 └─ forge/
     └─ src/main/kotlin/dev/andreblanke/mcmods/modid/
         │  # Entry point for Forge.
         └─ ModForge.java
```

Mods based upon this template make use of the fact that Mojang released official obfuscation mappings which, if we are
careful, allows us to write mod loader-agnostic code that targets both Fabric and Forge in the `common` module.

Due to limitations of IntelliJ IDEA it is currently not possible to share the `common` folder as a source root between
the modules `fabric` and `forge`. As a workaround we use symbolic links pointing from `common-fabric/src` and
`common-forge/src` to `common/src` which causes no major issues. Some minor issues exist though, such as

- IntelliJ IDEA not displaying [File Status Highlights](https://www.jetbrains.com/help/idea/file-status-highlights.html)
  correctly of files within the `common-fabric`/`common-forge` modules or
- changes to files inside the `common-fabric`/`common-forge` not being immediately visible to its counterpart
  `common-forge`/`common-fabric` module (forcing a refresh of the project view using "Reload from Disk" works though).

## Using this template

## Troubleshooting

### Fabric IntelliJ IDEA run/debug configurations not generating

No fixes for this are known at the time of writing, though several people claim to have similar
issues on the Fabric Discord. As a temporary workaround the two run configurations
`fabric_runClient.xml` and `fabric_runServer.xml` have been committed to source control.
