package io.github.phantamanta44.finalvoyage.item;

import gnu.trove.impl.Constants;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.github.phantamanta44.finalvoyage.FVMod;
import io.github.phantamanta44.finalvoyage.constant.LangConst;
import io.github.phantamanta44.finalvoyage.constant.NBTConst;
import io.github.phantamanta44.finalvoyage.item.base.IThirstMutator;
import io.github.phantamanta44.finalvoyage.item.base.ItemMod;
import io.github.phantamanta44.finalvoyage.util.FilteredFluidTank;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemWaterBottle extends ItemMod implements IThirstMutator {

    public static final int CAPACITY = 8000;
    private static final TObjectIntMap<String> VALID_FLUIDS;

    static {
        VALID_FLUIDS = new TObjectIntHashMap<>(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0);
        VALID_FLUIDS.put("water", 4);
        VALID_FLUIDS.put("milk", 7);
        VALID_FLUIDS.put("blood", 6);
        VALID_FLUIDS.put("blueslime", 3);
        VALID_FLUIDS.put("purpleslime", 3);
        VALID_FLUIDS.put("brine", 1);
        VALID_FLUIDS.put("syrup", 3);
    }

    public ItemWaterBottle() {
        super(LangConst.ITEM_WATER_BOTTLE_NAME);
        FVMod.PROXY.registerItemModel(this, 0, "water_bottle");
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        IFluidTank tank = tankFor(stack);
        if (tank.getFluidAmount() <= 0) {
            tooltip.add(TextFormatting.GRAY + LangConst.get(LangConst.TT_EMPTY));
        } else {
            tooltip.add(TextFormatting.AQUA + tank.getFluid().getLocalizedName() + ": "
                    + TextFormatting.GRAY + Integer.toString(tank.getFluidAmount()) + "mB");
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        IFluidTank tank = tankFor(stack);
        stack.getTagCompound().setInteger(NBTConst.CACHED_THIRST, thristFor(tank));
        tank.drain(1000, true);
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (tankFor(stack).getFluidAmount() >= 1000 && FVMod.PROXY.getThirst(player) < 20F && !player.capabilities.disableDamage) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }
    }

    @Override
    public float getThirstOffset(ItemStack stack) {
        return stack.getTagCompound().getInteger(NBTConst.CACHED_THIRST);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new ICapabilityProvider() {
            @Override
            public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
            }

            @Override
            @SuppressWarnings("unchecked")
            public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T)tankFor(stack) : null;
            }
        };
    }

    private static FluidTank tankFor(ItemStack stack) {
        FilteredFluidTank tank = new FilteredFluidTank(CAPACITY, VALID_FLUIDS.keySet());
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            stack.setTagCompound(nbt = new NBTTagCompound());
        if (!nbt.hasKey(NBTConst.FLUID_TANK)) {
            nbt.setTag(NBTConst.FLUID_TANK, new NBTTagCompound());
            tank.writeToNBT(nbt.getCompoundTag(NBTConst.FLUID_TANK));
        } else {
            tank.readFromNBT(nbt.getCompoundTag(NBTConst.FLUID_TANK));
        }
        tank.setBacking(nbt);
        return tank;
    }

    private static int thristFor(IFluidTank tank) {
        return VALID_FLUIDS.get(tank.getFluid().getFluid().getName());
    }

}
