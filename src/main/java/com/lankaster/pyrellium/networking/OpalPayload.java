package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpalPayload(boolean opal) implements CustomPacketPayload {
    public static final ResourceLocation OPAL = ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal");
    public static final CustomPacketPayload.Type<OpalPayload> ID = new CustomPacketPayload.Type<>(OPAL);
    public static final StreamCodec<RegistryFriendlyByteBuf, OpalPayload> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, OpalPayload::opal, OpalPayload::new);
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
