package io.github.phantamanta44.finalvoyage.item;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.base.IThirstMutator;
import io.github.phantamanta44.finalvoyage.item.base.ItemModSubs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ItemDrink extends ItemModSubs implements IThirstMutator {

    public ItemDrink() {
        super(LangConst.ITEM_DRINK_NAME, 1);
        for (Type type : Type.values())
            FVMod.PROXY.registerItemModel(this, type.ordinal(), LangConst.ITEM_DRINK_NAME + "_" + type.getName());
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        --stack.stackSize;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            ItemDrink.Type type = ItemDrink.Type.values()[stack.getMetadata()];
            type.action.accept(player);
            player.addStat(StatList.getObjectUseStats(this));
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (FVMod.PROXY.getThirst(player) < 20F && !player.capabilities.disableDamage) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }
    }

    @Override
    public float getThirstOffset(ItemStack stack) {
        return Type.values()[stack.getMetadata()].thirst;
    }

    public enum Type implements IStringSerializable {

        WATER_SACK(1.33F, p -> {});

        public final float thirst;
        public final Consumer<EntityPlayer> action;

        Type(float thirst, Consumer<EntityPlayer> action) {
            this.thirst = thirst;
            this.action = action;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

    }

}
