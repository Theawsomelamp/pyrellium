package com.lankaster.pyrellium.networking;


import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.client.util.BlockOutline;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import com.lankaster.pyrellium.entity.GeodinEntity;
import com.lankaster.pyrellium.entity.ModEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Pyrellium.MOD_ID)
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

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GEODIN.get(), GeodinEntity.createGeodinAttributes().build());
    }

    @SubscribeEvent
    static void setupBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, ModBlocks.BURNING_SIGN.get(), ModBlocks.BURNING_WALL_SIGN.get(), ModBlocks.SHADEROOT_SIGN.get(), ModBlocks.SHADEROOT_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, ModBlocks.BURNING_HANGING_SIGN.get(), ModBlocks.BURNING_WALL_HANGING_SIGN.get(), ModBlocks.SHADEROOT_HANGING_SIGN.get(), ModBlocks.SHADEROOT_WALL_HANGING_SIGN.get());
    }

    public static void passAlong(ServerPlayer player, BlockPos pos) {
        if (!Config.instance().items.opal_spyglass_block_sharing) return;
        PacketDistributor.sendToPlayersTrackingChunk(player.getServer().getLevel(player.level().dimension()), new ChunkPos(pos), new MarkerPayload(pos));
    }
}