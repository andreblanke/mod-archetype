package dev.andreblanke.mcmods.modid

import me.shedaniel.autoconfig.AutoConfig

import net.minecraft.client.gui.screens.Screen

import net.minecraftforge.client.ConfigGuiHandler.ConfigGuiFactory
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod

@Mod(${modName}.ID)
object ${modName}Forge : ${modName}() {

    init {
        if (isClothConfigLoaded()) {
            ModLoadingContext
                .get()
                .registerExtensionPoint(ConfigGuiFactory::class.java) {
                    ConfigGuiFactory { _, parent -> getConfigScreen(parent) }
                }
        }
    }

    override fun isClothConfigLoaded(): Boolean =
        ModList.get().isLoaded("cloth_config")

    override fun getConfigScreen(parent: Screen): Screen =
        AutoConfig.getConfigScreen(ModConfigData::class.java, parent).get()
}
