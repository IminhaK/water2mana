package net.bloop.water2mana;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = Water2Mana.MODID, name = Water2Mana.NAME, version = Water2Mana.VERSION, dependencies = "required-after:botania")
public class Water2Mana {
    public static final String MODID = "water2mana";
    public static final String NAME = "Water 2 Mana";
    public static final String VERSION = "1.0";

    @Mod.Instance(Water2Mana.MODID)
    public static Water2Mana mod;
}