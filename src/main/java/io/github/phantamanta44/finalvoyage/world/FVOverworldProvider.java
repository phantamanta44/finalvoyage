package io.github.phantamanta44.finalvoyage.world;

import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.integration.IntMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderOverworld;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class FVOverworldProvider extends WorldProviderSurface {

    @Override
    public void createBiomeProvider() {
        biomeProvider = worldObj.getWorldType().getBiomeProvider(worldObj);
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        IChunkGenerator gen = super.createChunkGenerator();
        if (gen instanceof ChunkProviderOverworld) {
            try {
                Field oceanBlockField = ReflectionHelper.findField(gen.getClass(), "oceanBlock", "field_186001_t");
                oceanBlockField.setAccessible(true);
                oceanBlockField.set(gen, Block.REGISTRY.getObject(new ResourceLocation(IntMod.TCON.modId, "blood")).getDefaultState());
            } catch (Exception e) {
                FVMod.LOGGER.error("ChunkProviderOverworld hack failed!", e);
            }
        }
        return gen;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return false;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean getHasNoSky() {
        return true;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public float getSunBrightness(float partialTick) {
        return 0.2F;
    }

    @Override
    public float getSunBrightnessFactor(float partialTick) {
        return 0.2F;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return Vec3d.ZERO;
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
        return -1;
    }

}
