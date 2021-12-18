package ${package}

import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer

import net.minecraft.client.gui.screens.Screen

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

abstract class ${modName} protected constructor() {

    companion object {
        const val ID = "${modId}"

        val logger: Logger = LogManager.getLogger()
    }

    init {
        /*
         * isClothConfigLoaded should be safe to call even if the ${modName} subclass is not initialized yet because it
         * should not rely on any state.
         */
        @Suppress("LeakingThis")
        if (isClothConfigLoaded())
            AutoConfig.register(ModConfigData::class.java, ::JanksonConfigSerializer)
    }

    abstract fun isClothConfigLoaded(): Boolean

    abstract fun getConfigScreen(parent: Screen): Screen
}