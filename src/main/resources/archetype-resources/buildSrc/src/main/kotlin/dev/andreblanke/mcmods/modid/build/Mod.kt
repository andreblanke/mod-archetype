package dev.andreblanke.mcmods.modid.build

import java.util.*

interface Versioned {
    val version: String
}

// "Unused" properties may be used implicitly as part of the Gradle property expansion.
@Suppress("unused", "MemberVisibilityCanBePrivate")
object Mod : Versioned {

    const val ID   = "modid"
    const val NAME = "Mod Name"

    const val AUTHOR  = "andreblanke"
    const val LICENSE = "MIT"

    const val DESCRIPTION = "Description"

    val properties: Map<String, Any?>
        get() = TreeMap()

    const val VERSION = "1.0.0"
    override val version
        get() = VERSION

    val Contact = object {
        val HOMEPAGE = "https://github.com/andreblanke/mod-template"
        val ISSUES   = "https://github.com/andreblanke/mod-template/issues"
    }

    object Dependencies {

        object ClothConfigApi : Versioned {
            override val version = "6.0.45"
        }

        object ModMenu : Versioned {
            override val version = "3.0.0"
        }

        object Fabric {
            object Api : Versioned {
                private const val VERSION = "0.44.0"

                override val version: String
                    get() = "${VERSION}+${Minecraft.version}"
            }

            object Asm : Versioned {
                override val version = "2.3"
            }

            object Loader : Versioned {
                override val version = "0.12.8"
            }

            object Language {
                object Kotlin : Versioned {
                    override val version = "1.7.0+kotlin.1.6.0"
                }
            }
        }

        object Forge : Versioned {
            private const val VERSION = "38.0.14"

            override val version: String
                get() = "${Minecraft.version}-$VERSION"

            object Language {
                object Kotlin : Versioned {
                    override val version = "3.0.0"
                }
            }
        }

        object Minecraft : Versioned {
            override val version = "1.18"
        }
    }
}
