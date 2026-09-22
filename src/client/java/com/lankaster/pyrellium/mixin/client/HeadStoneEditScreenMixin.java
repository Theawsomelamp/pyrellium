package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.block.entity.HeadStoneBlockEntity;
import com.lankaster.pyrellium.gui.HeadStoneEditScreen;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class HeadStoneEditScreenMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Inject(method = "openTextEdit", at = @At(value = "HEAD"), cancellable = true)
    private void openHeadStoneEditScreen(SignBlockEntity sign, boolean front, CallbackInfo ci) {
        if (sign instanceof HeadStoneBlockEntity) {
            this.minecraft.setScreen(new HeadStoneEditScreen(sign, front, this.minecraft.isTextFilteringEnabled()));
            ci.cancel();
        }
    }
}