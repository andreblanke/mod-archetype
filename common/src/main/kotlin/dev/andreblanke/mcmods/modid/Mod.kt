package dev.andreblanke.mcmods.modid

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer

import net.minecraft.client.gui.screens.Screen

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

abstract class Mod protected constructor() {

    companion object {
        const val ID = "modid"

        val logger: Logger = LogManager.getLogger()
    }

    init {
        // isClothConfigLoaded should be safe to call even if the Mod subclass is not initialized yet.
        @Suppress("LeakingThis")
        if (isClothConfigLoaded())
            AutoConfig.register(ModConfigData::class.java, ::JanksonConfigSerializer)
    }

    abstract fun isClothConfigLoaded(): Boolean

    abstract fun getConfigScreen(parent: Screen): Screen
}
