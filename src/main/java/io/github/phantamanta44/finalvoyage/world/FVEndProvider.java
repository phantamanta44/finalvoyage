package io.github.phantamanta44.finalvoyage.world;

import com.google.common.base.Supplier;
import io.github.phantamanta44.finalvoyage.FVMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class FVEndProvider extends WorldProvider {

    public static Supplier<WorldProvider> factory;

    private final WorldProvider backing;

    public FVEndProvider() {
        this.backing = factory.get();
        FVMod.LOGGER.info("End provider wrapping " + backing.getClass().getName());
    }

    @Override
    public void createBiomeProvider() {
        backing.registerWorld(worldObj);
        ((WorldProviderEnd)backing).createBiomeProvider();
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return backing.createChunkGenerator();
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
        return -1;
    }

    @Override
    public ICapabilityProvider initCapabilities() {
        return backing.initCapabilities();
    }

    @Override
    public Biome getBiomeForCoords(BlockPos pos) {
        return backing.getBiomeForCoords(pos);
    }

    @Override
    public boolean isDaytime() {
        return backing.isDaytime();
    }

    @Override
    public float getSunBrightnessFactor(float par1) {
        return backing.getSunBrightnessFactor(par1);
    }

    @Override
    public float getCurrentMoonPhaseFactor() {
        return backing.getCurrentMoonPhaseFactor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return backing.getSkyColor(cameraEntity, partialTicks);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getCloudColor(float partialTicks) {
        return backing.getCloudColor(partialTicks);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
        return backing.getSunBrightness(par1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        return backing.getStarBrightness(par1);
    }

    @Override
    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
        backing.setAllowedSpawnTypes(allowHostile, allowPeaceful);
    }

    @Override
    public void calculateInitialWeather() {
        backing.calculateInitialWeather();
    }

    @Override
    public void updateWeather() {
        backing.updateWeather();
    }

    @Override
    public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
        return backing.canBlockFreeze(pos, byWater);
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight) {
        return backing.canSnowAt(pos, checkLight);
    }

    @Override
    public void setWorldTime(long time) {
        backing.setWorldTime(time);
    }

    @Override
    public long getSeed() {
        return backing.getSeed();
    }

    @Override
    public long getWorldTime() {
        return backing.getWorldTime();
    }

    @Override
    public BlockPos getSpawnPoint() {
        return backing.getSpawnPoint();
    }

    @Override
    public void setSpawnPoint(BlockPos pos) {
        backing.setSpawnPoint(pos);
    }

    @Override
    public boolean canMineBlock(EntityPlayer player, BlockPos pos) {
        return backing.canMineBlock(player, pos);
    }

    @Override
    public boolean isBlockHighHumidity(BlockPos pos) {
        return backing.isBlockHighHumidity(pos);
    }

    @Override
    public int getHeight() {
        return backing.getHeight();
    }

    @Override
    public int getActualHeight() {
        return backing.getActualHeight();
    }

    @Override
    public double getHorizon() {
        return backing.getHorizon();
    }

    @Override
    public void resetRainAndThunder() {
        backing.resetRainAndThunder();
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return backing.canDoLightning(chunk);
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return backing.canDoRainSnowIce(chunk);
    }

    @Override
    public void onPlayerAdded(EntityPlayerMP player) {
        backing.onPlayerAdded(player);
    }

    @Override
    public void onPlayerRemoved(EntityPlayerMP player) {
        backing.onPlayerRemoved(player);
    }

    @Override
    public DimensionType getDimensionType() {
        return backing.getDimensionType();
    }

    @Override
    public void onWorldSave() {
        backing.onWorldSave();
    }

    @Override
    public void onWorldUpdateEntities() {
        backing.onWorldUpdateEntities();
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return backing.canCoordinateBeSpawn(x, z);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return backing.calculateCelestialAngle(worldTime, partialTicks);
    }

    @Override
    public int getMoonPhase(long worldTime) {
        return backing.getMoonPhase(worldTime);
    }

    @Override
    public boolean isSurfaceWorld() {
        return backing.isSurfaceWorld();
    }

    @Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return backing.calcSunriseSunsetColors(celestialAngle, partialTicks);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return backing.getFogColor(p_76562_1_, p_76562_2_);
    }

    @Override
    public boolean canRespawnHere() {
        return backing.canRespawnHere();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return backing.getCloudHeight();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return backing.isSkyColored();
    }

    @Override
    public BlockPos getSpawnCoordinate() {
        return backing.getSpawnCoordinate();
    }

    @Override
    public int getAverageGroundLevel() {
        return backing.getAverageGroundLevel();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
        return backing.getVoidFogYFactor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return backing.doesXZShowFog(x, z);
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return backing.getBiomeProvider();
    }

    @Override
    public boolean doesWaterVaporize() {
        return backing.doesWaterVaporize();
    }

    @Override
    public boolean getHasNoSky() {
        return backing.getHasNoSky();
    }

    @Override
    public float[] getLightBrightnessTable() {
        return backing.getLightBrightnessTable();
    }

    @Override
    public WorldBorder createWorldBorder() {
        return backing.createWorldBorder();
    }

    @Override
    public void setDimension(int dim) {
        backing.setDimension(dim);
    }

    @Override
    public int getDimension() {
        return backing.getDimension();
    }

    @Override
    public String getSaveFolder() {
        return backing.getSaveFolder();
    }

    @Override
    public String getWelcomeMessage() {
        return backing.getWelcomeMessage();
    }

    @Override
    public String getDepartMessage() {
        return backing.getDepartMessage();
    }

    @Override
    public double getMovementFactor() {
        return backing.getMovementFactor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        return backing.getSkyRenderer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setSkyRenderer(IRenderHandler skyRenderer) {
        backing.setSkyRenderer(skyRenderer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer() {
        return backing.getCloudRenderer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setCloudRenderer(IRenderHandler renderer) {
        backing.setCloudRenderer(renderer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getWeatherRenderer() {
        return backing.getWeatherRenderer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setWeatherRenderer(IRenderHandler renderer) {
        backing.setWeatherRenderer(renderer);
    }

    @Override
    public BlockPos getRandomizedSpawnPoint() {
        return backing.getRandomizedSpawnPoint();
    }

    @Override
    public boolean shouldMapSpin(String entity, double x, double y, double z) {
        return backing.shouldMapSpin(entity, x, y, z);
    }

}
