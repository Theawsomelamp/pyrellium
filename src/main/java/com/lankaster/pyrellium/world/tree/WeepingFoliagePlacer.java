package com.lankaster.pyrellium.world.tree;

import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class WeepingFoliagePlacer extends BlobFoliagePlacer {
    public static final MapCodec<WeepingFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> blobParts(instance).and(instance.group(Codec.floatRange(0, 1).fieldOf("chance").forGetter((foliagePlacer) -> foliagePlacer.chance), IntProvider.codec(0, 16).fieldOf("length").forGetter((foliagePlacer) -> foliagePlacer.length))).apply(instance, WeepingFoliagePlacer::new));
    public static final FoliagePlacerType<WeepingFoliagePlacer> WEEPING_FOLIAGE_PLACER = Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "weeping_foliage_placer"), new FoliagePlacerType<>(WeepingFoliagePlacer.CODEC));
    private final float chance;
    private final IntProvider length;

    public WeepingFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, int i, float chance, IntProvider length) {
        super(intProvider, intProvider2, i);
        this.chance = chance;
        this.length = length;
    }

    protected FoliagePlacerType<?> type() {
        return WEEPING_FOLIAGE_PLACER;
    }

    protected void createFoliage(LevelSimulatedReader world, FoliagePlacer.FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        for(int i = offset; i >= offset - foliageHeight; --i) {
            int j = radius + (i != offset && i != offset - foliageHeight ? 1 : 0);
            this.placeLeavesRow(world, placer, random, config, treeNode.pos(), j, i, treeNode.doubleTrunk());
        }

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int j = radius + 1;

        for(int l = -j; l <= j; ++l) {
            for(int k = -j; k <= j; ++k) {
                mutable.setWithOffset(treeNode.pos(), l, -2, k);
                if (!placer.isSet(mutable) && generateColumn(world, placer, random, config, chance, treeNode.pos(), mutable)) {
                    generateColumn(world, placer, random, config, chance, treeNode.pos(), mutable);
                }
            }
        }
    }

    private boolean generateColumn(LevelSimulatedReader world, FoliageSetter placer, RandomSource random, TreeConfiguration config, float chance, BlockPos origin, BlockPos.MutableBlockPos pos) {
        if (!TreeFeature.validTreePos(world, pos) || pos.distManhattan(origin) >= 7) {
            return false;
        } else {
            if (random.nextFloat() < chance) {
                for (int i = 0; i < length.sample(random); ++i) {
                    BlockState blockState = config.foliageProvider.getState(random, pos);
                    if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, world.isFluidAtPosition(pos, (fluidState) -> fluidState.isSourceOfType(Fluids.WATER)));
                    }

                    if (TreeFeature.validTreePos(world, pos.below())){
                        placer.set(pos, blockState);
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