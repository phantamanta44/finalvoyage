package io.github.phantamanta44.finalvoyage.event;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.NBTConst;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerListener {

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
        if (event.getItemStack() != null && event.getItemStack().getItem() == Items.GLASS_BOTTLE) {
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
                d3 = ((net.minecraft.entity.player.EntityPlayerMP)event.getEntity()).interactionManager.getBlockReachDistance();
            Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
            RayTraceResult trace = event.getWorld().rayTraceBlocks(vec3d, vec3d1, true, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK
                    && event.getWorld().getBlockState(trace.getBlockPos()).getMaterial() == Material.WATER) {
                event.setCanceled(true);
            }
        }
    }

}
