package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;


public record SquareFeatureConfig(int width, int height, BlockStateProvider state) implements FeatureConfiguration {
    public static final Codec<SquareFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.intRange(0, 16).fieldOf("width").forGetter(SquareFeatureConfig::width),
                    Codec.intRange(0, 16).fieldOf("height").forGetter(SquareFeatureConfig::height),
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(SquareFeatureConfig::state))
            .apply(instance, SquareFeatureConfig::new));
}