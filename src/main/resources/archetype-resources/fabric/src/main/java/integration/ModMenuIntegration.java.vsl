package ${package}.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!${modName}Fabric.isClothConfigLoaded())
            return super.getModConfigScreenFactory();
        return (parent) -> ${modName}Fabric.getConfigScreen(parent);
    }
}
