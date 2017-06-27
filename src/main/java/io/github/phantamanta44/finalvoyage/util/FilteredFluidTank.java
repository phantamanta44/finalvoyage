package io.github.phantamanta44.finalvoyage.util;

import io.github.phantamanta44.finalvoyage.constant.NBTConst;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class FilteredFluidTank extends FluidTank {
    
    private final Collection<String> valid;
    private NBTTagCompound backing;

    public FilteredFluidTank(int capacity, Collection<String> valid) {
        super(capacity);
        this.valid = new LinkedList<>(valid);
    }

    public FilteredFluidTank(int capacity, String... valid) {
        this(capacity, Arrays.asList(valid));
    }

    public void setBacking(NBTTagCompound backing) {
        this.backing = backing;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return super.canFillFluidType(fluid) && valid.contains(fluid.getFluid().getName());
    }

    @Override
    public int fillInternal(FluidStack resource, boolean doFill) {
        int filled = super.fillInternal(resource, doFill);
        if (doFill)
            writeToBacking();
        return filled;
    }

    @Override
    public FluidStack drainInternal(FluidStack resource, boolean doDrain) {
        FluidStack drained = super.drainInternal(resource, doDrain);
        if (doDrain)
            writeToBacking();
        return drained;
    }

    @Override
    public FluidStack drainInternal(int maxDrain, boolean doDrain) {
        FluidStack drained = super.drainInternal(maxDrain, doDrain);
        if (doDrain)
            writeToBacking();
        return drained;
    }

    private void writeToBacking() {
        if (backing != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            writeToNBT(nbt);
            backing.setTag(NBTConst.FLUID_TANK, nbt);
        }
    }

}
