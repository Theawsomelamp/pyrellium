package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.ConfigHandler;
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
    private static Resource readAndApply(Optional<Resource> resource, ModNoiseSettings data) {
        Pyrellium.LOGGER.info("Adding Pyrellium Noise Settings");

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
        ModNoiseSettings data = new ModNoiseSettings(Identifier.of("minecraft", "worldgen/noise_settings/nether.json"), () -> ConfigHandler.getConfig().globalFeatureConfig().doIncreasedHeight(), ModNoiseSettings::changeNoiseRouter);

        List<Identifier> ids = new ArrayList<>(original.keySet());
        for (Identifier id : ids) {
            if (!data.target.equals(id) || !data.enabled.get()) continue;
            original.replace(id, readAndApply(Optional.of(original.get(id)), data));
        }

        return original;
    }
}
