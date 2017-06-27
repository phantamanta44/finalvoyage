package io.github.phantamanta44.finalvoyage.constant;

import net.minecraft.util.text.translation.I18n;

public class LangConst {

    public static final String CREATIVE_TAB_NAME = FVConst.MOD_ID;

    public static final String BLOCK_NETHER_ORE_NAME = "netherOre";
    public static final String BLOCK_NETHER_DIRT_NAME = "netherDirt";
    public static final String BLOCK_INTERDICTION_MATRIX_NAME = "interdictionMatrix";

    public static final String ITEM_DRINK_NAME = "drink";
    public static final String ITEM_FOOD_NAME = "food";
    public static final String ITEM_WATER_BOTTLE_NAME = "waterBottle";
    public static final String ITEM_MEKANISM_ORE_NAME = "mekanismOre";
    public static final String ITEM_DUST_NAME = "dust";
    public static final String ITEM_MISC_NAME = "itemMisc";

    public static final String TT_KEY = FVConst.MOD_ID + ".tooltip.";
    public static final String TT_EMPTY = TT_KEY + "empty";
    public static final String TT_INTERDICTION = TT_KEY + "interdiction";

    public static final String ORE_KEY = FVConst.MOD_ID + ".ore.";

    public static final String MISC_KEY = FVConst.MOD_ID + ".misc.";
    public static final String MISC_SLURRY = MISC_KEY + "slurry";

    public static String get(String key, Object... args) {
        return String.format(I18n.translateToLocal(key), args);
    }

}
