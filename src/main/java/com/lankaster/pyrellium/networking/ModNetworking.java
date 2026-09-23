package com.lankaster.pyrellium.networking;


import com.lankaster.pyrellium.client.util.BlockOutline;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(OpalPayload.ID, OpalPayload.CODEC, (payload, context) -> context.enqueueWork(() -> CrystalArrowEntity.opal = payload.opal()));
        registrar.playBidirectional(MarkerPayload.ID, MarkerPayload.CODEC, new DirectionalPayloadHandler<>(
                (payload, context) -> context.enqueueWork(() -> BlockOutline.sharedPos = payload.marker()),
                (payload, context) -> context.enqueueWork(() -> passAlong((ServerPlayer) context.player(), payload.marker()))
        ));
    }

    public static void passAlong(ServerPlayer player, BlockPos pos) {
        if (!Config.instance().items.opal_spyglass_block_sharing) return;
        PacketDistributor.sendToPlayersTrackingChunk(player.getServer().getLevel(player.level().dimension()), new ChunkPos(pos), new MarkerPayload(pos));
    }
}