package com.lankaster.pyrellium.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldGenRegion.class)
public class WarningSuppressor {
    @Inject(method = "ensureCanWrite", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;logAndPauseIfInIde(Ljava/lang/String;)V", ordinal = 0), cancellable = true)
    private void isValidForSetBlock(BlockPos pos, CallbackInfoReturnable<Boolean> setBlockError) {
        setBlockError.setReturnValue(false);
    }
}
