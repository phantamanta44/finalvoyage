package io.github.phantamanta44.finalvoyage.tile;

import io.github.phantamanta44.finalvoyage.tile.base.TileMod;
import io.github.phantamanta44.finalvoyage.util.Capabilities;
import io.github.phantamanta44.finalvoyage.util.FVEnergy;
import io.github.phantamanta44.finalvoyage.util.TileDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TileInterdictionMatrix extends TileMod implements ITickable {

    private static final Collection<TileInterdictionMatrix> instances = new HashSet<>();
    public static final double INTERDICTION_RANGE_SQ = 32 * 32;
    public static final long INTERDICTION_COST = 4000;

    public static boolean attemptInterdiction(Vec3d pos, World world) {
        synchronized (instances) {
            Collection<TileInterdictionMatrix> matrices = instances.stream()
                    .filter(t -> t.getWorld() == world
                            && t.pos.distanceSq(pos.xCoord, pos.yCoord, pos.zCoord) < INTERDICTION_RANGE_SQ)
                    .collect(Collectors.toList());
            for (TileInterdictionMatrix matrix : matrices) {
                if (matrix.isInvalid()) {
                    instances.remove(matrix);
                    continue;
                }
                IEnergyStorage energy = matrix.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN);
                if (energy.getEnergyStored() >= INTERDICTION_COST) {
                    ((FVEnergy)energy).offsetEnergy(-INTERDICTION_COST);
                    matrix.onInterdict();
                    return true;
                }
            }
        }
        return false;
    }

    private FVEnergy energy;
    private long lastEffectTime;
    private int updateTimer;

    public TileInterdictionMatrix() {
        this.energy = new FVEnergy(80000, 1024, 0);
        synchronized (instances) {
            instances.add(this);
        }
        lastEffectTime = 0;
        updateTimer = 0;
    }

    @Override
    public void update() {
        if (--updateTimer <= 0) {
            if (worldObj.isRemote)
                worldObj.markBlockRangeForRenderUpdate(pos, pos.add(1, 1, 1));
            else
                TileDispatcher.dispatchTEToNearbyPlayers(this);
            updateTimer = 10;
        }
    }

    private void onInterdict() {
        long time = worldObj.getTotalWorldTime();
        if (time - lastEffectTime > 3) {
            worldObj.playEvent(2003, pos, 0);
            lastEffectTime = time;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY
                || capability == Capabilities.TESLA_CONSUMER
                || capability == Capabilities.TESLA_HOLDER
                || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY
                || capability == Capabilities.TESLA_CONSUMER
                || capability == Capabilities.TESLA_HOLDER) {
            return (T)energy;
        } else {
            return super.getCapability(capability, facing);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        energy.readFromNBT(nbt);
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return energy.writeToNBT(super.writeToNBT(nbt));
    }

}
