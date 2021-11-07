package dev.andreblanke.mcmods.modid

import org.apache.logging.log4j.kotlin.Logging

abstract class Mod protected constructor() {

    companion object : Logging {
        const val ID = "modid"
    }
}