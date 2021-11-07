package dev.andreblanke.mcmods.modid

import net.minecraftforge.fml.common.Mod

@Mod(dev.andreblanke.mcmods.modid.Mod.ID)
object ModForge : dev.andreblanke.mcmods.modid.Mod() {

    init {
        logger.info("Hello, world!")
    }
}
