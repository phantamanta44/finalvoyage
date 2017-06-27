package io.github.phantamanta44.finalvoyage.item.base;

import io.github.phantamanta44.finalvoyage.constant.FVConst;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemBlockMod extends ItemBlock {

    public ItemBlockMod(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        return super.getUnlocalizedNameInefficiently(stack).replaceAll("tile\\.", "tile." + FVConst.MOD_PREF);
    }

}
