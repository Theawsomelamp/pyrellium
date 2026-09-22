package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.MarkerPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.BlockPos;

import java.util.Objects;

public class BlockOutline {
    public static BlockPos savedPos;
    public static BlockPos sharedPos;

    public static void renderBoxOverlay(WorldRenderContext context, BlockPos blockPos, float red, float green, float blue){
        if (blockPos == null) {
            return;
        }
        Camera camera = context.camera();

        PoseStack matrixStack = context.matrixStack();

        VertexConsumer vertexConsumer = Objects.requireNonNull(context.consumers()).getBuffer(RenderType.LINES);

        double x = blockPos.getX() - camera.getPosition().x;
        double y = blockPos.getY() - camera.getPosition().y;
        double z = blockPos.getZ() - camera.getPosition().z;

        LevelRenderer.renderLineBox(matrixStack, vertexConsumer, x, y, z, x + 1, y + 1, z + 1, red, green, blue, 1.0f, red, green, blue);
    }

    public static BlockPos raycast() {
        Minecraft client = Minecraft.getInstance();
        double maxReach = 1000; //The farthest target the cameraEntity can detect
        float tickDelta = 1.0F; //Used for tracking animation progress; no tracking is 1.0F
        boolean includeFluids = true; //Whether to detect fluids as block

        if (client.player.getUseItem().getItem() == ModItems.OPAL_SPYGLASS) {
            HitResult hit = client.cameraEntity.pick(maxReach, tickDelta, includeFluids);

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
        Minecraft client = Minecraft.getInstance();

        if (client.player.getUseItem().getItem() == ModItems.OPAL_SPYGLASS) {
            if (client.options.keyPickItem.consumeClick() && savedPos != null) {
                MarkerPayload markerPayload = new MarkerPayload(savedPos);
                client.level.playLocalSound(client.player.position().x, client.player.position().y, client.player.position().z, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.PLAYERS, 1.0f, 1.0f, true);
                ClientPlayNetworking.send(markerPayload);
            } else if (client.options.keyAttack.consumeClick()) {
                savedPos = (client.options.keyShift.isDown() ? null : raycast());
                sharedPos = (client.options.keyShift.isDown() ? null : sharedPos);
            }
        }

        if (savedPos != null && client.level.hasChunk(savedPos.getX(), savedPos.getZ())) {
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
