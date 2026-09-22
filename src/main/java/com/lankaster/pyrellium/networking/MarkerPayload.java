package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public record MarkerPayload(BlockPos marker) implements CustomPacketPayload {
    public static final ResourceLocation MARKER = ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "marker");
    public static final Type<MarkerPayload> ID = new Type<>(MARKER);
    public static final StreamCodec<RegistryFriendlyByteBuf, MarkerPayload> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, MarkerPayload::marker, MarkerPayload::new);
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
