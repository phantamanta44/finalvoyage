package io.github.phantamanta44.finalvoyage.event;

import io.github.phantamanta44.finalvoyage.tile.TileInterdictionMatrix;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.EnumSet;

public class WorldListener {

    private static final EnumSet<DecorateBiomeEvent.Decorate.EventType> ILLEAL_DECORATION = EnumSet.of(
            DecorateBiomeEvent.Decorate.EventType.FLOWERS, DecorateBiomeEvent.Decorate.EventType.GRASS,
            DecorateBiomeEvent.Decorate.EventType.TREE, DecorateBiomeEvent.Decorate.EventType.LAKE_WATER,
            DecorateBiomeEvent.Decorate.EventType.ICE, DecorateBiomeEvent.Decorate.EventType.LILYPAD);

    @SubscribeEvent
    public void onMobSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (!event.getWorld().isRemote && event.getEntity().isCreatureType(EnumCreatureType.MONSTER, false)) {
            if (event.getWorld().provider.getDimension() == 0) {
                if (event.getWorld().getDifficulty() != EnumDifficulty.PEACEFUL && ((EntityLiving)event.getEntity()).isNotColliding()
                        && event.getWorld().getBlockState(new BlockPos(event.getEntity()).down()).canEntitySpawn(event.getEntity())) {
                    if (TileInterdictionMatrix.attemptInterdiction(event.getEntity().getPositionVector(), event.getWorld()))
                        event.setResult(Event.Result.DENY);
                    else
                        event.setResult(Event.Result.ALLOW);
                }
            } else if (TileInterdictionMatrix.attemptInterdiction(event.getEntity().getPositionVector(), event.getWorld())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onDecorate(DecorateBiomeEvent.Decorate event) {
        if (ILLEAL_DECORATION.contains(event.getType()))
            event.setResult(Event.Result.DENY);
    }

}
