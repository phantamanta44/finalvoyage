package io.github.phantamanta44.finalvoyage.client;

import io.github.phantamanta44.finalvoyage.CommonProxy;
import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.block.BlockNetherOre;
import io.github.phantamanta44.finalvoyage.block.FVBlocks;
import io.github.phantamanta44.finalvoyage.client.event.IntegratedServerStartListener;
import io.github.phantamanta44.finalvoyage.client.event.RenderListener;
import io.github.phantamanta44.finalvoyage.constant.FVConst;
import io.github.phantamanta44.finalvoyage.item.FVItems;
import io.github.phantamanta44.finalvoyage.item.ItemDust;
import io.github.phantamanta44.finalvoyage.item.ItemMekanismOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.LockableBooleanGlState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class ClientProxy extends CommonProxy {

    public static LockableBooleanGlState glStateFog;

    @Override
    public void onPreInit() {
        super.onPreInit();
        glStateFog = new LockableBooleanGlState(2912);
        try {
            Field fogStateField = ReflectionHelper.findField(GlStateManager.class, "fogState", "field_179155_g");
            fogStateField.setAccessible(true);
            Object fogState = fogStateField.get(null);
            Field fogField = ReflectionHelper.findField(fogState.getClass(), "fog", "field_179049_a");
            fogField.setAccessible(true);
            fogField.set(fogState, glStateFog);
        } catch (Exception e) {
            FVMod.LOGGER.warn("GlStateManager hack failed!", e);
        }
    }

    @Override
    public void onInit() {
        super.onInit();
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> tintIndex != 0 ? BlockNetherOre.Type.resolve(state).colour : -1,
                FVBlocks.netherOre);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
                (stack, tintIndex) -> tintIndex != 0 ? BlockNetherOre.Type.resolve(stack).colour : -1,
                FVBlocks.netherOre);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
                (stack, tintIndex) -> ItemMekanismOre.getStage(stack).type.colour,
                FVItems.mekanismOre);
        MinecraftForge.EVENT_BUS.register(new RenderListener());
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
                (stack, tintIndex) -> ItemDust.getType(stack).colour,
                FVItems.dust
        );
    }

    @Override
    public void registerItemModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(
                item, meta, new ModelResourceLocation(FVConst.MOD_ID + ":" + name));
    }

    @Override
    protected void registerServerStartHook() {
        serverStartHooks.add(new IntegratedServerStartListener());
    }

}
