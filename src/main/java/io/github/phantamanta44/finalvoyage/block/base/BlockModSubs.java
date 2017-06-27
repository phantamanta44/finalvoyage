package io.github.phantamanta44.finalvoyage.block.base;

import io.github.phantamanta44.finalvoyage.item.base.ItemBlockModSubs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockModSubs extends BlockMod {

    protected final int subs;

    public BlockModSubs(Material material, int subs) {
        super(material);
        this.subs = subs;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < subs; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    protected ItemBlock generateItemBlock() {
        return new ItemBlockModSubs(this);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

}
