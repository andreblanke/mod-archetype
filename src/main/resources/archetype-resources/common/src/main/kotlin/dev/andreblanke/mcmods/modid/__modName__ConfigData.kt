package dev.andreblanke.mcmods.modid

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = Mod.ID)
class ${modName}ConfigData : ConfigData {

    private var enabled: Boolean = true
}
