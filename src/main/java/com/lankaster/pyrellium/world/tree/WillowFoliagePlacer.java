package com.lankaster.pyrellium.world.tree;

import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class WillowFoliagePlacer extends BlobFoliagePlacer {
    public static final MapCodec<WillowFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> createCodec(instance).and(instance.group(Codec.floatRange(0, 1).fieldOf("chance").forGetter((foliagePlacer) -> foliagePlacer.chance), IntProvider.createValidatingCodec(0, 16).fieldOf("length").forGetter((foliagePlacer) -> foliagePlacer.length), BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter((foliagePlacer) -> foliagePlacer.provider))).apply(instance, WillowFoliagePlacer::new));
    public static final FoliagePlacerType<WillowFoliagePlacer> WILLOW_FOLIAGE_PLACER = Registry.register(Registries.FOLIAGE_PLACER_TYPE, Identifier.of(Pyrellium.MOD_ID, "willow_foliage_placer"), new FoliagePlacerType<>(WillowFoliagePlacer.CODEC));
    private final float chance;
    private final IntProvider length;
    private final BlockStateProvider provider;

    public WillowFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, int i, float chance, IntProvider length, BlockStateProvider provider) {
        super(intProvider, intProvider2, i);
        this.chance = chance;
        this.length = length;
        this.provider = provider;
    }

    protected FoliagePlacerType<?> getType() {
        return WILLOW_FOLIAGE_PLACER;
    }

    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        for(int i = offset; i >= offset - foliageHeight; --i) {
            int j = radius + (i != offset && i != offset - foliageHeight ? 1 : 0);
            this.generateSquare(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
        }

        int i = treeNode.isGiantTrunk() ? 1 : 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.Type.HORIZONTAL) {
            Direction direction2 = direction.rotateYClockwise();
            int j = direction2.getDirection() == Direction.AxisDirection.POSITIVE ? radius + i : radius;
            mutable.set(treeNode.getCenter(), 0, -2, 0).move(direction2, j + 1).move(direction, -radius -1);
            int k = -radius - 1;

            while(k < radius + i) {
                boolean bl = placer.hasPlacedBlock(mutable.move(Direction.UP));
                mutable.move(Direction.DOWN);
                if (bl && generateColumn(world, placer, random, chance, treeNode.getCenter(), mutable)) {
                    generateColumn(world, placer, random, chance, treeNode.getCenter(), mutable);
                }

                ++k;
                mutable.move(direction);
            }
        }
    }

    private boolean generateColumn(TestableWorld world, BlockPlacer placer, Random random, float chance, BlockPos origin, BlockPos.Mutable pos) {
        if (!TreeFeature.canReplace(world, pos) || pos.getManhattanDistance(origin) >= 7) {
            return false;
        } else {
            if (random.nextFloat() < chance) {
                for (int i = 0; i < length.get(random); ++i) {
                    BlockState blockState = provider.get(random, pos);
                    if (blockState.contains(Properties.WATERLOGGED)) {
                        blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
                    }

                    if (TreeFeature.canReplace(world, pos)){
                        placer.placeBlock(pos, blockState);
                        pos.move(Direction.DOWN);
                    }
                }
            }
            return true;
        }
    }

    public static void registerWillowFoliagePlacer() {
    }
}