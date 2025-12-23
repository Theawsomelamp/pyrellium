package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.ModNetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class BlockOutline {
    public static BlockPos savedPos;
    public static BlockPos sharedPos;

    public static void renderBoxOverlay(WorldRenderContext context, BlockPos blockPos, float red, float green, float blue){
        if (blockPos == null) {
            return;
        }
        Camera camera = context.camera();

        MatrixStack matrixStack = context.matrixStack();

        VertexConsumer vertexConsumer = Objects.requireNonNull(context.consumers()).getBuffer(RenderLayer.LINES);

        double x = blockPos.getX() - camera.getPos().x;
        double y = blockPos.getY() - camera.getPos().y;
        double z = blockPos.getZ() - camera.getPos().z;

        WorldRenderer.drawBox(matrixStack, vertexConsumer, x, y, z, x + 1, y + 1, z + 1, red, green, blue, 1.0f, red, green, blue);
    }

    public static BlockPos raycast() {
        MinecraftClient client = MinecraftClient.getInstance();
        double maxReach = 1000; //The farthest target the cameraEntity can detect
        float tickDelta = 1.0F; //Used for tracking animation progress; no tracking is 1.0F
        boolean includeFluids = true; //Whether to detect fluids as blocks

        if (client.player.getActiveItem().getItem() == ModItems.OPAL_SPYGLASS) {
            HitResult hit = client.cameraEntity.raycast(maxReach, tickDelta, includeFluids);

            return switch (hit.getType()) {
                case MISS, ENTITY -> null;
                case BLOCK -> {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    yield blockHit.getBlockPos();
                }
            };
        }
        return null;
    }

    public static BlockPos saveBlock() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player.getActiveItem().getItem() == ModItems.OPAL_SPYGLASS) {
            if (client.options.pickItemKey.wasPressed() && savedPos != null) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(savedPos);
                client.world.playSound(client.player.getPos().x, client.player.getPos().y, client.player.getPos().z, SoundEvents.BLOCK_AMETHYST_BLOCK_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                ClientPlayNetworking.send(ModNetworkingConstants.MARKER_ID, buf);
            } else if (client.options.attackKey.wasPressed() && client.player.getActiveItem().getItem() == ModItems.OPAL_SPYGLASS) {
                savedPos = (client.options.sneakKey.isPressed() ? null : raycast());
                sharedPos = (client.options.sneakKey.isPressed() ? null : sharedPos);
            }
        }

        if (savedPos != null && client.world.isChunkLoaded(savedPos.getX(), savedPos.getZ())) {
            return savedPos;
        }
        return null;
    }

    public static void renderBoxOverlay(WorldRenderContext worldRenderContext) {
        renderBoxOverlay(worldRenderContext, raycast(), 0.76f, 0.85f, 0.98f);
        renderBoxOverlay(worldRenderContext, saveBlock(), 0.76f, 0.85f, 0.98f);
        renderBoxOverlay(worldRenderContext, sharedPos, 0.60f, 0.36f, 0.78f);
    }
}
