package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PairFeature extends Feature<PairFeatureConfig> {
    public PairFeature(Codec<PairFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<PairFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        PairFeatureConfig config = context.getConfig();

        config.firstFeature().value().generate(world, context.getGenerator(), random, origin);
        config.secondFeature().value().generate(world, context.getGenerator(), random, origin);

        return true;
    }
}
