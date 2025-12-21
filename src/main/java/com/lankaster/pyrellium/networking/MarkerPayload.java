package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record MarkerPayload(BlockPos marker) implements CustomPayload {
    public static final Identifier MARKER = Identifier.of(Pyrellium.MOD_ID, "marker");
    public static final Id<MarkerPayload> ID = new Id<>(MARKER);
    public static final PacketCodec<RegistryByteBuf, MarkerPayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, MarkerPayload::marker, MarkerPayload::new);
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
