package dev.andreblanke.mcmods.modid;

public abstract class Mod {

    private static Mod instance;

    public static final String ID = "modid";

    protected Mod() {
        instance = this;
    }

    public static Mod getInstance() {
        return instance;
    }
}
