package ${package};

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

import net.minecraft.client.gui.screens.Screen;

public abstract class ${modName} {

    public static final String ID = "${modId}";

    protected ${modName}() {
        if (isClothConfigLoaded())
            AutoConfig.register(${modName}ConfigData.class, JanksonConfigSerializer::new);
    }

    public abstract boolean isClothConfigLoaded();

    public abstract Screen getConfigScreen();
}
