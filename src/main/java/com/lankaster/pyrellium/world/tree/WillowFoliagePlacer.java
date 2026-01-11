package com.lankaster.pyrellium.world.tree;

import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class WillowFoliagePlacer extends BlobFoliagePlacer {
    public static final Codec<WillowFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> createCodec(instance).apply(instance, WillowFoliagePlacer::new));
    public static final FoliagePlacerType<WillowFoliagePlacer> WILLOW_FOLIAGE_PLACER = Registry.register(Registries.FOLIAGE_PLACER_TYPE, Identifier.of(Pyrellium.MOD_ID, "willow_foliage_placer"), new FoliagePlacerType<>(WillowFoliagePlacer.CODEC));


    public WillowFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, int i) {
        super(intProvider, intProvider2, i);
    }

    protected FoliagePlacerType<?> getType() {
        return WILLOW_FOLIAGE_PLACER;
    }

    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        for(int i = offset; i >= offset - foliageHeight; --i) {
            int j = radius + (i != offset && i != offset - foliageHeight ? 1 : 0);
            this.generateSquare(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
        }
    }

    public static void registerWillowFoliagePlacer() {
    }
}
