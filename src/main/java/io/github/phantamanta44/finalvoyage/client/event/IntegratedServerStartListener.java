package io.github.phantamanta44.finalvoyage.client.event;

import com.mojang.authlib.GameProfile;
import io.github.phantamanta44.finalvoyage.CommonProxy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.integrated.IntegratedServer;

import java.util.function.Consumer;

public class IntegratedServerStartListener implements Consumer<MinecraftServer> {

    @Override
    public void accept(MinecraftServer server) {
        if (!server.isDedicatedServer())
            server.setPlayerList(new IntegratedPlayerListHack((IntegratedServer)server));
    }

    private final class IntegratedPlayerListHack extends IntegratedPlayerList {

        public IntegratedPlayerListHack(IntegratedServer server) {
            super(server);
        }

        @Override
        public EntityPlayerMP createPlayerForUser(GameProfile profile) {
            return CommonProxy.createPlayerForUser(this, profile);
        }

    }

}
