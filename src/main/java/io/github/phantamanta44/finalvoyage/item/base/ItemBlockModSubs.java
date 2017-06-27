package io.github.phantamanta44.finalvoyage.item.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockModSubs extends ItemBlockMod {

    public ItemBlockModSubs(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + stack.getItemDamage();
    }

}
