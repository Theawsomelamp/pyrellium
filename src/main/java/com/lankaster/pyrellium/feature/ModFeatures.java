package com.lankaster.pyrellium.feature;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class ModFeatures {

    public static final Feature SPIKE = registerFeature("spike", new SpikeFeature(SpikeFeatureConfig.CODEC));
    public static final Feature WALL_GROWTH_BLOCK = registerFeature("wall_growth_block", new WallGrowthBlockFeature(SimpleBlockFeatureConfig.CODEC));

    private static Feature registerFeature(String name, Feature feature) {
        return Registry.register(Registries.FEATURE, new Identifier(Pyrellium.MOD_ID, name), feature);
    }

    public static void registerModFeatures() {
    }
}
