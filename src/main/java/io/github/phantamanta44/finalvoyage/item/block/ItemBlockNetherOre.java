package io.github.phantamanta44.finalvoyage.item.block;

import io.github.phantamanta44.finalvoyage.block.BlockNetherOre;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.base.ItemBlockModSubs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockNetherOre extends ItemBlockModSubs {

    public ItemBlockNetherOre(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile." + LangConst.BLOCK_NETHER_ORE_NAME;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return String.format(super.getItemStackDisplayName(stack), BlockNetherOre.Type.resolve(stack).getLocalizedName());
    }

}
