package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.world.ModNoiseSettings;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(LifecycledResourceManagerImpl.class)
public class LifecycledResourceManagerImplMixin {
    @Unique
    @SuppressWarnings("deprecation")
    private static Resource readAndApply(Optional<Resource> resource, ModNoiseSettings data) {

        String result = "";
        if (resource.isEmpty())
            result = data.apply(null);
        else {
            try {
                result = data.apply(new String(resource.get().getInputStream().readAllBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String finalResult = result;
        return new Resource(resource.get().getPack(),
                () -> new CharSequenceInputStream(finalResult, Charset.defaultCharset()));
    }

    @ModifyReturnValue(method = "findResources", at = @At("RETURN"))
    public Map<Identifier, Resource> findConfiguredResources(Map<Identifier, Resource> original, String startingPath, Predicate<Identifier> allowedPathPredicate) {
        List<Identifier> ids = new ArrayList<>(original.keySet());
        for (Identifier id : ids) {
            ModNoiseSettings data = ModNoiseSettings.get(id);
            if (data == null || !data.enabled.get()) continue;
            original.replace(id, readAndApply(Optional.of(original.get(id)), data));

            Pyrellium.LOGGER.info("Adding Pyrellium Noise Settings");
        }

        return original;
    }
}