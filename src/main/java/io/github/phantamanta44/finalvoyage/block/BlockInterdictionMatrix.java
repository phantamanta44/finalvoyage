package io.github.phantamanta44.finalvoyage.block;

import io.github.phantamanta44.finalvoyage.block.base.BlockMod;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.block.ItemBlockInterdictionMatrix;
import io.github.phantamanta44.finalvoyage.tile.TileInterdictionMatrix;
import io.github.phantamanta44.finalvoyage.util.Capabilities;
import io.github.phantamanta44.finalvoyage.util.Properties;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInterdictionMatrix extends BlockMod implements ITileEntityProvider {

    public BlockInterdictionMatrix() {
        super(Material.IRON);
        registerBlock(LangConst.BLOCK_INTERDICTION_MATRIX_NAME);
        setHardness(4F);
        setResistance(8F);
    }

    @Override
    protected ItemBlock generateItemBlock() {
        return new ItemBlockInterdictionMatrix(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, Properties.ACTIVE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileInterdictionMatrix tile = (TileInterdictionMatrix)world.getTileEntity(pos);
        if (tile == null)
            return state;
        return state.withProperty(Properties.ACTIVE,
                tile.getCapability(Capabilities.TESLA_HOLDER, EnumFacing.DOWN).getStoredPower()
                        >= TileInterdictionMatrix.INTERDICTION_COST);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileInterdictionMatrix();
    }

}
