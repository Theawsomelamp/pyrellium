package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;


public record SpikeFeatureConfig(IntProvider height, BlockStateProvider state) implements FeatureConfig {
    public static final Codec<SpikeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter(SpikeFeatureConfig::height),
                BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter(SpikeFeatureConfig::state))
            .apply(instance, SpikeFeatureConfig::new));
}
