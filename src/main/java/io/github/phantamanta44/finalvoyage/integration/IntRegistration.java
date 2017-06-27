package io.github.phantamanta44.finalvoyage.integration;

import gnu.trove.map.hash.TObjectIntHashMap;
import io.github.phantamanta44.finalvoyage.FVMod;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;

public class IntRegistration {

    public static void init() {
        registerNetherGlassOreDict();
        rebalanceThermalExpansionPowerGen();
    }

    private static void registerNetherGlassOreDict() {
        OreDictionary.registerOre("blockGlass", IntHelper.itemStack(IntMod.NAT, "nether_glass", 1, 0));
        OreDictionary.registerOre("blockGlass", IntHelper.itemStack(IntMod.NAT, "nether_glass", 1, 1));
    }

    @SuppressWarnings("unchecked")
    private static void rebalanceThermalExpansionPowerGen() {
        try {
            Field fuelMapField = Class.forName("cofh.thermalexpansion.util.managers.dynamo.CompressionManager")
                    .getDeclaredField("fuelMap");
            fuelMapField.setAccessible(true);
            TObjectIntHashMap<Fluid> fuelMap = (TObjectIntHashMap<Fluid>)fuelMapField.get(null);
            modifyFuel(fuelMap, "coal", 100000);
            modifyFuel(fuelMap, "creosote", 54000);
            modifyFuel(fuelMap, "tree_oil", 400000);
            modifyFuel(fuelMap, "refined_oil", 900000);
            modifyFuel(fuelMap, "fuel", 960000);
        } catch (Exception e) {
            FVMod.LOGGER.warn("Compression fuel hack failed!", e);
        }
        try {
            Field fuelMapField = Class.forName("cofh.thermalexpansion.util.managers.dynamo.MagmaticManager")
                    .getDeclaredField("fuelMap");
            fuelMapField.setAccessible(true);
            TObjectIntHashMap<Fluid> fuelMap = (TObjectIntHashMap<Fluid>)fuelMapField.get(null);
            modifyFuel(fuelMap, "pyrotheum", 54000);
        } catch (Exception e) {
            FVMod.LOGGER.warn("Magmatic fuel hack failed!", e);
        }
    }

    private static void modifyFuel(TObjectIntHashMap<Fluid> fuelMap, String name, int energy) {
        fuelMap.put(FluidRegistry.getFluid(name), energy);
    }

}
