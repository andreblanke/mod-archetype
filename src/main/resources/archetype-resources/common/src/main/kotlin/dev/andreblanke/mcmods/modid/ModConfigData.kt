package dev.andreblanke.mcmods.modid

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = Mod.ID)
class ModConfigData : ConfigData {

    private var enabled: Boolean = true
}
