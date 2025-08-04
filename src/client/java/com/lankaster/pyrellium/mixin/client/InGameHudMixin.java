package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.block.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique
    private boolean blackIce = false;

    @Unique
    private static final Identifier OPAL_SPYGLASS_SCOPE = Identifier.of(Pyrellium.MOD_ID,"textures/opal_spyglass_scope.png");

    @Unique
    private static final Identifier BLACK_ICE_OUTLINE = Identifier.of(Pyrellium.MOD_ID,"textures/black_ice_outline.png");

    @Inject(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",ordinal = 0))
    public void renderOpalSpyglass(DrawContext context, float scale, CallbackInfo ci, @Local(ordinal = 2) int k, @Local(ordinal = 3) int l, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j){
        if (client.player.getActiveItem().getItem() == ModItems.OPAL_SPYGLASS) {
            context.drawTexture(OPAL_SPYGLASS_SCOPE, k, l, 0.0F, 0.0F, i, j, i, j);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 1))
    public void renderBlackIceOverlay(InGameHud instance, DrawContext context, Identifier texture, float opacity, Operation<Void> original){
        if (client.world.getChunk(client.player.getBlockPos()).getBlockState(client.player.getBlockPos().down()).isOf(ModBlocks.FREEZING_ICE) && client.player.getFreezingScale() <= 0.03f) {
            blackIce = true;
        }

        if (blackIce && client.player.getFreezingScale() > 0.01f) {
            renderOverlay(context, BLACK_ICE_OUTLINE, client.player.getFreezingScale());
        } else {
            blackIce = false;
            original.call(instance, context, texture, client.player.getFreezingScale());
        }
    }
}
