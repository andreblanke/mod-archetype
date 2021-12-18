package ${package};

import me.shedaniel.autoconfig.AutoConfig;

import net.minecraft.client.gui.screens.Screen;

import net.minecraftforge.client.ConfigGuiHandler.ConfigGuiFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(${modName}.ID)
public final class ${modName}Forge extends ${modName} {

    public ${modName}Forge() {
        if (isClothConfigLoaded()) {
            ModLoadingContext
                .get()
                .registerExtensionPoint(ConfigGuiFactory.class, (minecraft, parent) -> getConfigScreen(parent));
        }
    }

    @Override
    public boolean isClothConfigLoaded() {
        return ModList.get().isLoaded("cloth_config");
    }

    @Override
    public Screen getConfigScreen(final Screen parent) {
        return AutoConfig.getConfigScreen(${modName}ConfigData.java, parent).get();
    }
}
