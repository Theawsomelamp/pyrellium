package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;


public record SpikeFeatureConfig(Direction direction, IntProvider height, IntProvider radius, BlockStateProvider state, BlockStateProvider tip, float chance) implements FeatureConfiguration {
    public static final Codec<SpikeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Direction.CODEC.fieldOf("direction").forGetter(SpikeFeatureConfig::direction),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter(SpikeFeatureConfig::height),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("radius").forGetter(SpikeFeatureConfig::radius),
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(SpikeFeatureConfig::state),
                    BlockStateProvider.CODEC.fieldOf("tip").forGetter(SpikeFeatureConfig::tip),
                    Codec.floatRange(0, 1).fieldOf("chance").forGetter(SpikeFeatureConfig::chance))
            .apply(instance, SpikeFeatureConfig::new));
}