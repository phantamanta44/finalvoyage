package io.github.phantamanta44.finalvoyage.item;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.BlockNetherOre;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.integration.IMCHelper;
import io.github.phantamanta44.finalvoyage.item.base.ItemModSubs;
import io.github.phantamanta44.finalvoyage.util.OreDictHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemDust extends ItemModSubs {

    private static final BlockNetherOre.Type[] types = new BlockNetherOre.Type[] {
            BlockNetherOre.Type.COBALT, BlockNetherOre.Type.ARDITE
    };

    public static BlockNetherOre.Type getType(ItemStack stack) {
        return types[stack.getMetadata()];
    }

    public ItemDust() {
        super(LangConst.ITEM_DUST_NAME, types.length);
        for (int i = 0; i < types.length; i++) {
            FVMod.PROXY.registerItemModel(this, i, LangConst.ITEM_DUST_NAME);
            OreDictionary.registerOre("dust" + types[i].oreKey, new ItemStack(this, 1, i));
        }
    }

    public void registerRecipes() {
        for (int i = 0; i < types.length; i++) {
            IMCHelper.addEnrichmentRecipe(BlockNetherOre.generateStack(1, types[i]), new ItemStack(this, 2, i));
            IMCHelper.addEnrichmentRecipe(OreDictHelper.getStack("ingot" + types[i].oreKey, 1), new ItemStack(this, 1, i));
            GameRegistry.addSmelting(new ItemStack(this, 1, i), OreDictHelper.getStack("ingot" + types[i].oreKey, 1), 0);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return LangConst.get("item." + FVConst.MOD_PREF + LangConst.ITEM_DUST_NAME + ".name", getType(stack).getLocalizedName());
    }

}
