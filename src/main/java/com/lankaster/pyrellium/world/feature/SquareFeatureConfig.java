package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;


public record SquareFeatureConfig(int width, int height, BlockStateProvider state) implements FeatureConfig {
    public static final Codec<SquareFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.intRange(0, 16).fieldOf("width").forGetter(SquareFeatureConfig::width),
                Codec.intRange(0, 16).fieldOf("height").forGetter(SquareFeatureConfig::height),
                BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter(SquareFeatureConfig::state))
            .apply(instance, SquareFeatureConfig::new));
}
