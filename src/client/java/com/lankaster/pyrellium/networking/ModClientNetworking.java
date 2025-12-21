package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import com.lankaster.pyrellium.util.BlockOutline;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModClientNetworking {

    public static void registerPacketReceivers() {
        receiveOpalArrowPacket();
        receiveBlockMarkingPacket();
    }

    public static void receiveOpalArrowPacket() {
        ClientPlayNetworking.registerGlobalReceiver(OpalPayload.ID, ((opalPayload, context) -> context.client().execute(() -> CrystalArrowEntity.opal = opalPayload.opal())));
    }

    public static void receiveBlockMarkingPacket() {
        ClientPlayNetworking.registerGlobalReceiver(MarkerPayload.ID, (markerPayload, context) -> context.client().execute(() -> BlockOutline.sharedPos = markerPayload.marker()));
    }
}
