package com.lankaster.pyrellium.networking;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import com.lankaster.pyrellium.util.BlockOutline;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;

public class ModClientNetworking {

    public static void registerPacketReceivers() {
        receiveOpalArrowPacket();
        receiveBlockMarkingPacket();
    }

    public static void receiveOpalArrowPacket() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworkingConstants.OPAL_ARROW_ID, (client, handler, buf, responseSender) -> {
            boolean opal = buf.readBoolean();
            client.execute(() -> CrystalArrowEntity.opal = opal);
        });
    }

    public static void receiveBlockMarkingPacket() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworkingConstants.MARKER_ID, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> BlockOutline.sharedPos = pos);
        });
    }
}
