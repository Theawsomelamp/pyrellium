package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModClientNetworking {

    public static void registerPacketReceivers() {
        receiveOpalArrowPacket();
    }

    public static void receiveOpalArrowPacket() {
        ClientPlayNetworking.registerGlobalReceiver(OpalPayload.ID, ((opalPayload, context) -> {
                context.client().execute(() -> CrystalArrowEntity.opal = opalPayload.opal());
        }));
    }
}
