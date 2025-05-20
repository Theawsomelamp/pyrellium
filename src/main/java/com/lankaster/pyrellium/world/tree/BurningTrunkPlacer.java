package com.lankaster.pyrellium.world.tree;

import com.google.common.collect.Lists;
import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BurningTrunkPlacer extends TrunkPlacer {
    public static final Codec<BurningTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).and(instance.group(IntProvider.createValidatingCodec(1, 32).fieldOf("fork_height").forGetter((trunkPlacer) -> trunkPlacer.forkHeight), IntProvider.createValidatingCodec(1, 4).fieldOf("branch_count").forGetter((trunkPlacer) -> trunkPlacer.branchCount), Codec.floatRange(0, 1).fieldOf("bend_chance").forGetter((trunkPlacer) -> trunkPlacer.bendChance))).apply(instance, BurningTrunkPlacer::new));
    public static final TrunkPlacerType<BurningTrunkPlacer> BURNING_TRUNK_PLACER = Registry.register(Registries.TRUNK_PLACER_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_trunk_placer"), new TrunkPlacerType<>(BurningTrunkPlacer.CODEC));
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
    protected TrunkPlacerType<?> getType() {
        return BURNING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos.Mutable mutable = startPos.mutableCopy();
        Direction direction = Direction.Type.HORIZONTAL.random(random);
        int perTreeForkHeight = forkHeight.get(random);

        for(int i = 0; i < perTreeForkHeight; ++i) {
            this.getAndSetState(world, replacer, random, mutable, config);

            if (random.nextFloat() < bendChance && i >= 2) {
                mutable.move(direction);
            }

            mutable.move(Direction.UP);
        }

        this.generateBranch(world, replacer, random, height, config, list, mutable, perTreeForkHeight, direction);

        if (perTreeForkHeight > height) {
            list.add(new FoliagePlacer.TreeNode(mutable.toImmutable(), 0, false));
        }

        return list;
    }

    private void generateBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, TreeFeatureConfig config, List<FoliagePlacer.TreeNode> nodes, BlockPos.Mutable startPos, int yOffset, Direction direction) {
        int branches = branchCount.get(random);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        pos.set(startPos);

        direction = direction.getOpposite();

        for(int i = 0; i < branches; ++i) {
            if (branches < 3) {
                direction = direction.getOpposite();
            } else {
                direction = direction.rotateYClockwise();
            }

            for(int l = yOffset; l < height; ++l) {
                if (l >= 1) {
                    this.getAndSetState(world, replacer, random, pos, config);
                    pos.move(direction);
                    pos.move(Direction.UP);
                }
            }

            nodes.add(new FoliagePlacer.TreeNode(pos.move(direction).toImmutable(), 0, false));

            pos.set(startPos);
        }
    }

    public static void registerBurningTrunkPlacer() {
    }
}
