package io.github.phantamanta44.finalvoyage.item;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.item.base.ItemModSubs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ItemFood extends ItemModSubs {

    public ItemFood() {
        super(LangConst.ITEM_FOOD_NAME, 1);
        for (Type type : Type.values())
            FVMod.PROXY.registerItemModel(this, type.ordinal(), LangConst.ITEM_FOOD_NAME + "_" + type.getName());
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        --stack.stackSize;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            Type type = Type.values()[stack.getMetadata()];
            player.getFoodStats().addStats(type.food, type.saturation);
            type.action.accept(player);
            player.addStat(StatList.getObjectUseStats(this));
            world.playSound(null, player.posX, player.posY, player.posZ,
                    SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,
                    0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (player.canEat(false)) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }
    }

    public enum Type implements IStringSerializable {

        MRE(7, 8F, p -> {});

        public final int food;
        public final float saturation;
        public final Consumer<EntityPlayer> action;

        Type(int food, float saturation, Consumer<EntityPlayer> action) {
            this.food = food;
            this.saturation = saturation;
            this.action = action;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

    }

}
