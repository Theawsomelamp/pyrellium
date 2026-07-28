package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.GeodeLayerConfig;
import net.minecraft.world.gen.feature.GeodeLayerThicknessConfig;

public record CrackFeatureConfig(GeodeLayerConfig geodeLayerConfig, GeodeLayerThicknessConfig geodeLayerThicknessConfig, IntProvider length, IntProvider width, IntProvider depth, boolean haveBuddingBlocks, float buddingChance, float bendChance) implements FeatureConfig {
    public static final Codec<CrackFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    GeodeLayerConfig.CODEC.fieldOf("layers").forGetter(CrackFeatureConfig::geodeLayerConfig),
                    GeodeLayerThicknessConfig.CODEC.fieldOf("layer_thickness").forGetter(CrackFeatureConfig::geodeLayerThicknessConfig),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("length").forGetter(CrackFeatureConfig::length),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("width").forGetter(CrackFeatureConfig::width),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("depth").forGetter(CrackFeatureConfig::depth),
                    Codec.BOOL.fieldOf("have_budding_blocks").forGetter(CrackFeatureConfig::haveBuddingBlocks),
                    Codec.floatRange(0, 1).fieldOf("budding_chance").forGetter(CrackFeatureConfig::buddingChance),
                    Codec.floatRange(0, 1).fieldOf("bend_chance").forGetter(CrackFeatureConfig::bendChance))
            .apply(instance, CrackFeatureConfig::new));
}
