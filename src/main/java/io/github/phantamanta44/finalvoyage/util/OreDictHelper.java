package io.github.phantamanta44.finalvoyage.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class OreDictHelper {

    public static ItemStack getStack(String entry, int qty) {
        List<ItemStack> items = OreDictionary.getOres(entry, false);
        if (items.isEmpty())
            return null;
        ItemStack stack = items.get(0).copy();
        stack.stackSize = qty;
        return stack;
    }

    public static boolean exists(String entry) {
        return OreDictionary.doesOreNameExist(entry);
    }

}
