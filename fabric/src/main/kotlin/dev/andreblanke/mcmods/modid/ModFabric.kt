package dev.andreblanke.mcmods.modid

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader

object ModFabric : Mod(), ModInitializer {

    override fun onInitialize() {
    }

    override fun isClothConfigLoaded(): Boolean =
        FabricLoader.getInstance().isModLoaded("cloth-config2")
}
