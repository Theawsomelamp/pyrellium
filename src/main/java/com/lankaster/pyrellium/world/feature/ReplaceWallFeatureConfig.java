package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record ReplaceWallFeatureConfig(BlockPredicate target, BlockStateProvider provider, IntProvider radius) implements FeatureConfig {
    public static final Codec<ReplaceWallFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    BlockPredicate.BASE_CODEC.fieldOf("target").forGetter(ReplaceWallFeatureConfig::target),
                    BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter(ReplaceWallFeatureConfig::provider),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("radius").forGetter(ReplaceWallFeatureConfig::radius))
            .apply(instance, ReplaceWallFeatureConfig::new));
}
