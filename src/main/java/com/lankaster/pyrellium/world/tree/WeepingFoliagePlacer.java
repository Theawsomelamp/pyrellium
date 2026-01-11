package com.lankaster.pyrellium.world.tree;

import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class WeepingFoliagePlacer extends BlobFoliagePlacer {
    public static final Codec<WeepingFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> createCodec(instance).and(instance.group(Codec.floatRange(0, 1).fieldOf("chance").forGetter((foliagePlacer) -> foliagePlacer.chance), IntProvider.createValidatingCodec(0, 16).fieldOf("length").forGetter((foliagePlacer) -> foliagePlacer.length))).apply(instance, WeepingFoliagePlacer::new));
    public static final FoliagePlacerType<WeepingFoliagePlacer> WEEPING_FOLIAGE_PLACER = Registry.register(Registries.FOLIAGE_PLACER_TYPE, Identifier.of(Pyrellium.MOD_ID, "weeping_foliage_placer"), new FoliagePlacerType<>(WeepingFoliagePlacer.CODEC));
    private final float chance;
    private final IntProvider length;

    public WeepingFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, int i, float chance, IntProvider length) {
        super(intProvider, intProvider2, i);
        this.chance = chance;
        this.length = length;
    }

    protected FoliagePlacerType<?> getType() {
        return WEEPING_FOLIAGE_PLACER;
    }

    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        for(int i = offset; i >= offset - foliageHeight; --i) {
            int j = radius + (i != offset && i != offset - foliageHeight ? 1 : 0);
            this.generateSquare(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
        }

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        int j = radius + 1;

        for(int l = -j; l <= j; ++l) {
            for(int k = -j; k <= j; ++k) {
                mutable.set(treeNode.getCenter(), l, -2, k);
                if (!placer.hasPlacedBlock(mutable) && generateColumn(world, placer, random, config, chance, treeNode.getCenter(), mutable)) {
                    generateColumn(world, placer, random, config, chance, treeNode.getCenter(), mutable);
                }
            }
        }
    }

    private boolean generateColumn(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, float chance, BlockPos origin, BlockPos.Mutable pos) {
        if (!TreeFeature.canReplace(world, pos) || pos.getManhattanDistance(origin) >= 7) {
            return false;
        } else {
            if (random.nextFloat() < chance) {
                for (int i = 0; i < length.get(random); ++i) {
                    BlockState blockState = config.foliageProvider.get(random, pos);
                    if (blockState.contains(Properties.WATERLOGGED)) {
                        blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
                    }

                    if (TreeFeature.canReplace(world, pos.down())){
                        placer.placeBlock(pos, blockState);
                        pos.move(Direction.DOWN);
                    }
                }
            }
            return true;
        }
    }

    public static void registerWeepingFoliagePlacer() {
    }
}