package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ReplaceWallFeatureConfig(BlockStateProvider target, BlockStateProvider provider, IntProvider radius) implements FeatureConfiguration {
    public static final Codec<ReplaceWallFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("target").forGetter(ReplaceWallFeatureConfig::target),
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(ReplaceWallFeatureConfig::provider),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("radius").forGetter(ReplaceWallFeatureConfig::radius))
            .apply(instance, ReplaceWallFeatureConfig::new));
}