package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModClientNetworking {

    public static void registerPacketReceivers() {
        receiveOpalArrowPacket();
    }

    public static void receiveOpalArrowPacket() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworkingConstants.OPAL_ARROW_ID, (client, handler, buf, responseSender) -> {
            boolean opal = buf.readBoolean();
            client.execute(() -> CrystalArrowEntity.opal = opal);
        });
    }
}
