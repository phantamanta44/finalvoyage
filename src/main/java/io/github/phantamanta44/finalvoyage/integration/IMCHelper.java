package io.github.phantamanta44.finalvoyage.integration;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.util.BuildableTagCompound;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class IMCHelper {

    // Thermal Expansion

    public static void addCompactorPress(int energy, ItemStack input, ItemStack output) {
        messageRuntime(IntMod.TE, "addcompactorpressrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("input", input)
                .withItemStack("output", output));
    }

    public static void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
        messageRuntime(IntMod.TE, "addcruciblerecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("input", input)
                .withFluidStack("output", output));
    }

    public static void addInductionSmelterRecipe(int energy, ItemStack inputA, ItemStack inputB, ItemStack output) {
        messageRuntime(IntMod.TE, "addsmelterrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("primaryInput", inputA)
                .withItemStack("secondaryInput", inputB)
                .withItemStack("primaryOutput", output));
    }

    public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack output) {
        messageRuntime(IntMod.TE, "addpulverizerrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("input", input)
                .withItemStack("primaryOutput", output));
    }

    public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack output, ItemStack chanceOutput, int chance) {
        messageRuntime(IntMod.TE, "addpulverizerrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("input", input)
                .withItemStack("primaryOutput", output)
                .withItemStack("secondaryOutput", chanceOutput)
                .withInt("secondaryChance", chance));
    }

    public static void removePulverizerRecipe(ItemStack input) {
        messageRuntime(IntMod.TE, "removepulverizerrecipe", new BuildableTagCompound()
                .withItemStack("input", input));
    }

    public static void addRefineryRecipe(int energy, FluidStack input, FluidStack outputFluid, ItemStack outputItem) {
        messageRuntime(IntMod.TE, "addrefineryrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withFluidStack("input", input)
                .withFluidStack("output", outputFluid)
                .withItemStack("outputItem", outputItem));
    }

    public static void addTransposerExtract(int energy, ItemStack input, FluidStack outputFluid, ItemStack output, boolean reversible) {
        messageRuntime(IntMod.TE, "addtransposerextractrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("input", input)
                .withItemStack("output", output)
                .withFluidStack("fluid", outputFluid)
                .withBool("reversible", reversible));
    }

    public static void addTransposerFill(int energy, ItemStack inputItem, FluidStack inputFluid, ItemStack output, boolean reversible) {
        messageRuntime(IntMod.TE, "addtransposerfillrecipe", new BuildableTagCompound()
                .withInt("energy", energy)
                .withItemStack("input", inputItem)
                .withItemStack("output", output)
                .withFluidStack("fluid", inputFluid)
                .withBool("reversible", reversible));
    }

    // Mekanism

    public static void addAmbientAccumulatorRecipe(int dimension, GasStack output) {
        message(IntMod.MEK, "AmbientAccumulatorRecipe", new BuildableTagCompound()
                .withInt("input", dimension)
                .withGasStack("output", output));
    }

    public static void addChemicalCrystalizationRecipe(GasStack input, ItemStack output) {
        message(IntMod.MEK, "ChemicalCrystallizerRecipe", new BuildableTagCompound()
                .withGasStack("input", input)
                .withItemStack("output", output));
    }

    public static void addChemicalDissolutionRecipe(ItemStack input, GasStack output) {
        message(IntMod.MEK, "ChemicalDissolutionChamberRecipe", new BuildableTagCompound()
                .withItemStack("input", input)
                .withGasStack("output", output));
    }

    public static void addChemicalInfuserRecipe(GasStack inputA, GasStack inputB, GasStack output) {
        message(IntMod.MEK, "ChemicalInfuserRecipe", new BuildableTagCompound()
                .withGasStack("leftInput", inputA)
                .withGasStack("rightInput", inputB)
                .withGasStack("output", output));
    }

    public static void addChemicalInjectionRecipe(ItemStack inputItem, Gas inputGas, ItemStack output) {
        message(IntMod.MEK, "ChemicalInjectionChamberRecipe", new BuildableTagCompound()
                .withItemStack("input", inputItem)
                .withSerializable("gasType", inputGas::write)
                .withItemStack("output", output));
    }

    public static void addChemicalOxidizerRecipe(ItemStack input, GasStack output) {
        message(IntMod.MEK, "ChemicalOxidizerRecipe", new BuildableTagCompound()
                .withItemStack("input", input)
                .withGasStack("output", output));
    }

    public static void addChemicalWasherRecipe(GasStack input, GasStack output) {
        message(IntMod.MEK, "ChemicalWasherRecipe", new BuildableTagCompound()
                .withGasStack("input", input)
                .withGasStack("output", output));
    }

    public static void addCrusherRecipe(ItemStack input, ItemStack output) {
        message(IntMod.MEK, "CrusherRecipe", new BuildableTagCompound()
                .withItemStack("input", input)
                .withItemStack("output", output));
    }

    public static void addElectrolyticSeparatorRecipe(FluidStack input, GasStack outputA, GasStack outputB) {
        message(IntMod.MEK, "ElectrolyticSeparatorRecipe", new BuildableTagCompound()
                .withFluidStack("input", input)
                .withGasStack("leftOutput", outputA)
                .withGasStack("rightOutput", outputB));
    }

    public static void addEnrichmentRecipe(ItemStack input, ItemStack output) {
        message(IntMod.MEK, "EnrichmentChamberRecipe", new BuildableTagCompound()
                .withItemStack("input", input)
                .withItemStack("output", output));
    }

    public static void removeEnrichmentRecipe(ItemStack input, ItemStack output) {
        message(IntMod.MEK, "RemoveEnrichmentChamberRecipe", new BuildableTagCompound()
                .withItemStack("input", input)
                .withItemStack("output", output));
    }

    public static void addMetallurgicInfuserRecipe(ItemStack inputStack, String inputInfuse, int inputInfuseAmount, ItemStack output) {
        message(IntMod.MEK, "MetallurgicInfuserRecipe", new BuildableTagCompound()
                .withItemStack("input", inputStack)
                .withStr("infuseType", inputInfuse)
                .withInt("infuseAmount", inputInfuseAmount)
                .withItemStack("output", output));
    }

    public static void addPurificationRecipe(ItemStack inputItem, Gas inputGas, ItemStack output) {
        message(IntMod.MEK, "PurificationChamberRecipe", new BuildableTagCompound()
                .withItemStack("input", inputItem)
                .withSerializable("gasType", inputGas::write)
                .withItemStack("output", output));
    }

    private static void message(IntMod target, String method, NBTTagCompound nbt) {
        FMLInterModComms.sendMessage(target.modId, method, nbt);
    }

    private static void messageRuntime(IntMod target, String method, NBTTagCompound nbt) {
        FMLInterModComms.sendRuntimeMessage(FVMod.INSTANCE, target.modId, method, nbt);
    }

}