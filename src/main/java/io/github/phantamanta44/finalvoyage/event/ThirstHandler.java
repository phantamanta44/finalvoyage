package io.github.phantamanta44.finalvoyage.event;

import io.github.phantamanta44.finalvoyage.CommonProxy;
import io.github.phantamanta44.finalvoyage.FVMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ThirstHandler {

    private static final float DELTA_EASY = -20F / 144000F;
    private static final float DELTA_NORM = -20F / 72000F;
    private static final float DELTA_HARD = -20F / 36000F;

    private final Map<UUID, ThirstProperties> tracker;

    public ThirstHandler() {
        tracker = new HashMap<>();
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            propertiesOf(event.player).tick();
    }

    @SubscribeEvent
    public void onFinishUse(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof EntityPlayer && !event.getEntity().worldObj.isRemote) {
            float refilled = FVMod.PROXY.getThirstHealed(event.getItem());
            if (refilled != 0)
                propertiesOf((EntityPlayer)event.getEntity()).offset(refilled);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer)
            propertiesOf((EntityPlayer)event.getEntity()).set(20F);
    }

    public ThirstProperties propertiesOf(EntityPlayer player) {
        return tracker.computeIfAbsent(player.getGameProfile().getId(), id -> new ThirstProperties(player));
    }

    public static class ThirstProperties {

        private final EntityPlayer player;
        private double prevPosX, prevPosZ;
        private float water;
        private int damageTimer;

        public ThirstProperties(EntityPlayer player) {
            this.player = player;
            this.prevPosX = player.posX;
            this.prevPosZ = player.posZ;
            this.water = 20F;
            this.damageTimer = 0;
        }

        public void tick() {
            if (player.isCreative())
                return;
            EnumDifficulty diff = player.worldObj.getDifficulty();
            if (diff == EnumDifficulty.PEACEFUL)
                return;
            double deltaX = player.posX - prevPosX, deltaZ = player.posZ - prevPosZ;
            if (deltaX * deltaX + deltaZ * deltaZ >= 0.03) {
                switch (diff) {
                    case EASY:
                        offset(DELTA_EASY);
                        break;
                    case NORMAL:
                        offset(DELTA_NORM);
                        break;
                    case HARD:
                        offset(DELTA_HARD);
                        break;
                }
            }
            prevPosX = player.posX;
            prevPosZ = player.posZ;
            if (water < 2)
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 70, 0, true, false));
            if (water < 1) {
                if (--damageTimer <= 0) {
                    player.attackEntityFrom(CommonProxy.dmgThirst, (1F - water) * 4F);
                    damageTimer = 30;
                }
            } else {
                damageTimer = 0;
            }
        }

        public void set(float amount) {
            water = MathHelper.clamp_float(amount, 0, 20);
        }

        public void offset(float amount) {
            set(water + amount);
        }

        public float getThirst() {
            return water;
        }

    }

}
