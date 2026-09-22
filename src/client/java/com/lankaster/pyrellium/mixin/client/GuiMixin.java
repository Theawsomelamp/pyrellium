package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.block.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow protected abstract void renderTextureOverlay(GuiGraphics context, ResourceLocation texture, float opacity);

    @Unique
    private boolean blackIce = false;

    @Unique
    private static final ResourceLocation OPAL_SPYGLASS_SCOPE = ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID,"textures/opal_spyglass_scope.png");

    @Unique
    private static final ResourceLocation BLACK_ICE_OUTLINE = ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID,"textures/black_ice_outline.png");

    @Inject(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V",ordinal = 0))
    public void renderOpalSpyglass(GuiGraphics context, float scale, CallbackInfo ci, @Local(ordinal = 2) int k, @Local(ordinal = 3) int l, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j){
        if (minecraft.player.getUseItem().getItem() == ModItems.OPAL_SPYGLASS) {
            context.blit(OPAL_SPYGLASS_SCOPE, k, l, 0.0F, 0.0F, i, j, i, j);
        }
    }

    @WrapOperation(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderTextureOverlay(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;F)V", ordinal = 1))
    public void renderBlackIceOverlay(Gui instance, GuiGraphics context, ResourceLocation texture, float opacity, Operation<Void> original){
        if (minecraft.level.getChunk(minecraft.player.blockPosition()).getBlockState(minecraft.player.blockPosition().below()).is(ModBlocks.FREEZING_ICE) && minecraft.player.getPercentFrozen() <= 0.03f) {
            blackIce = true;
        }

        if (blackIce && minecraft.player.getPercentFrozen() > 0.01f) {
            renderTextureOverlay(context, BLACK_ICE_OUTLINE, minecraft.player.getPercentFrozen());
        } else {
            blackIce = false;
            original.call(instance, context, texture, minecraft.player.getPercentFrozen());
        }
    }
}