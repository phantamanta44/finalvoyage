package io.github.phantamanta44.finalvoyage.recipe;

import io.github.phantamanta44.finalvoyage.block.FVBlocks;
import io.github.phantamanta44.finalvoyage.integration.IMCHelper;
import io.github.phantamanta44.finalvoyage.integration.IntHelper;
import io.github.phantamanta44.finalvoyage.integration.IntMod;
import io.github.phantamanta44.finalvoyage.item.FVItems;
import io.github.phantamanta44.finalvoyage.item.ItemDrink;
import io.github.phantamanta44.finalvoyage.item.ItemFood;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {

    public static void init() {
        registerConsumables();
        registerEnderPearl();
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(FVItems.waterBottle),
                " v ", "pbp", " p ",
                'v', IntHelper.itemStack(IntMod.ADV_GEN, "PressureValve", 1, 0),
                'b', new ItemStack(Items.BUCKET),
                'p', "plateAluminum"));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(FVBlocks.interdictionMatrix),
                "tht", "gdg", "tct",
                't', IntHelper.itemStack(IntMod.RELIQ, "interdiction_torch", 1, 0),
                'h', IntHelper.itemStack(IntMod.OC, "hologram2", 1, 0),
                'g', "blockGlassHardened",
                'd', IntHelper.itemStack(IntMod.OSEC, "entity_detector", 1, 0),
                'c', "circuitAdvanced"));
    }

    private static void registerConsumables() {
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(FVItems.food, 1, ItemFood.Type.MRE.ordinal()),
                " p ", "pfp", " p ",
                'p', IntHelper.itemStack(IntMod.MEK, "Polyethene", 1, 0),
                'f', "listAllmeatcooked"));
        for (int i = 10; i <= 15; i++) {
            GameRegistry.addRecipe(new ShapedOreRecipe(
                    new ItemStack(FVItems.food, 1, ItemFood.Type.MRE.ordinal()),
                    " p ", "pfp", " p ",
                    'p', IntHelper.itemStack(IntMod.MEK, "Polyethene", 1, 0),
                    'f', IntHelper.itemStack(IntMod.TCON, "edible", 1, i)));
        }
        for (int i = 20; i <= 23; i++) {
            GameRegistry.addRecipe(new ShapedOreRecipe(
                    new ItemStack(FVItems.food, 1, ItemFood.Type.MRE.ordinal()),
                    " p ", "pfp", " p ",
                    'p', IntHelper.itemStack(IntMod.MEK, "Polyethene", 1, 0),
                    'f', IntHelper.itemStack(IntMod.TCON, "edible", 1, i)));
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(FVItems.drink, 1, ItemDrink.Type.WATER_SACK.ordinal()),
                " p ", "pwp", " p ",
                'p', IntHelper.itemStack(IntMod.MEK, "Polyethene", 1, 0),
                'w', new ItemStack(Items.POTIONITEM, 1, 0)));
    }

    private static void registerEnderPearl() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(
                new ItemStack(FVItems.misc, 5, 0),
                "dustPyrotheum", "dustCryotheum", "dustPetrotheum",
                "dustAerotheum", "dustMana"));
        IMCHelper.addCrucibleRecipe(3200,
                new ItemStack(FVItems.misc, 1, 0),
                IntHelper.fluidStack("ender", 100));
        IMCHelper.addTransposerFill(16000,
                new ItemStack(Items.DIAMOND),
                IntHelper.fluidStack("ender", 250),
                new ItemStack(Items.ENDER_PEARL),
                false);
    }

}
