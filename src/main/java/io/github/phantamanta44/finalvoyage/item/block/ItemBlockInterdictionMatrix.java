package io.github.phantamanta44.finalvoyage.item.block;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.base.ItemBlockMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ItemBlockInterdictionMatrix extends ItemBlockMod {

    private static final String NEWLINE = Pattern.quote("!n");

    public ItemBlockInterdictionMatrix(Block block) {
        super(block);
        FVMod.PROXY.registerItemModel(this, 0, "interdiction_matrix");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        Arrays.stream(LangConst.get(LangConst.TT_INTERDICTION).split(NEWLINE))
                .map(l -> TextFormatting.GRAY + l)
                .forEach(tooltip::add);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

}
