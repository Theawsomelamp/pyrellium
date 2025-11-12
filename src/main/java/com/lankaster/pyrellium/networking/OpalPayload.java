package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpalPayload(boolean opal) implements CustomPayload {
    public static final Identifier OPAL = Identifier.of(Pyrellium.MOD_ID, "opal");
    public static final CustomPayload.Id<OpalPayload> ID = new CustomPayload.Id<>(OPAL);
    public static final PacketCodec<RegistryByteBuf, OpalPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, OpalPayload::opal, OpalPayload::new);
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
