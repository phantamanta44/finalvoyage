package io.github.phantamanta44.finalvoyage.util;

import io.github.phantamanta44.finalvoyage.constant.NBTConst;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class FVEnergy implements IEnergyStorage, ITeslaConsumer, ITeslaHolder, ITeslaProducer {

    private final long capacity, inputRate, outputRate;
    private long energy;

    public FVEnergy(int capacity) {
        this(capacity, -1, -1);
    }

    public FVEnergy(int capacity, int transferRate) {
        this(capacity, transferRate, transferRate);
    }

    public FVEnergy(int capacity, int inputRate, int outputRate) {
        this.capacity = capacity;
        this.inputRate = inputRate;
        this.outputRate = outputRate;
        this.energy = 0;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    public void offsetEnergy(long offset) {
        this.energy += offset;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.energy = nbt.getLong(NBTConst.ENERGY);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setLong(NBTConst.ENERGY, energy);
        return nbt;
    }

    @Override
    public long getStoredPower() {
        return energy;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    @Override
    public long givePower(long power, boolean simulated) {
        if (!canReceive())
            return 0;
        long toTransfer = Math.min(power, capacity - energy);
        if (inputRate > 0)
            toTransfer = Math.min(toTransfer, inputRate);
        if (!simulated)
            energy += toTransfer;
        return toTransfer;
    }

    @Override
    public long takePower(long power, boolean simulated) {
        if (!canExtract())
            return 0;
        long toTransfer = Math.min(power, energy);
        if (outputRate > 0)
            toTransfer = Math.min(toTransfer, outputRate);
        if (!simulated)
            energy -= toTransfer;
        return toTransfer;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return Long.valueOf(givePower(maxReceive, simulate)).intValue();
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return Long.valueOf(takePower(maxExtract, simulate)).intValue();
    }

    @Override
    public int getEnergyStored() {
        return (int)Math.min(getStoredPower(), Integer.MAX_VALUE);
    }

    @Override
    public int getMaxEnergyStored() {
        return (int)Math.min(getCapacity(), Integer.MAX_VALUE);
    }

    @Override
    public boolean canExtract() {
        return outputRate != 0;
    }

    @Override
    public boolean canReceive() {
        return inputRate != 0;
    }

}

