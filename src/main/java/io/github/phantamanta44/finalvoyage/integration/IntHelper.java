package io.github.phantamanta44.finalvoyage.integration;

import io.github.phantamanta44.finalvoyage.block.BlockNetherOre;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class IntHelper {

    public static ItemStack itemStack(IntMod mod, String name, int count, int meta) {
        return new ItemStack(mod.getItem(name), count, meta);
    }

    public static FluidStack fluidStack(String name, int count) {
        return new FluidStack(FluidRegistry.getFluid(name), count);
    }

    public static GasStack gasStack(String name, int count) {
        return new GasStack(GasRegistry.getGas(name), count);
    }

    public static void registerOreGas(BlockNetherOre.Type type) {
        OreGas clean = (OreGas)GasRegistry.register(new FVOreGas(type, true)).setVisible(false);
        GasRegistry.register(new FVOreGas(type, false).setCleanGas(clean).setVisible(false));
    }

    private static class FVOreGas extends OreGas {

        private final BlockNetherOre.Type type;

        public FVOreGas(BlockNetherOre.Type type, boolean clean) {
            super(clean ? "clean" + type.getName() : type.getName(), type.getName());
            this.type = type;
        }

        @Override
        public String getOreName() {
            return LangConst.get("tile." + FVConst.MOD_PREF + LangConst.BLOCK_NETHER_ORE_NAME + ".name", type.getLocalizedName());
        }

        @Override
        public String getLocalizedName() {
            return LangConst.get(LangConst.MISC_SLURRY, type.getLocalizedName());
        }

    }

}
