package io.github.phantamanta44.finalvoyage.event;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.NBTConst;
import io.github.phantamanta44.finalvoyage.integration.IntMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.lang.reflect.Field;
import java.util.function.IntSupplier;

public class PlayerListener {

    private final Item tconMaterials, natShelvesOverworld, natShelvesNether;
    private IntSupplier tconMendingLevel;

    public PlayerListener() {
        tconMaterials = IntMod.TCON.getItem("materials");
        natShelvesOverworld = IntMod.NAT.getItem("overworld_bookshelves");
        natShelvesNether = IntMod.NAT.getItem("nether_bookshelves");
        try {
            Field mendingLevelField = Class.forName("slimeknights.tconstruct.tools.modifiers.ModMendingMoss")
                    .getDeclaredField("MENDING_MOSS_LEVELS");
            tconMendingLevel = () -> {
                try {
                    return mendingLevelField.getInt(null);
                } catch (Exception e) {
                    return 10;
                }
            };
        } catch (Exception e) {
            FVMod.LOGGER.warn("Could not retrieve Tinkers' Construct mending moss config!", e);
            tconMendingLevel = () -> 10;
        }
    }

    @SubscribeEvent
    public void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        NBTTagCompound nbt = event.player.getEntityData();
        if (!nbt.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            nbt.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        nbt = nbt.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (!nbt.hasKey(NBTConst.FV_SUB_KEY))
            nbt.setTag(NBTConst.FV_SUB_KEY, new NBTTagCompound());
        nbt = nbt.getCompoundTag(NBTConst.FV_SUB_KEY);
        if (!nbt.getBoolean(NBTConst.STARTER_KIT)) {
            nbt.setBoolean(NBTConst.STARTER_KIT, true);
            FVMod.PROXY.procureStarterKit(event.player);
        }
    }

    @SubscribeEvent
    public void onItemUse(PlayerInteractEvent event) {
        if (event.getItemStack() != null) {
            ItemStack stack = event.getItemStack();
            if (stack.getItem() == Items.GLASS_BOTTLE) {
                float f = event.getEntity().rotationPitch;
                float f1 = event.getEntity().rotationYaw;
                double d0 = event.getEntity().posX;
                double d1 = event.getEntity().posY + (double)event.getEntity().getEyeHeight();
                double d2 = event.getEntity().posZ;
                Vec3d vec3d = new Vec3d(d0, d1, d2);
                float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
                float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
                float f4 = -MathHelper.cos(-f * 0.017453292F);
                float f5 = MathHelper.sin(-f * 0.017453292F);
                float f6 = f3 * f4;
                float f7 = f2 * f4;
                double d3 = 5.0D;
                if (event.getEntity() instanceof net.minecraft.entity.player.EntityPlayerMP)
                    d3 = ((EntityPlayerMP)event.getEntity()).interactionManager.getBlockReachDistance();
                Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
                RayTraceResult trace = event.getWorld().rayTraceBlocks(vec3d, vec3d1, true, false, false);
                if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK
                        && event.getWorld().getBlockState(trace.getBlockPos()).getMaterial() == Material.WATER) {
                    event.setCanceled(true);
                }
            } else if (stack.getItem() == tconMaterials && stack.getMetadata() == 18) {
                if (event.getFace() != null) {
                    IBlockState state = event.getWorld().getBlockState(event.getPos());
                    if (state != null && !state.getBlock().isAir(state, event.getWorld(), event.getPos())) {
                        Item item = Item.getItemFromBlock(state.getBlock());
                        if (item != null && (item == natShelvesOverworld || item == natShelvesNether)) {
                            EntityPlayer player = event.getEntityPlayer();
                            player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                            if (!event.getWorld().isRemote) {
                                event.getItemStack().stackSize--;
                                player.removeExperienceLevel(tconMendingLevel.getAsInt());
                                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(tconMaterials, 1, 19));
                                event.setResult(Event.Result.DENY);
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

}
