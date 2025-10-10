package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.world.ModSurfaceRules;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.world.dimension.DimensionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "createWorlds", at = @At("TAIL"))
    private void injectPyrelliumRules(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        ModSurfaceRules.addModMaterialRules((MinecraftServer) (Object) this, DimensionOptions.NETHER);
    }
}