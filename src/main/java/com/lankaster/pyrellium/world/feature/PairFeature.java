package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PairFeature extends Feature<PairFeatureConfig> {
    public PairFeature(Codec<PairFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PairFeatureConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        PairFeatureConfig config = context.config();

        config.firstFeature().value().placeWithBiomeCheck(world, context.chunkGenerator(), random, origin);
        config.secondFeature().value().placeWithBiomeCheck(world, context.chunkGenerator(), random, origin);

        return true;
    }
}