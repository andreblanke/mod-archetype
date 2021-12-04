package dev.andreblanke.mcmods.modid

import net.minecraftforge.client.ConfigGuiHandler.ConfigGuiFactory
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod

@Mod(dev.andreblanke.mcmods.modid.Mod.ID)
object ModForge : dev.andreblanke.mcmods.modid.Mod() {

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
}
