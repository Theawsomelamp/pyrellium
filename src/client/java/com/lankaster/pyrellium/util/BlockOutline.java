package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class BlockOutline {
    public static BlockPos savedPos;

    public static void renderBoxOverlay(WorldRenderContext context, BlockPos blockPos){
        if (blockPos == null) {
            return;
        }
        Camera camera = context.camera();

        MatrixStack matrixStack = context.matrixStack();

        VertexConsumer vertexConsumer = Objects.requireNonNull(context.consumers()).getBuffer(RenderLayer.LINES);

        double x = blockPos.getX() - camera.getPos().x;
        double y = blockPos.getY() - camera.getPos().y;
        double z = blockPos.getZ() - camera.getPos().z;

        WorldRenderer.drawBox(matrixStack, vertexConsumer, x, y, z, x + 1, y + 1, z + 1, 0.76f, 0.85f, 0.98f, 1.0f, 0.76f, 0.85f, 0.98f);
    }

    public static BlockPos raycast() {
        MinecraftClient client = MinecraftClient.getInstance();
        double maxReach = 1000; //The farthest target the cameraEntity can detect
        float tickDelta = 1.0F; //Used for tracking animation progress; no tracking is 1.0F
        boolean includeFluids = true; //Whether to detect fluids as blocks

        if (client.player.getActiveItem().getItem()== ModItems.OPAL_SPYGLASS) {
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
        if (client.options.attackKey.isPressed()) {
            savedPos = raycast();
        } else if (savedPos != null && client.world.isChunkLoaded(savedPos.getX(), savedPos.getZ())) {
            return savedPos;
        }
        return null;
    }

    public static void renderBoxOverlay(WorldRenderContext worldRenderContext) {
        renderBoxOverlay(worldRenderContext, raycast());
        renderBoxOverlay(worldRenderContext, saveBlock());
    }
}
