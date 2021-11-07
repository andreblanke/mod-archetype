package dev.andreblanke.mcmods.modid

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

abstract class Mod protected constructor() {

    companion object {
        const val ID = "modid"

        val logger: Logger = LogManager.getLogger()
    }
}