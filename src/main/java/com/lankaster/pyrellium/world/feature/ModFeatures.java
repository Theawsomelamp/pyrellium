package com.lankaster.pyrellium.world.feature;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ReplaceBlobsFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class ModFeatures {

    public static final Feature SPIKE = registerFeature("spike", new SpikeFeature(SpikeFeatureConfig.CODEC));
    public static final Feature WALL_GROWTH_BLOCK = registerFeature("wall_growth_block", new WallGrowthBlockFeature(SimpleBlockFeatureConfig.CODEC));
    public static final Feature PILLAR = registerFeature("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static final Feature SQUARE = registerFeature("square", new SquareFeature(SquareFeatureConfig.CODEC));
    public static final Feature REPLACE_WALL_FEATURE = registerFeature("replace_wall", new ReplaceWallFeature(ReplaceBlobsFeatureConfig.CODEC));

    private static Feature registerFeature(String name, Feature feature) {
        return Registry.register(Registries.FEATURE, new Identifier(Pyrellium.MOD_ID, name), feature);
    }

    public static void registerModFeatures() {
    }
}
