package io.github.phantamanta44.finalvoyage.block;

public class FVBlocks {

    public static BlockNetherOre[] netherOre;
    public static BlockNetherDirt netherDirt;
    public static BlockInterdictionMatrix interdictionMatrix;

    public static void init() {
        netherOre = new BlockNetherOre[(int)Math.ceil(BlockNetherOre.Type.values().length / 16F)];
        for (int i = 0; i < netherOre.length; i++)
            netherOre[i] = new BlockNetherOre();
        netherDirt = new BlockNetherDirt();
        interdictionMatrix = new BlockInterdictionMatrix();
    }

}
