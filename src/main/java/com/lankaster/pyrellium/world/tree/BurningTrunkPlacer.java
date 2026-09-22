package com.lankaster.pyrellium.world.tree;

import com.google.common.collect.Lists;
import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BurningTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<BurningTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance).and(instance.group(IntProvider.codec(1, 32).fieldOf("fork_height").forGetter((trunkPlacer) -> trunkPlacer.forkHeight), IntProvider.codec(1, 4).fieldOf("branch_count").forGetter((trunkPlacer) -> trunkPlacer.branchCount), Codec.floatRange(0, 1).fieldOf("bend_chance").forGetter((trunkPlacer) -> trunkPlacer.bendChance))).apply(instance, BurningTrunkPlacer::new));
    public static final TrunkPlacerType<BurningTrunkPlacer> BURNING_TRUNK_PLACER = Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "burning_trunk_placer"), new TrunkPlacerType<>(BurningTrunkPlacer.CODEC));
    private final IntProvider forkHeight;
    private final IntProvider branchCount;
    private final float bendChance;

    public BurningTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, IntProvider forkHeight, IntProvider branchCount, float bendChance) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.forkHeight = forkHeight;
        this.branchCount = branchCount;
        this.bendChance = bendChance;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return BURNING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        BlockPos.MutableBlockPos mutable = startPos.mutable();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int perTreeForkHeight = forkHeight.sample(random);

        for(int i = 0; i < perTreeForkHeight; ++i) {
            this.placeLog(world, replacer, random, mutable, config);

            if (random.nextFloat() < bendChance && i >= 2) {
                mutable.move(direction);
            }

            mutable.move(Direction.UP);
        }

        this.generateBranch(world, replacer, random, height, config, list, mutable, perTreeForkHeight, direction);

        if (perTreeForkHeight > height) {
            list.add(new FoliagePlacer.FoliageAttachment(mutable.immutable(), 0, false));
        }

        return list;
    }

    private void generateBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, TreeConfiguration config, List<FoliagePlacer.FoliageAttachment> nodes, BlockPos.MutableBlockPos startPos, int yOffset, Direction direction) {
        int branches = branchCount.sample(random);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        pos.set(startPos);

        direction = direction.getOpposite();

        for(int i = 0; i < branches; ++i) {
            if (branches < 3) {
                direction = direction.getOpposite();
            } else {
                direction = direction.getClockWise();
            }

            for(int l = yOffset; l < height; ++l) {
                if (l >= 1) {
                    this.placeLog(world, replacer, random, pos, config);
                    pos.move(direction);
                    pos.move(Direction.UP);
                }
            }

            this.placeLog(world, replacer, random, pos, config);

            nodes.add(new FoliagePlacer.FoliageAttachment(pos.move(Direction.UP).immutable(), 0, false));

            pos.set(startPos);
        }
    }

    public static void registerBurningTrunkPlacer() {
    }
}