package com.lankaster.pyrellium.world.feature;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Pyrellium.MOD_ID);

    public static final Supplier<Feature<?>> SPIKE = registerFeature("spike", new SpikeFeature(SpikeFeatureConfig.CODEC));
    public static final Supplier<Feature<?>> WALL_GROWTH_BLOCK = registerFeature("wall_growth_block", new WallGrowthBlockFeature(SimpleBlockConfiguration.CODEC));
    public static final Supplier<Feature<?>> PILLAR = registerFeature("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static final Supplier<Feature<?>> SQUARE = registerFeature("square", new SquareFeature(SquareFeatureConfig.CODEC));
    public static final Supplier<Feature<?>> REPLACE_WALL_FEATURE = registerFeature("replace_wall", new ReplaceWallFeature(ReplaceWallFeatureConfig.CODEC));
    public static final Supplier<Feature<?>> CRACK = registerFeature("crack", new CrackFeature(CrackFeatureConfig.CODEC));
    public static final Supplier<Feature<?>> PAIR = registerFeature("pair", new PairFeature(PairFeatureConfig.CODEC));

    private static Supplier<Feature<?>> registerFeature(String name, Feature<?> feature) {
        return FEATURES.register(name, () -> feature);
    }

    public static void registerModFeatures(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
