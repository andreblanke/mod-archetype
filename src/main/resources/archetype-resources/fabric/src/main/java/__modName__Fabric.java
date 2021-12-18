package ${package};

import me.shedaniel.autoconfig.AutoConfig;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.gui.screens.Screen;

public final class ${modName}Fabric extends ${modName} implements ModInitializer {

    @Override
    public void onInitialize() {
    }

    @Override
    public boolean isClothConfigLoaded() {
        return FabricLoader.getInstance().isModLoaded("cloth-config2");
    }

    @Override
    public Screen getConfigScreen(final Screen parent) {
        return AutoConfig.getConfigScreen(${modName}ConfigData.java, parent).get();
    }
}

