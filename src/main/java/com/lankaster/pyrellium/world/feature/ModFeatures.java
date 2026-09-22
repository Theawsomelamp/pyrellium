package com.lankaster.pyrellium.world.feature;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ModFeatures {

    public static final Feature SPIKE = registerFeature("spike", new SpikeFeature(SpikeFeatureConfig.CODEC));
    public static final Feature WALL_GROWTH_BLOCK = registerFeature("wall_growth_block", new WallGrowthBlockFeature(SimpleBlockConfiguration.CODEC));
    public static final Feature PILLAR = registerFeature("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static final Feature SQUARE = registerFeature("square", new SquareFeature(SquareFeatureConfig.CODEC));
    public static final Feature REPLACE_WALL_FEATURE = registerFeature("replace_wall", new ReplaceWallFeature(ReplaceWallFeatureConfig.CODEC));
    public static final Feature CRACK = registerFeature("crack", new CrackFeature(CrackFeatureConfig.CODEC));
    public static final Feature PAIR = registerFeature("pair", new PairFeature(PairFeatureConfig.CODEC));

    private static Feature registerFeature(String name, Feature feature) {
        return Registry.register(BuiltInRegistries.FEATURE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, name), feature);
    }

    public static void registerModFeatures() {
    }
}
