package com.lankaster.pyrellium.networking;


import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ModServerNetworking {

    public static void register() {
        PayloadTypeRegistry.playS2C().register(OpalPayload.ID, OpalPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(MarkerPayload.ID, MarkerPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(MarkerPayload.ID, MarkerPayload.CODEC);
        receiveBlockMarkingPacket();
    }

    public static void receiveBlockMarkingPacket() {
        ServerPlayNetworking.registerGlobalReceiver(MarkerPayload.ID, (markerPayload, context) -> context.server().execute(() -> passAlong(context.server(), context.player(), markerPayload.marker())));
    }

    public static void passAlong(MinecraftServer server, ServerPlayerEntity sender, BlockPos pos) {
        MarkerPayload markerPayload = new MarkerPayload(pos);
        for (ServerPlayerEntity player : PlayerLookup.tracking(server.getWorld(sender.getWorld().getRegistryKey()), pos)) {
            ServerPlayNetworking.send(player, markerPayload);
        }
    }
}