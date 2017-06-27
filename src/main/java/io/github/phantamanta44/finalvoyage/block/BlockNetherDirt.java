package io.github.phantamanta44.finalvoyage.block;

import io.github.phantamanta44.finalvoyage.block.base.BlockModSubs;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.block.ItemBlockNetherDirt;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNetherDirt extends BlockModSubs {

    public static final IProperty<Type> PROP_TYPE = PropertyEnum.create("type", Type.class);

    public BlockNetherDirt() {
        super(Material.GROUND, 2);
        registerBlock(LangConst.BLOCK_NETHER_DIRT_NAME);
        setHardness(0.6F);
        setResistance(1F);
        setSoundType(SoundType.GROUND);
    }

    @Override
    protected ItemBlock generateItemBlock() {
        return new ItemBlockNetherDirt(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PROP_TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(PROP_TYPE, Type.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PROP_TYPE).ordinal();
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }

    public enum Type implements IStringSerializable {

        DIRT, CLAY;

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public static Type resolve(ItemStack stack) {
            return values()[stack.getMetadata()];
        }

    }

}
