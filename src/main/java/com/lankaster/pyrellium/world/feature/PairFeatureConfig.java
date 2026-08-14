package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public record PairFeatureConfig(RegistryEntry<PlacedFeature> firstFeature, RegistryEntry<PlacedFeature> secondFeature) implements FeatureConfig {
    public static final Codec<PairFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    PlacedFeature.REGISTRY_CODEC.fieldOf("first_feature").forGetter(PairFeatureConfig::firstFeature),
                    PlacedFeature.REGISTRY_CODEC.fieldOf("second_feature").forGetter(PairFeatureConfig::secondFeature))
            .apply(instance, PairFeatureConfig::new));
}