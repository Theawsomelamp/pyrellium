package com.lankaster.pyrellium.networking;


import com.lankaster.pyrellium.config.Config;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ModServerNetworking {

    public static void registerPacketReceivers() {
        receiveBlockMarkingPacket();
    }

    public static void receiveBlockMarkingPacket() {
        ServerPlayNetworking.registerGlobalReceiver(ModNetworkingConstants.MARKER_ID, (server, player, handler, buf, sender) -> {
            BlockPos pos = buf.readBlockPos();
            server.execute(() -> passAlong(server, player, pos));
        });
    }

    public static void passAlong(MinecraftServer server, ServerPlayerEntity sender, BlockPos pos) {
        if (!Config.instance().items.opal_spyglass_block_sharing) return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        for (ServerPlayerEntity player : PlayerLookup.tracking(server.getWorld(sender.getWorld().getRegistryKey()), pos)) {
            ServerPlayNetworking.send(player, ModNetworkingConstants.MARKER_ID, buf);
        }
    }
}