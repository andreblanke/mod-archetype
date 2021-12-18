package ${package}.integration

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

import ${package}.${modName}Fabric

class ModMenuIntegration : ModMenuApi {

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        if (!${modName}Fabric.isClothConfigLoaded())
            return super.getModConfigScreenFactory()
        return ConfigScreenFactory { ${modName}Fabric.getConfigScreen(it) }
    }
}
