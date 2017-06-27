package io.github.phantamanta44.finalvoyage.item.block;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.BlockNetherDirt;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.base.ItemBlockModSubs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockNetherDirt extends ItemBlockModSubs {

    public ItemBlockNetherDirt(Block block) {
        super(block);
        for (BlockNetherDirt.Type type : BlockNetherDirt.Type.values())
            FVMod.PROXY.registerItemModel(this, type.ordinal(), "nether_" + type.getName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile." + LangConst.BLOCK_NETHER_DIRT_NAME +
                "." + BlockNetherDirt.Type.resolve(stack).getName();
    }

}
