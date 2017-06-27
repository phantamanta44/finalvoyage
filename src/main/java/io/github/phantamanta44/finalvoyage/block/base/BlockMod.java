package io.github.phantamanta44.finalvoyage.block.base;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockMod extends Block {

    public BlockMod(Material material) {
        super(material);
        setCreativeTab(FVMod.CREATIVE_TAB);
    }

    protected ItemBlock registerBlock(String name) {
        setUnlocalizedName(name);
        if (!Block.REGISTRY.containsKey(new ResourceLocation(FVConst.MOD_ID, name))) {
            setRegistryName(name);
            GameRegistry.register(this);
        }
        ItemBlock item;
        if ((item = (ItemBlock)Item.getItemFromBlock(this)) == null) {
            item = generateItemBlock();
            item.setRegistryName(name);
            GameRegistry.register(item);
        }
        return item;
    }

    @Override
    public Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(name);
    }

    protected ItemBlock generateItemBlock() {
        return new ItemBlock(this);
    }

}
