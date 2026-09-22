package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.data.PyrelliumCustomData;
import com.lankaster.pyrellium.data.PyrelliumReloadListener;
import com.lankaster.pyrellium.data.PyrelliumResourcePack;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resources.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(MultiPackResourceManager.class)
public class MultiPackResourceManagerMixin {
    @Unique
    @SuppressWarnings("deprecation")
    private static Resource readAndApply(Optional<Resource> resource, PyrelliumCustomData data) {

        String result = "";
        if (resource.isEmpty())
            result = data.apply(null);
        else {
            try {
                result = data.apply(new String(resource.get().open().readAllBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String finalResult = result;
        return new Resource(PyrelliumResourcePack.INSTANCE,
                () -> new CharSequenceInputStream(finalResult, Charset.defaultCharset()));
    }

    @ModifyReturnValue(method = "listResources", at = @At("RETURN"))
    public Map<ResourceLocation, Resource> findConfiguredResources(Map<ResourceLocation, Resource> original, String startingPath, Predicate<ResourceLocation> allowedPathPredicate) {
        for (PyrelliumCustomData data : PyrelliumCustomData.INSTANCES) {
            if (data.enabled.get() && data.target.getPath().startsWith(startingPath + "/") && allowedPathPredicate.test(data.target)) {
                if (!original.containsKey(data.target)) {
                    original.put(data.target, readAndApply(Optional.empty(), data));
                }
            }
        }

        List<ResourceLocation> ids = new ArrayList<>(original.keySet());
        for (ResourceLocation id : ids) {
            PyrelliumCustomData data = PyrelliumCustomData.get(id);
            if (data == null || !data.enabled.get()) continue;
            original.replace(id, readAndApply(Optional.of(original.get(id)), data));

            Pyrellium.LOGGER.info("Adding Pyrellium Noise Settings");
        }

        return original;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void reloadConfigs(PackType type, List<PackResources> packs, CallbackInfo ci) {
        PyrelliumReloadListener.INSTANCE.preload((ResourceManager) this);
    }
}