package io.github.phantamanta44.finalvoyage.world;

import com.google.common.base.Predicate;
import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.BlockNetherOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Arrays;
import java.util.Random;

public class NetherOreGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == -1) {
            Generator gen = new Generator(random, world, chunkX, chunkZ);
            for (int i = 0; i < 15; i++) {
                gen.generate(24, 80, 6, BlockNetherOre.Type.COAL);
                gen.generate(0, 64, 8, BlockNetherOre.Type.SILVER, BlockNetherOre.Type.LEAD);
                if (i < 12) {
                    gen.generate(0, 72, 6, BlockNetherOre.Type.ALUMINIUM);
                    gen.generate(0, 72, 6, BlockNetherOre.Type.TIN);
                    gen.generate(0, 64, 6, BlockNetherOre.Type.COPPER);

                    gen.generate(64, 128, 6, BlockNetherOre.Type.REDSTONE);

                    gen.generate(0, 128, 3, BlockNetherOre.Type.PERIDOT);
                    gen.generate(0, 128, 3, BlockNetherOre.Type.RUBY);
                    gen.generate(0, 128, 3, BlockNetherOre.Type.SAPPHIRE);

                    gen.generate(0, 72, 1, BlockNetherOre.Type.COBALT, BlockNetherOre.Type.ARDITE);
                }
                if (i < 10) {
                    gen.generate(24, 72, 6, BlockNetherOre.Type.OSMIUM);
                    gen.generate(72, 128, 5, BlockNetherOre.Type.MITHRIL);
                }
                if (i < 8) {
                    gen.generate(0, 128, 2, BlockNetherOre.Type.EMERALD);
                    gen.generate(56, 128, 4, BlockNetherOre.Type.LAPIS);

                    gen.generate(36, 96, 8, BlockNetherOre.Type.IRON);
                    gen.generate(0, 36, 7, BlockNetherOre.Type.IRON, BlockNetherOre.Type.NICKEL);
                }
                if (i < 4) {
                    gen.generate(0, 36, 3, BlockNetherOre.Type.DIAMOND);

                    gen.generate(0, 56, 4, BlockNetherOre.Type.IRIDIUM);
                    gen.generate(0, 56, 6, BlockNetherOre.Type.IRIDIUM, BlockNetherOre.Type.OSMIUM);

                    gen.generate(0, 64, 6, BlockNetherOre.Type.GOLD);
                    gen.generate(0, 64, 8, BlockNetherOre.Type.GOLD, BlockNetherOre.Type.COPPER);

                    gen.generate(0, 36, 4, BlockNetherOre.Type.NICKEL);
                }
                if (i < 2) {
                    gen.generate(0, 48, 6, BlockNetherOre.Type.PLATINUM, BlockNetherOre.Type.NICKEL, BlockNetherOre.Type.IRON);
                    gen.generate(0, 48, 4, BlockNetherOre.Type.PLATINUM, BlockNetherOre.Type.NICKEL);
                    gen.generate(0, 48, 2, BlockNetherOre.Type.PLATINUM);
                }
            }
            gen.generate(8, 120, 1, BlockNetherOre.Type.DRACONIUM);
            FVMod.LOGGER.debug(String.format("Generated ores with success rate of %.2f%%", gen.getSuccessRate() * 100));
        }
    }

    private static class Generator {

        private final Random random;
        private final World world;
        private final int xOff;
        private final int zOff;
        private int successful, total;

        public Generator(Random random, World world, int chunkX, int chunkZ) {
            this.random = random;
            this.world = world;
            this.xOff = chunkX * 16;
            this.zOff = chunkZ * 16;
            this.total = this.successful = 0;
        }

        public void generate(int yMin, int yMax, int count, BlockNetherOre.Type... types) {
            IBlockState[] states = Arrays.stream(types).map(BlockNetherOre.Type::getState).toArray(IBlockState[]::new);
            BlockPos pos = new BlockPos(random.nextInt(16) + xOff, yMin + random.nextInt(yMax - yMin), random.nextInt(16) + zOff);
            Predicate<IBlockState> predicate = s ->
                    s.getBlock() == Blocks.NETHERRACK || s.getBlock().getRegistryName().getResourcePath().equals("nether_tainted_soil");
            if (count >= 4)
                generateVein(states, pos, predicate, count);
            else
                generateCluster(states, pos, predicate, count);
            total += count;
        }

        private void generateVein(IBlockState[] states, BlockPos pos, Predicate<IBlockState> predicate, int count) {
            float f = random.nextFloat() * (float)Math.PI;
            double d0 = (double)((float)(pos.getX() + 8) + MathHelper.sin(f) * (float)count / 8.0F);
            double d1 = (double)((float)(pos.getX() + 8) - MathHelper.sin(f) * (float)count / 8.0F);
            double d2 = (double)((float)(pos.getZ() + 8) + MathHelper.cos(f) * (float)count / 8.0F);
            double d3 = (double)((float)(pos.getZ() + 8) - MathHelper.cos(f) * (float)count / 8.0F);
            double d4 = (double)(pos.getY() + random.nextInt(3) - 2);
            double d5 = (double)(pos.getY() + random.nextInt(3) - 2);

            for (int i = 0; i < count; ++i) {
                float f1 = (float)i / (float)count;
                double d6 = d0 + (d1 - d0) * (double)f1;
                double d7 = d4 + (d5 - d4) * (double)f1;
                double d8 = d2 + (d3 - d2) * (double)f1;
                double d9 = random.nextDouble() * (double)count / 16.0D;
                double d10 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
                double d11 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
                int j = MathHelper.floor_double(d6 - d10 / 2.0D);
                int k = MathHelper.floor_double(d7 - d11 / 2.0D);
                int l = MathHelper.floor_double(d8 - d10 / 2.0D);
                int i1 = MathHelper.floor_double(d6 + d10 / 2.0D);
                int j1 = MathHelper.floor_double(d7 + d11 / 2.0D);
                int k1 = MathHelper.floor_double(d8 + d10 / 2.0D);

                for (int l1 = j; l1 <= i1; ++l1) {
                    double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);

                    if (d12 * d12 < 1.0D) {
                        for (int i2 = k; i2 <= j1; ++i2) {
                            double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);

                            if (d12 * d12 + d13 * d13 < 1.0D) {
                                for (int j2 = l; j2 <= k1; ++j2) {
                                    double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);

                                    if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                                        BlockPos blockpos = new BlockPos(l1, i2, j2);

                                        IBlockState state = world.getBlockState(blockpos);
                                        if (state.getBlock().isReplaceableOreGen(state, world, blockpos, predicate)) {
                                            if (world.setBlockState(blockpos, states[random.nextInt(states.length)], 2))
                                                successful++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private void generateCluster(IBlockState[] states, BlockPos pos, Predicate<IBlockState> predicate, int count) {
            for (int i = 0; i < count; i++) {
                IBlockState state = world.getBlockState(pos);
                if (state.getBlock().isReplaceableOreGen(state, world, pos, predicate)) {
                    if (world.setBlockState(pos, states[random.nextInt(states.length)], 2))
                        successful++;
                }
                int axis = 1 << random.nextInt(3);
                pos = pos.add((axis & 0b100) >> 2, (axis & 0b010) >> 1, axis & 0b001);
            }
        }

        public float getSuccessRate() {
            return (float)successful / (float)total;
        }

    }

}
