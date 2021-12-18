package ${package}

import me.shedaniel.autoconfig.AutoConfig

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader

import net.minecraft.client.gui.screens.Screen

object ${modName}Fabric : ${modName}(), ModInitializer {

    override fun onInitialize() {
    }

    override fun isClothConfigLoaded(): Boolean =
        FabricLoader.getInstance().isModLoaded("cloth-config2")

    override fun getConfigScreen(parent: Screen): Screen =
        AutoConfig.getConfigScreen(ModConfigData::class.java, parent).get()
}
