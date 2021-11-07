# Mod Template

This repository contains a template for a Minecraft mod targeting both the Fabric and Forge mod loaders, relying only
on [ForgeGradle](https://github.com/MinecraftForge/ForgeGradle) and
[Fabric Loom](https://github.com/FabricMC/fabric-loom) for its build system, as opposed to mods developed using
[Architectury](https://github.com/architectury).

The template is already configured to support [Mixins](https://github.com/SpongePowered/Mixin),
[Cloth Config](https://github.com/shedaniel/cloth-config), and [Mod Menu](https://github.com/TerraformersMC/ModMenu).

## Table of contents

- [Project structure](#project-structure)
- [About the template](#about-the-template)
  - [Tweaking](#tweaking)
    - [Removal of Kotlin](#removal-of-kotlin)
- [Troubleshooting](#troubleshooting)
  - [Fabric IntelliJ IDEA run/debug configurations not generating](#fabric-intellij-idea-rundebug-configurations-not-generating)
  - [Could not initialize class org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel](#could-not-initialize-class-orgjetbrainskotlincomintellijpomjavalanguagelevel)
- [To-do](#to-do)

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

## About the template

### Tweaking

#### Removal of Kotlin

While the template is meant to be used primarily with Kotlin it can be changed to support only  Java relatively easy if
more minimal build scripts are preferred. The following steps would need to be taken:

1. Convert the Kotlin stub classes to Java and move them into the `java` source root
2. Edit `build.gradle.kts` files
    - Remove Kotlin Gradle plugin application at the top of each file
    - Root build script:
        - Remove `KotlinCompile` task configuration inside the `subprojects` block
    - Submodule build scripts:
        - Remove additional Kotlin source sets
3. Fabric-specific:
    - Remove `fabric-language-kotlin` dependency from `build.gradle.kts` and `fabric.mod.json`
4. Forge-specific:
    - Remove KotlinForForge dependency along with its repository from `build.gradle.kts`
    - Tweak `mods.toml` to restore original `javafml` `modLoader` and `loaderVersion` settings

## Troubleshooting

### IntelliJ IDEA shows problems regarding `fabric.mod.json` or `mods.toml`

The problems are caused by checks stemming from the Minecraft Development or the Toml plugin because both are not aware
that the two files are merely templates.

In the case of `fabric.mod.json` the IDE should only consider the violation of the `id` pattern a problem which can
easily be suppressed by switching from "Highlight: All Problems" to "Highlight: Syntax".

`mods.toml` is more of a challenge because problems remain even if highlighting is changed to "Highlight: None".
To get rid of the problems entirely one would either have to disable the Toml plugin or, if that is not an option,
rename `mods.toml` to something else and tweak the `processResources` to output the processed file as `mods.toml`.

### Fabric IntelliJ IDEA run/debug configurations not generating

No fixes for this are known at the time of writing, though several people claim to have similar issues on the Fabric
Discord. As a temporary workaround the two run configurations `fabric_runClient.xml` and `fabric_runServer.xml` have
been committed to source control.

### Could not initialize class org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel

```text
java.lang.NoClassDefFoundError: Could not initialize class org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel
	at org.jetbrains.kotlin.com.intellij.core.CoreLanguageLevelProjectExtension.<init>(CoreLanguageLevelProjectExtension.java:26)
	at org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment.<init>(JavaCoreProjectEnvironment.java:42)
	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreProjectEnvironment.<init>(KotlinCoreProjectEnvironment.kt:26)
	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$ProjectEnvironment.<init>(KotlinCoreEnvironment.kt:121)
	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.createForProduction(KotlinCoreEnvironment.kt:425)
	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.createCoreEnvironment(K2JVMCompiler.kt:226)
	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:152)
	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:52)
	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:88)
	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:44)
	at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:98)
	at org.jetbrains.kotlin.daemon.CompileServiceImpl.compile(CompileServiceImpl.kt:1575) <17 internal lines>
```

You likely started the Gradle daemon with a currently unsupported JDK pointed to by your `JAVA_HOME` environment
variable. In my case this occurred because I was using JDK 17. Changing `JAVA_HOME` to a path containing a JDK
installation of version 16 and killing all active Gradle daemons resolved the issue.

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
