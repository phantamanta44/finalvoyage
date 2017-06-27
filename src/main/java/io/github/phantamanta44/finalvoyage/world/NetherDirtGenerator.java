package io.github.phantamanta44.finalvoyage.world;

import io.github.phantamanta44.finalvoyage.block.BlockNetherDirt;
import io.github.phantamanta44.finalvoyage.block.FVBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class NetherDirtGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == -1) {
            IBlockState dirtState = FVBlocks.netherDirt.getDefaultState()
                    .withProperty(BlockNetherDirt.PROP_TYPE, BlockNetherDirt.Type.DIRT);
            IBlockState clayState = FVBlocks.netherDirt.getDefaultState()
                    .withProperty(BlockNetherDirt.PROP_TYPE, BlockNetherDirt.Type.CLAY);
            BlockPos chunkRoot = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            for (int i = 0; i < 8; i++) {
                new WorldGenMinable(dirtState, 8 + random.nextInt(4), BlockMatcher.forBlock(Blocks.NETHERRACK))
                        .generate(world, random, chunkRoot.add(random.nextInt(16), random.nextInt(128), random.nextInt(16)));
                new WorldGenMinable(clayState, 8 + random.nextInt(6), BlockMatcher.forBlock(Blocks.NETHERRACK))
                        .generate(world, random, chunkRoot.add(random.nextInt(16), random.nextInt(128), random.nextInt(16)));
            }
        }
    }

}
