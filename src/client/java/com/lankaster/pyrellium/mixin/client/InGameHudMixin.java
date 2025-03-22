package com.lankaster.pyrellium.mixin.client;

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
public class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Unique
    private static final Identifier OPAL_SPYGLASS_SCOPE = Identifier.of(Pyrellium.MOD_ID,"textures/opal_spyglass_scope.png");

    @Inject(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",ordinal = 0))
    public void renderOpalSpyglass(DrawContext context, float scale, CallbackInfo ci, @Local(ordinal = 2) int k, @Local(ordinal = 3) int l, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j){
        if (client.player.getActiveItem().getItem() == ModItems.OPAL_SPYGLASS) {
            context.drawTexture(OPAL_SPYGLASS_SCOPE, k, l, 0.0F, 0.0F, i, j, i, j);
        }
    }
}
