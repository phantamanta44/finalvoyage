package io.github.phantamanta44.finalvoyage.event;

import com.mojang.authlib.GameProfile;
import io.github.phantamanta44.finalvoyage.CommonProxy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;

import java.util.function.Consumer;

public class DedicatedServerStartListener implements Consumer<MinecraftServer> {

    @Override
    public void accept(MinecraftServer server) {
        if (server.isDedicatedServer())
            server.setPlayerList(new DedicatedPlayerListHack((DedicatedServer)server));
    }

    private final class DedicatedPlayerListHack extends DedicatedPlayerList {

        public DedicatedPlayerListHack(DedicatedServer server) {
            super(server);
        }

        @Override
        public EntityPlayerMP createPlayerForUser(GameProfile profile) {
            return CommonProxy.createPlayerForUser(this, profile);
        }
        
    }

}
