package io.github.phantamanta44.finalvoyage.integration;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.BlockNetherDirt;
import io.github.phantamanta44.finalvoyage.block.FVBlocks;
import io.github.phantamanta44.finalvoyage.item.FVItems;
import io.github.phantamanta44.finalvoyage.util.OreDictHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.function.Predicate;

import static io.github.phantamanta44.finalvoyage.integration.IntHelper.*;
import static io.github.phantamanta44.finalvoyage.integration.IntMod.*;

public class IntRecipes {

    public static void init() {
        registerWater();
        registerPurification();
        registerRehydration();
        registerPrismarine();
        registerMisc();
        registerVanillaRebalancing();
        registerNatura();
        registerMekanism();
        registerQuantumStorage();
        registerRefinedStorage();
        registerReliquary();
        registerAdvGenerators();
        registerChickenChunks();
        registerMPS();
        registerOpenComputers();
        registerSonarCore();
        registerFactoryManager();
        registerBans();
        FVItems.mekanismOre.registerRecipes();
        FVItems.dust.registerRecipes();
    }

    private static void registerWater() {
        IMCHelper.addAmbientAccumulatorRecipe(-1, gasStack("hydrogen", 2));
        IMCHelper.addAmbientAccumulatorRecipe(0, gasStack("oxygen", 2));
        IMCHelper.addAmbientAccumulatorRecipe(1, gasStack("chlorine", 2));
        IMCHelper.addRefineryRecipe(32000,
                new FluidStack(FluidRegistry.LAVA, 100),
                new FluidStack(FluidRegistry.WATER, 100),
                new ItemStack(Blocks.COBBLESTONE));
        for (int i = 2; i <= 9; i++) {
            IMCHelper.addTransposerExtract(2400,
                    itemStack(NAT, "edibles", 1, i),
                    new FluidStack(FluidRegistry.WATER, 100),
                    itemStack(MEK, "BioFuel", 1, 0),
                    false);
        }
    }

    private static void registerPurification() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(TF, "material", 2, 1028),
                "dustGlowstone",
                new ItemStack(Items.PRISMARINE_CRYSTALS),
                "dustRedstone",
                "dustMithril"));
        IMCHelper.addCrucibleRecipe(8000,
                itemStack(TF, "material", 1, 1028),
                fluidStack("mana", 250));
        IMCHelper.addTransposerFill(6000,
                new ItemStack(Blocks.SOUL_SAND, 1),
                fluidStack("mana", 250),
                new ItemStack(Blocks.SAND, 1),
                false);
        IMCHelper.addTransposerFill(6000,
                new ItemStack(Blocks.NETHERRACK, 1),
                fluidStack("mana", 250),
                new ItemStack(Blocks.STONE, 1),
                false);
        IMCHelper.addTransposerFill(1500,
                new ItemStack(Items.NETHERBRICK, 1),
                fluidStack("mana", 125),
                new ItemStack(Items.BRICK, 1),
                false);
        IMCHelper.addTransposerFill(1500,
                new ItemStack(Items.NETHER_WART, 1),
                fluidStack("mana", 125),
                new ItemStack(Items.WHEAT_SEEDS, 1),
                false);
        IMCHelper.addTransposerFill(3000,
                itemStack(NAT, "edibles", 1, 10),
                fluidStack("mana", 250),
                new ItemStack(Items.APPLE, 1),
                false);
        IMCHelper.addTransposerFill(3000,
                new ItemStack(FVBlocks.netherDirt, 1, BlockNetherDirt.Type.DIRT.ordinal()),
                fluidStack("mana", 250),
                new ItemStack(Blocks.DIRT, 1),
                false);
        IMCHelper.addTransposerFill(3000,
                itemStack(NAT, "nether_tainted_soil", 1, 0),
                fluidStack("mana", 250),
                new ItemStack(Blocks.DIRT, 1),
                false);
        IMCHelper.addTransposerFill(3000,
                itemStack(TCON, "materials", 1, 17),
                fluidStack("mana", 250),
                new ItemStack(Items.BONE, 1),
                false);
    }

    private static void registerRehydration() {
        IMCHelper.addTransposerFill(2400,
                new ItemStack(FVBlocks.netherDirt, 1, BlockNetherDirt.Type.CLAY.ordinal()),
                fluidStack("water", 1000),
                new ItemStack(Blocks.CLAY),
                true);
        IMCHelper.addTransposerFill(2400,
                itemStack(NAT, "materials", 1, 7),
                fluidStack("water", 250),
                new ItemStack(Items.STRING),
                false);
    }

    private static void registerPrismarine() {
        IMCHelper.addTransposerFill(3000,
                new ItemStack(Items.GLOWSTONE_DUST),
                fluidStack("mana", 250),
                new ItemStack(Items.PRISMARINE_SHARD),
                false);
        IMCHelper.addPulverizerRecipe(3000,
                new ItemStack(Blocks.PRISMARINE, 1, 0),
                new ItemStack(Items.PRISMARINE_SHARD, 4));
        IMCHelper.addPulverizerRecipe(3000,
                new ItemStack(Blocks.PRISMARINE, 1, 1),
                new ItemStack(Items.PRISMARINE_SHARD, 9));
        IMCHelper.addPulverizerRecipe(3000,
                new ItemStack(Blocks.PRISMARINE, 1, 2),
                new ItemStack(Items.PRISMARINE_SHARD, 8),
                new ItemStack(Items.DYE, 1, 0), 25);
    }

    private static void registerMisc() {
        IMCHelper.addCrucibleRecipe(800,
                new ItemStack(Items.ROTTEN_FLESH),
                fluidStack("blood", 5));
        IMCHelper.addPulverizerRecipe(2400,
                new ItemStack(Blocks.NETHER_WART_BLOCK),
                new ItemStack(Items.NETHER_WART, 9));
        GameRegistry.addSmelting(
                itemStack(IntMod.PLUSTIC, "tindust", 1, 0), OreDictHelper.getStack("ingotTin", 1), 0);
        GameRegistry.addSmelting(
                itemStack(IntMod.PLUSTIC, "osmiumdust", 1, 0), OreDictHelper.getStack("ingotOsmium", 1), 0);
        GameRegistry.addSmelting(
                itemStack(IntMod.PLUSTIC, "steeldust", 1, 0), OreDictHelper.getStack("ingotSteel", 1), 0);
    }

    private static void registerVanillaRebalancing() {
        removeRecipesFor(Items.FLINT_AND_STEEL);
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                new ItemStack(Items.FLINT_AND_STEEL),
                new ItemStack(Items.FLINT),
                "ingotSteel"));
        removeRecipesFor(Items.ENDER_EYE);
        IMCHelper.addTransposerFill(4000,
                new ItemStack(Items.ENDER_PEARL),
                fluidStack("pyrotheum", 500),
                new ItemStack(Items.ENDER_EYE),
                false);
        removeRecipesFor(Items.END_CRYSTAL);
        IMCHelper.addTransposerFill(4000,
                new ItemStack(Items.NETHER_STAR),
                fluidStack("ender", 1000),
                new ItemStack(Items.END_CRYSTAL),
                false);
    }

    private static void registerNatura() {
        removeRecipesFor(itemStack(NAT, "materials", 1, 1));
        IMCHelper.addPulverizerRecipe(2000,
                itemStack(NAT, "materials", 1, 0),
                itemStack(NAT, "materials", 1, 1));
        IMCHelper.addPulverizerRecipe(2000,
                new ItemStack(Items.WHEAT),
                itemStack(NAT, "materials", 1, 2));
        for (int i = 0; i <= 8; i++)
            OreDictionary.registerOre("workbench", itemStack(NAT, "overworld_workbenches", 1, i));
        for (int i = 0; i <= 3; i++)
            OreDictionary.registerOre("workbench", itemStack(NAT, "nether_workbenches", 1, i));
    }

    private static void registerMekanism() {
        IMCHelper.addCompactorPress(4000,
                new ItemStack(Items.COAL, 1, 0),
                itemStack(MEK, "CompressedCarbon", 1, 0));
        IMCHelper.addCompactorPress(4000,
                new ItemStack(Items.COAL, 1, 1),
                itemStack(MEK, "CompressedCarbon", 1, 0));
        IMCHelper.addCompactorPress(4000,
                new ItemStack(Items.REDSTONE, 1, 0),
                itemStack(MEK, "CompressedRedstone", 1, 0));
        IMCHelper.addCompactorPress(4000,
                itemStack(MEK, "OtherDust", 1, 0),
                itemStack(MEK, "CompressedDiamond", 1, 0));
        IMCHelper.addCompactorPress(4000,
                itemStack(MEK, "OtherDust", 1, 1),
                itemStack(MEK, "CompressedObsidian", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MEK, "MachineBlock", 1, 8),
                "ifi", "ror", "ifi",
                'i', "ingotIron",
                'f', itemStack(NAT, "netherrack_furnace", 1, 0),
                'r', "dustRedstone",
                'o', "ingotOsmium"));
        removeRecipesFor(itemStack(MEK, "AnchorUpgrade", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MEK, "AnchorUpgrade", 1, 0),
                " g ", "aca", " g ",
                'g', "blockGlass",
                'a', "alloyAdvanced",
                'c', itemStack(CHUNKS, "chickenChunkLoader", 1, 0)));
    }

    private static void registerQuantumStorage() {
        removeRecipesFor(itemStack(QS, "quantumdsu", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(QS, "quantumdsu", 1, 0),
                "pcp", "sgs", "pcp",
                'p', "plateIridium",
                'c', "circuitUltimate",
                's', itemStack(RS, "storage_part", 1, 3),
                'g', itemStack(RS, "grid", 1, 0)));
        removeRecipesFor(itemStack(QS, "quantumtank", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(QS, "quantumtank", 1, 0),
                "pcp", "sgs", "pcp",
                'p', "plateIridium",
                'c', "circuitUltimate",
                's', itemStack(RS, "fluid_storage_part", 1, 3),
                'g', itemStack(RS, "grid", 1, 3)));
        removeRecipesFor(itemStack(QS, "renderupgrade", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(QS, "renderupgrade", 1, 0),
                " h ", "cdc", " r ",
                'h', itemStack(PL2, "HolographicDisplay", 1, 0),
                'c', "circuitBasic",
                'd', "dustSapphire",
                'r', itemStack(PL2, "InventoryReader", 1, 0)));
    }

    private static void registerRefinedStorage() {
        removeRecipesFor(itemStack(RS, "controller", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "controller", 1, 0),
                "opo", "sfs", "oso",
                'o', "ingotRefinedObsidian",
                'p', itemStack(RS, "processor", 1, 5),
                's', "itemSilicon",
                'f', itemStack(RS, "machine_casing", 1, 0)));
        removeRecipesFor(itemStack(RS, "processing_pattern_encoder", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "processing_pattern_encoder", 1, 0),
                "ici", "pfp", "ici",
                'i', itemStack(RS, "quartz_enriched_iron", 1, 0),
                'c', itemStack(RS, "processor", 1, 5),
                'p', itemStack(RS, "pattern", 1, 0),
                'f', itemStack(RS, "machine_casing", 1, 0)));
        removeRecipesFor(itemStack(RS, "solderer", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "solderer", 1, 0),
                "ili", "ici", "ipi",
                'i', "ingotConstantan",
                'l', itemStack(ORADIO, "laserItem", 1, 0),
                'c', "circuitAdvanced",
                'p', "plateIron"));
        removeRecipesFor(itemStack(RS, "cable", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "cable", 8, 0),
                "ggg", "sss", "ggg",
                'g', "blockGlassHardened",
                's', "itemSilicon"));
        removeRecipesFor(itemStack(RS, "importer", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "importer", 1, 0),
                " w ", " c ", "ipi",
                'w', itemStack(RS, "cable", 1, 0),
                'c', itemStack(RS, "core", 1, 1),
                'i', itemStack(RS, "quartz_enriched_iron", 1, 0),
                'p', new ItemStack(Blocks.STICKY_PISTON)));
        removeRecipesFor(itemStack(RS, "exporter", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "exporter", 1, 0),
                " w ", "ici", " p ",
                'w', itemStack(RS, "cable", 1, 0),
                'c', itemStack(RS, "core", 1, 0),
                'i', itemStack(RS, "quartz_enriched_iron", 1, 0),
                'p', new ItemStack(Blocks.PISTON)));
        removeRecipesFor(itemStack(RS, "external_storage", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "external_storage", 1, 0),
                "cqd", "owi", "qpq",
                'c', itemStack(RS, "core", 1, 0),
                'q', itemStack(RS, "quartz_enriched_iron", 1, 0),
                'd', itemStack(RS, "core", 1, 1),
                'o', new ItemStack(Blocks.PISTON),
                'w', itemStack(RS, "cable", 1, 0),
                'i', new ItemStack(Blocks.STICKY_PISTON),
                'p', itemStack(RS, "processor", 1, 4)));
        removeRecipesFor(itemStack(RS, "network_transmitter", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "network_transmitter", 1, 0),
                "ici", "efe", "isi",
                'i', "ingotTin",
                'c', itemStack(MEK, "TeleportationCore", 1, 0),
                'e', "nuggetEnderium",
                'f', itemStack(RS, "machine_casing", 1, 0),
                's', itemStack(OC, "component", 1, 15)));
        removeRecipesFor(itemStack(RS, "network_receiver", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(RS, "network_receiver", 1, 0),
                "ici", "efe", "isi",
                'i', "ingotTin",
                'c', itemStack(MEK, "TeleportationCore", 1, 0),
                'e', "nuggetEnderium",
                'f', itemStack(RS, "machine_casing", 1, 0),
                's', itemStack(ORADIO, "dsp", 1, 2)));
        removeRecipesFor(itemStack(RS, "core", 1, 0));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(RS, "core", 2, 0),
                itemStack(RS, "processor", 1, 4),
                "dustMana",
                new ItemStack(Items.QUARTZ)));
        removeRecipesFor(itemStack(RS, "core", 1, 1));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(RS, "core", 2, 1),
                itemStack(RS, "processor", 1, 4),
                "dustMana",
                new ItemStack(Items.GLOWSTONE_DUST)));
        removeRecipesFor(itemStack(RS, "quartz_enriched_iron", 1, 0));
        IMCHelper.addInductionSmelterRecipe(4000,
                new ItemStack(Items.QUARTZ, 2),
                new ItemStack(Items.IRON_INGOT),
                itemStack(RS, "quartz_enriched_iron", 1, 0));
    }

    private static void registerReliquary() {
        // NO-OP
    }

    private static void registerAdvGenerators() {
        removeRecipesFor(itemStack(ADV_GEN, "IronFrame", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "IronFrame", 2, 0),
                " p ", "p p", " p ",
                'p', "plateIron"));
        removeRecipesFor(itemStack(ADV_GEN, "PowerIO", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "PowerIO", 1, 0),
                "ctc", "aea", "crc",
                'c', itemStack(MEK, "MultipartTransmitter", 1, 1),
                't', itemStack(TF, "material", 1, 514),
                'a', "alloyAdvanced",
                'e', "circuitElite",
                'r', itemStack(TF, "material", 1, 513)));
        removeRecipesFor(itemStack(ADV_GEN, "Controller", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "Controller", 1, 0),
                "sps", "ici", "sps",
                's', "itemSilicon",
                'p', "plateIridium",
                'i', "ingotElectrum",
                'c', "circuitElite"));
        removeRecipesFor(itemStack(ADV_GEN, "PressureValve", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "PressureValve", 1, 0),
                "sfs",
                's', "plateSteel",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "AdvancedPressureValve", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "AdvancedPressureValve", 1, 0),
                "ppp", "cvc", "ppp",
                'p', "plateConstantan",
                'c', "dustEnderium",
                'v', itemStack(ADV_GEN, "PressureValve", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "UpgradeKit", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "UpgradeKit", 1, 0),
                " i ", "igi", "rir",
                'i', "ingotSteel",
                'g', "gearIridium",
                'r', "dustRedstone"));
        removeRecipesFor(itemStack(ADV_GEN, "GasInput", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "GasInput", 1, 0),
                "sps", "fvf", "sps",
                's', "plateSteel",
                'p', itemStack(MEK, "MultipartTransmitter", 1, 9),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'v', itemStack(ADV_GEN, "PressureValve", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "FluidInput", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "FluidInput", 1, 0),
                "sps", "fvf", "sps",
                's', "plateSteel",
                'p', itemStack(MEK, "MultipartTransmitter", 1, 5),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'v', itemStack(ADV_GEN, "PressureValve", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "FluidOutputSelect", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "FluidOutputSelect", 1, 0),
                "sps", "fpf", "svs",
                's', "plateSteel",
                'p', itemStack(MEK, "MultipartTransmitter", 1, 5),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'v', itemStack(ADV_GEN, "PressureValve", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "ItemInput", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "ItemInput", 1, 0),
                "sps", "fvf", "sps",
                's', "plateSteel",
                'p', itemStack(MEK, "MultipartTransmitter", 1, 13),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'v', itemStack(ADV_GEN, "PressureValve", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "ItemOutput", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "ItemOutput", 1, 0),
                "sps", "fpf", "svs",
                's', "plateSteel",
                'p', itemStack(MEK, "MultipartTransmitter", 1, 13),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'v', itemStack(ADV_GEN, "PressureValve", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "FuelTank", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "FuelTank", 1, 0),
                "fpf", "pbp", "fpf",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'p', "plateSteel",
                'b', new ItemStack(Items.BUCKET)));
        removeRecipesFor(itemStack(ADV_GEN, "HeatingChamber", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "HeatingChamber", 1, 0),
                "fpf", "pbp", "fpf",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'p', "plateConstantan",
                'b', itemStack(MEK, "MultipartTransmitter", 1, 19)));
        removeRecipesFor(itemStack(ADV_GEN, "MixingChamber", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "MixingChamber", 1, 0),
                "fpf", "pbp", "fpf",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'p', "plateSteel",
                'b', itemStack(MEK_GEN, "TurbineBlade", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "HeatExchanger", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "HeatExchanger", 1, 0),
                "ccc", "ftf", "ccc",
                'c', "plateConstantan",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                't', itemStack(MEK, "MultipartTransmitter", 1, 19)));
        removeRecipesFor(itemStack(ADV_GEN, "Sensor", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "Sensor", 1, 0),
                "fpf", "crc", "fpf",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'p', "plateTin",
                'c', "circuitAdvanced",
                'r', itemStack(PL2, "InfoReader", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "Control", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "Control", 1, 0),
                "fpf", "crc", "fpf",
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'p', "plateTin",
                'c', "circuitAdvanced",
                'r', "dustRedstone"));
        removeRecipesFor(itemStack(ADV_GEN, "EfficiencyUpgradeTier1", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "EfficiencyUpgradeTier1", 1, 0),
                "ptp", "fcf", "ptp",
                'p', "plateIridium",
                't', itemStack(ADV_GEN, "PressureValve", 1, 0),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'c', "circuitElite"));
        removeRecipesFor(itemStack(ADV_GEN, "EfficiencyUpgradeTier2", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "EfficiencyUpgradeTier2", 1, 0),
                "ptp", "fcf", "ptp",
                'p', "plateEnderium",
                't', itemStack(ADV_GEN, "AdvancedPressureValve", 1, 0),
                'f', itemStack(ADV_GEN, "IronFrame", 1, 0),
                'c', "circuitUltimate"));
        removeRecipesFor(itemStack(ADV_GEN, "PowerCapacitorRedstone", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "PowerCapacitorRedstone", 1, 0),
                "hlh", "lcl", "hlh",
                'h', itemStack(MPS, "powerArmorComponent", 1, 6),
                'l', itemStack(MPS, "powerArmorComponent", 1, 5),
                'c', "circuitAdvanced"));
        removeRecipesFor(itemStack(ADV_GEN, "PowerCapacitorAdvanced", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "PowerCapacitorAdvanced", 1, 0),
                "hlh", "lcl", "hlh",
                'h', itemStack(MPS, "powerArmorComponent", 1, 7),
                'l', itemStack(ADV_GEN, "PowerCapacitorRedstone", 1, 0),
                'c', "circuitElite"));
        removeRecipesFor(itemStack(ADV_GEN, "CapacitorKitAdvanced", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "CapacitorKitAdvanced", 1, 0),
                "hlh", "lcl", "hkh",
                'h', itemStack(MPS, "powerArmorComponent", 1, 7),
                'l', itemStack(ADV_GEN, "PowerCapacitorRedstone", 1, 0),
                'c', "circuitElite",
                'k', itemStack(ADV_GEN, "UpgradeKit", 1, 0)));
        removeRecipesFor(itemStack(ADV_GEN, "PowerCapacitorDense", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "PowerCapacitorDense", 1, 0),
                "hlh", "lcl", "hlh",
                'h', itemStack(MPS, "powerArmorComponent", 1, 8),
                'l', itemStack(ADV_GEN, "PowerCapacitorAdvanced", 1, 0),
                'c', "circuitUltimate"));
        removeRecipesFor(itemStack(ADV_GEN, "CapacitorKitDense", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(ADV_GEN, "CapacitorKitDense", 1, 0),
                "hlh", "lcl", "hkh",
                'h', itemStack(MPS, "powerArmorComponent", 1, 8),
                'l', itemStack(ADV_GEN, "PowerCapacitorAdvanced", 1, 0),
                'c', "circuitUltimate",
                'k', itemStack(ADV_GEN, "UpgradeKit", 1, 0)));
    }

    private static void registerChickenChunks() {
        removeRecipesFor(itemStack(CHUNKS, "chickenChunkLoader", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(CHUNKS, "chickenChunkLoader", 1, 0),
                "aca", "gtg", "aca",
                'a', "alloyUltimate",
                'c', "circuitUltimate",
                'g', "gearEnderium",
                't', itemStack(MEK, "TeleportationCore", 1, 0)));
        removeRecipesFor(itemStack(CHUNKS, "chickenChunkLoader", 1, 1));
        IMCHelper.addTransposerFill(3600,
                itemStack(CHUNKS, "chickenChunkLoader", 1, 0),
                fluidStack("ender", 1500),
                itemStack(CHUNKS, "chickenChunkLoader", 8, 1),
                false);
    }

    private static void registerMPS() {
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorHead", 1, 0),
                "ppp", "scs",
                'p', "plateSteel",
                's', "plateIridium",
                'c', "circuitAdvanced"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorTorso", 1, 0),
                "scs", "sps", "ppp",
                'p', "plateSteel",
                's', "plateIridium",
                'c', "circuitAdvanced"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorLegs", 1, 0),
                "sss", "pcp", "p p",
                'p', "plateSteel",
                's', "plateIridium",
                'c', "circuitAdvanced"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorFeet", 1, 0),
                "pcp", "s s",
                'p', "plateSteel",
                's', "plateIridium",
                'c', "circuitAdvanced"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerTool", 1, 0),
                "sps", "sps", " c ",
                'p', "ingotSteel",
                's', "plateIridium",
                'c', "circuitAdvanced"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "tile.tinkerTable", 1, 0),
                " h ", "cfc", "sws",
                'h', itemStack(PL2, "HolographicDisplay", 1, 0),
                'c', "circuitAdvanced",
                'f', itemStack(MEK, "BasicBlock", 1, 8),
                's', itemStack(MPS, "powerArmorComponent", 1, 2),
                'w', itemStack(OC, "assembler", 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 0),
                "ppp", "ccc", "ppp",
                'p', "plateCopper",
                'c', "ingotElectrum"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 5),
                " w ", "pep", " w ",
                'w', itemStack(MPS, "powerArmorComponent", 1, 0),
                'p', "nuggetLead",
                'e', "ingotElectrum"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 6),
                " i ", "ldl", " i ",
                'l', itemStack(MPS, "powerArmorComponent", 1, 5),
                'i', "ingotSignalum",
                'd', "dustCryotheum"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 7),
                " i ", "ldl", " i ",
                'l', itemStack(MPS, "powerArmorComponent", 1, 6),
                'i', "ingotIridium",
                'd', "dustRefinedObsidian"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 8),
                " i ", "ldl", " i ",
                'l', itemStack(MPS, "powerArmorComponent", 1, 7),
                'i', "ingotEnderium",
                'd', "dustLithium"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 9),
                "www", "sis", "sis",
                'w', "blockWool",
                's', "string",
                'i', "ingotTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 10),
                "pap", "aga", "pap",
                'p', "plateSteel",
                'a', "alloyAdvanced",
                'g', "gearInvar"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 11),
                "pap", "aga", "pap",
                'p', "plateIridium",
                'a', "alloyElite",
                'g', itemStack(MPS, "powerArmorComponent", 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 12),
                "mlm", "cgc", "mlm",
                'm', itemStack(MPS, "powerArmorComponent", 1, 19),
                'l', itemStack(MEK, "MachineBlock2", 1, 13),
                'c', "circuitElite",
                'g', itemStack(MPS, "powerArmorComponent", 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 13),
                "ggg", "chc", "iri",
                'g', "blockGlass",
                'c', "circuitBasic",
                'h', itemStack(PL2, "HolographicDisplay", 1, 0),
                'i', "ingotIron",
                'r', itemStack(TF, "material", 1, 513)));
        IMCHelper.addTransposerFill(4000,
                itemStack(TF, "material", 1, 768),
                fluidStack("coal", 3500),
                itemStack(MPS, "powerArmorComponent", 1, 14),
                false);
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 15),
                "tdt", "cuc", "tat",
                't', itemStack(OC, "material", 1, 7),
                'd', itemStack(OC, "card", 1, 10),
                'c', "circuitBasic",
                'u', itemStack(OC, "component", 1, 0),
                'a', itemStack(OC, "material", 1, 11)));
        IMCHelper.addTransposerFill(2400,
                new ItemStack(Items.MAGMA_CREAM),
                fluidStack("refined_oil", 250),
                itemStack(MPS, "powerArmorComponent", 1, 16),
                false);
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 18),
                "sss", "tct", "wcw",
                's', itemStack(MEK_GEN, "SolarPanel", 1, 0),
                't', itemStack(TF, "material", 1, 514),
                'c', "alloyAdvanced",
                'w', itemStack(MPS, "powerArmorComponent", 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 20),
                "tdt", "mcm", "tpt",
                't', itemStack(OC, "material", 1, 7),
                'd', itemStack(OC, "component", 1, 10),
                'm', itemStack(OC, "material", 1, 10),
                'c', itemStack(MPS, "powerArmorComponent", 1, 15),
                'p', itemStack(OC, "component", 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(MPS, "powerArmorComponent", 1, 21),
                "ttt", "g g", "ttt",
                't', itemStack(TF, "material", 1, 833),
                'g', "blockGlassHardened"));
    }

    private static void registerOpenComputers() {
        removeRecipesFor(itemStack(OC, "material", 1, 3));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(OC, "material", 3, 3),
                "itemSilicon",
                "dustGold",
                "dustCopper",
                new ItemStack(Items.CLAY_BALL)));
        removeRecipesFor(itemStack(OC, "material", 1, 30));
        IMCHelper.addInductionSmelterRecipe(1800,
                itemStack(MEK, "OtherDust", 1, 0),
                itemStack(RS, "silicon", 6, 0),
                itemStack(OC, "material", 6, 30));
        removeRecipesFor(itemStack(OC, "upgrade", 1, 4));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(OC, "upgrade", 1, 4),
                "igi", "mcm", "opo",
                'i', "ingotGold",
                'g', "blockGlass",
                'm', itemStack(OC, "material", 1, 10),
                'c', itemStack(CHUNKS, "chickenChunkLoader", 1, 0),
                'o', "ingotRefinedObsidian",
                'p', itemStack(OC, "material", 1, 5)));
    }

    private static void registerSonarCore() {
        removeRecipesFor(itemStack(SCORE, "ReinforcedStoneBlock", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(SCORE, "ReinforcedStoneBlock", 1, 0),
                "nsn",
                'n', "nuggetLead",
                's', new ItemStack(Blocks.COBBLESTONE)));
    }

    private static void registerFactoryManager() {
        removeRecipesFor(itemStack(SFM, "BlockMachineManagerName", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(SFM, "BlockMachineManagerName", 1, 0),
                "iri", "pfp", "ici",
                'i', "ingotSteel",
                'r', "dustRedstone",
                'p', itemStack(SFM, "BlockCableName", 1, 0),
                'f', itemStack(MEK, "BasicBlock", 1, 8),
                'c', itemStack(MPS, "powerArmorComponent", 1, 15)));
        removeRecipesFor(itemStack(SFM, "BlockCableName", 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                itemStack(SFM, "BlockCableName", 8, 0),
                "ppp", "pcp", "ppp",
                'p', itemStack(MEK, "MultipartTransmitter", 1, 13),
                'c', itemStack(OC, "material", 1, 21)));
        removeRecipesFor(itemStack(SFM, "BlockCableRelayName", 1, 8));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(SFM, "BlockCableRelayName", 1, 8),
                itemStack(SFM, "BlockCableRelayName", 1, 0),
                "circuitBasic"));
        removeRecipesFor(itemStack(SFM, "BlockCableIntakeName", 1, 0));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(SFM, "BlockCableIntakeName", 1, 0),
                itemStack(SFM, "BlockCableName", 1, 0),
                itemStack(ADV_GEN, "PressureValve", 1, 0),
                new ItemStack(Blocks.HOPPER)));
        removeRecipesFor(itemStack(SFM, "BlockCableIntakeName", 1, 8));
        IMCHelper.addTransposerFill(4000,
                itemStack(SFM, "BlockCableIntakeName", 1, 0),
                fluidStack("glowstone", 1000),
                itemStack(SFM, "BlockCableIntakeName", 1, 8),
                false);
        removeRecipesFor(itemStack(SFM, "BlockCableBreakerName", 1, 0));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(SFM, "BlockCableBreakerName", 1, 0),
                itemStack(RS, "destructor", 1, 0),
                itemStack(RS, "constructor", 1, 0)));
        removeRecipesFor(itemStack(SFM, "BlockCableCamouflageName", 1, 0));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(SFM, "BlockCableCamouflageName", 1, 0),
                itemStack(SFM, "BlockCableName", 1, 0),
                itemStack(OC, "material", 1, 29)));
        removeRecipesFor(itemStack(SFM, "BlockCableCamouflageName", 1, 2));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                itemStack(SFM, "BlockCableCamouflageName", 1, 2),
                itemStack(SFM, "BlockCableCamouflageName", 1, 1),
                itemStack(MPS, "powerArmorComponent", 1, 13)));
    }

    private static void registerBans() {
        removeRecipesFor(itemStack(RELIQ, "emperor_chalice", 1, 0));
        removeRecipesFor(itemStack(RELIQ, "alkahestry_tome", 1, 1001));
        // FIXME removeRecipesFor(itemStack(P_RED_EXP, "solar_panel", 1, 0));
        removeRecipesFor(itemStack(ZERO_CORE, "debugtool", 1, 0));
    }

    private static void removeRecipesFor(Predicate<ItemStack> output) {
        if (!CraftingManager.getInstance().getRecipeList().removeIf(r -> {
            ItemStack rOut = r.getRecipeOutput();
            return rOut != null && output.test(rOut);
        })) {
            FVMod.LOGGER.warn("Failed to remove some recipes:", new IllegalArgumentException());
        }
    }

    private static void removeRecipesFor(Item output, int outputMeta) {
        removeRecipesFor(s -> s.getItem() == output && s.getMetadata() == outputMeta);
    }

    private static void removeRecipesFor(ItemStack output) {
        if (output.getItem() != null)
            removeRecipesFor(output.getItem(), output.getMetadata());
        else
            FVMod.LOGGER.warn("Failed to remove some recipes:", new IllegalArgumentException());
    }

    private static void removeRecipesFor(Item output) {
        removeRecipesFor(s -> s.getItem() == output);
    }

}
