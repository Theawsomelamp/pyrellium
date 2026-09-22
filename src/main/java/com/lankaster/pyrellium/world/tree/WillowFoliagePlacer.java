package com.lankaster.pyrellium.world.tree;

import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class WillowFoliagePlacer extends BlobFoliagePlacer {
    public static final MapCodec<WillowFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> blobParts(instance).apply(instance, WillowFoliagePlacer::new));
    public static final FoliagePlacerType<WillowFoliagePlacer> WILLOW_FOLIAGE_PLACER = Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "willow_foliage_placer"), new FoliagePlacerType<>(WillowFoliagePlacer.CODEC));


    public WillowFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, int i) {
        super(intProvider, intProvider2, i);
    }

    protected FoliagePlacerType<?> type() {
        return WILLOW_FOLIAGE_PLACER;
    }

    protected void createFoliage(LevelSimulatedReader world, FoliagePlacer.FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        for(int i = offset; i >= offset - foliageHeight; --i) {
            int j = radius + (i != offset && i != offset - foliageHeight ? 1 : 0);
            this.placeLeavesRow(world, placer, random, config, treeNode.pos(), j, i, treeNode.doubleTrunk());
        }
    }

    public static void registerWillowFoliagePlacer() {
    }
}