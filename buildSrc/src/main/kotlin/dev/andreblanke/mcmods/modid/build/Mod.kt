package dev.andreblanke.mcmods.modid.build

import java.util.*

interface Versioned {
    val version: String
}

// Unused properties may be used implicitly as part of Mod.properties.
@Suppress("unused")
object Mod : Versioned {

    const val ID   = "modid"
    const val NAME = "Mod Name"

    const val AUTHOR  = "andreblanke"
    const val LICENSE = "All rights reserved"

    const val DESCRIPTION = ""

    val deps = Dependencies

    val properties: Map<String, Any?>
        get() = TreeMap()

    override val version = "1.0.0"

    object Dependencies {

        object ClothConfigApi : Versioned {
            override val version = "5.0.38"
        }

        object ModMenu : Versioned {
            override val version = "2.0.14"
        }

        object Fabric {
            object Api : Versioned {
                override val version = "0.39.2+1.17"
            }

            object Asm : Versioned {
                override val version = "2.3"
            }

            object Loader : Versioned {
                override val version = "0.11.6"
            }

            object Language {
                object Kotlin : Versioned {
                    override val version = "1.6.5+kotlin.1.5.31"
                }
            }
        }

        object Forge : Versioned {
            private const val VERSION = "37.0.104"

            override val version: String
                get() = "${Minecraft.version}-$VERSION"

            object Language {
                object Kotlin : Versioned {
                    override val version = "2.0.1"
                }
            }
        }

        object Minecraft : Versioned {
            override val version = "1.17.1"
        }
    }
}
