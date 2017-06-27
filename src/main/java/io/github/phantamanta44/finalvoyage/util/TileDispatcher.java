package io.github.phantamanta44.finalvoyage.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class TileDispatcher {

    private static final double TILE_DISPATCH_RADIUS = 64 * 64;

    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        tile.getWorld().playerEntities.stream()
                .filter(p -> p.getPosition().distanceSq(tile.getPos()) < TILE_DISPATCH_RADIUS)
                .forEach(p -> ((EntityPlayerMP)p).connection.sendPacket(tile.getUpdatePacket()));
    }

}