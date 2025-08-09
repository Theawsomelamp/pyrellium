package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.block.entity.HeadStoneBlockEntity;
import com.lankaster.pyrellium.gui.HeadStoneEditScreen;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class HeadStoneEditScreenMixin {
    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "openEditSignScreen", at = @At(value = "HEAD"), cancellable = true)
    private void openHeadStoneEditScreen(SignBlockEntity sign, boolean front, CallbackInfo ci) {
        if (sign instanceof HeadStoneBlockEntity) {
            this.client.setScreen(new HeadStoneEditScreen(sign, front, this.client.shouldFilterText()));
            ci.cancel();
        }
    }
}
