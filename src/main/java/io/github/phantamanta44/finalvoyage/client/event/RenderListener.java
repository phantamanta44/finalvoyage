package io.github.phantamanta44.finalvoyage.client.event;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.client.ClientProxy;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.LockableBooleanGlState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class RenderListener {

    @SubscribeEvent
    public void onFogDensityCheck(EntityViewRenderEvent.FogDensity event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer entity = (EntityPlayer)event.getEntity();
            IBlockState head = ActiveRenderInfo.getBlockStateAtEntityViewpoint(
                    entity.worldObj, entity, (float)event.getRenderPartialTicks());
            if (entity.getActivePotionEffect(MobEffects.NIGHT_VISION) != null
                    && !(head.getBlock() instanceof BlockLiquid)
                    && !(head.getBlock() instanceof IFluidBlock)
                    && entity.getActivePotionEffect(MobEffects.BLINDNESS) == null) {
                ClientProxy.glStateFog.setLockedState(LockableBooleanGlState.State.DISABLED);
            } else {
                ClientProxy.glStateFog.setLockedState(LockableBooleanGlState.State.NONE);
            }
        }
    }

    @SubscribeEvent
    public void onHudRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(FVConst.LOC_WIDGETS);
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
            GlStateManager.enableBlend();
            GlStateManager.color(1F, 1F, 1F, 1F);
            int left = res.getScaledWidth() / 2 + 91;
            int top = res.getScaledHeight() - 49;
            int level = (int)Math.ceil(FVMod.PROXY.getThirst(Minecraft.getMinecraft().thePlayer));
            Random random = new Random();
            for (int i = 0; i < 10; ++i) {
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                if (level < 4 && gui.getUpdateCounter() % (level * 3 + 1) == 0)
                    y = top + (random.nextInt(3) - 1);
                gui.drawTexturedModalRect(x, y, 0, 0, 9, 9);
                if (idx < level)
                    gui.drawTexturedModalRect(x, y, 9, 0, 9, 9);
                else if (idx == level)
                    gui.drawTexturedModalRect(x, y, 18, 0, 9, 9);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        GlStateManager.disableBlend(); // Clean up after HWYLA
    }

}
