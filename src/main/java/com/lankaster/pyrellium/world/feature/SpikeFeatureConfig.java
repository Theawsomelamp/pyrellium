package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;


public record SpikeFeatureConfig(IntProvider height, BlockStateProvider state, BlockStateProvider tip, float chance) implements FeatureConfig {
    public static final Codec<SpikeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter(SpikeFeatureConfig::height),
                BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter(SpikeFeatureConfig::state),
                    BlockStateProvider.TYPE_CODEC.fieldOf("tip").forGetter(SpikeFeatureConfig::tip),
                    Codec.floatRange(0, 1).fieldOf("chance").forGetter(SpikeFeatureConfig::chance))
            .apply(instance, SpikeFeatureConfig::new));
}
