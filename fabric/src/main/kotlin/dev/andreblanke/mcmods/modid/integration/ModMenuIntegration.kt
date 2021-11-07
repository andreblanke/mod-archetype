package dev.andreblanke.mcmods.modid.integration

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

import dev.andreblanke.mcmods.modid.ModFabric

class ModMenuIntegration : ModMenuApi {

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        if (!ModFabric.isClothConfigLoaded())
            return super.getModConfigScreenFactory()
        return ConfigScreenFactory { ModFabric.getConfigScreen(it) }
    }
}
