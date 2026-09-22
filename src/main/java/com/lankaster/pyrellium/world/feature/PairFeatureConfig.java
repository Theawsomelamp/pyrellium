package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record PairFeatureConfig(Holder<PlacedFeature> firstFeature, Holder<PlacedFeature> secondFeature) implements FeatureConfiguration {
    public static final Codec<PairFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    PlacedFeature.CODEC.fieldOf("first_feature").forGetter(PairFeatureConfig::firstFeature),
                    PlacedFeature.CODEC.fieldOf("second_feature").forGetter(PairFeatureConfig::secondFeature))
            .apply(instance, PairFeatureConfig::new));
}