package com.lankaster.pyrellium.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import static com.lankaster.pyrellium.entity.CrystalArrowEntity.opal;

public class ModClientNetworking {

    public static void registerPacketReceivers() {
        receiveOpalArrowPacket();
    }

    public static void receiveOpalArrowPacket() {
        ClientPlayNetworking.registerGlobalReceiver(OpalPayload.ID, ((opalPayload, context) -> {
                context.client().execute(() -> opal = opalPayload.opal());
        }));
    }
}
