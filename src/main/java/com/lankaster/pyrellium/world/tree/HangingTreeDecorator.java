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
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;


public class HangingTreeDecorator extends TreeDecorator {
    public static final Codec<HangingTreeDecorator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((treeDecorator) -> treeDecorator.chance), IntProvider.createValidatingCodec(0, 16).fieldOf("length").forGetter((treeDecorator) -> treeDecorator.length), BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter((treeDecorator) -> treeDecorator.provider), BlockStateProvider.TYPE_CODEC.fieldOf("tip_provider").forGetter((treeDecorator) -> treeDecorator.tipProvider), IntProvider.createValidatingCodec(0, 16).fieldOf("minOffset").forGetter((treeDecorator) -> treeDecorator.minOffset), IntProvider.createValidatingCodec(0, 24).fieldOf("maxOffset").forGetter((treeDecorator) -> treeDecorator.maxOffset)).apply(instance, HangingTreeDecorator::new));
    public static final TreeDecoratorType<HangingTreeDecorator> HANGING_TREE_DECORATOR = Registry.register(Registries.TREE_DECORATOR_TYPE, Identifier.of(Pyrellium.MOD_ID, "hanging_vines"), new TreeDecoratorType<>(HangingTreeDecorator.CODEC));
    private final float chance;
    private final IntProvider length;
    private final BlockStateProvider provider;
    private final BlockStateProvider tipProvider;
    private final IntProvider minOffset;
    private final IntProvider maxOffset;

    public HangingTreeDecorator (float chance, IntProvider length, BlockStateProvider provider, BlockStateProvider tipProvider, IntProvider minOffset, IntProvider maxOffset) {
        this.chance = chance;
        this.length = length;
        this.provider = provider;
        this.tipProvider = tipProvider;
        this.minOffset = minOffset;
        this.maxOffset = maxOffset;
    }

    @Override
    protected TreeDecoratorType<?> getType(){
        return HANGING_TREE_DECORATOR;
    }

    @Override
    public void generate(Generator generator){
        TestableWorld world = generator.getWorld();
        Random random = generator.getRandom();
        List<BlockPos> leaves = Util.copyShuffled(generator.getLeavesPositions(), random);
        int minY = generator.getLeavesPositions().get(0).getY() -1;

        for(BlockPos pos : leaves) {
            BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.DOWN);
            boolean valid = !leaves.contains(mutable) && (mutable.getY() >= minY + minOffset.get(random) && mutable.getY() <= minY + maxOffset.get(random)) && world.testBlockState(pos, (blockState) -> blockState.isFullCube((BlockView) world, pos));
            if (random.nextFloat() < chance && valid) {
                for (int i = 0; i < length.get(random) -1; ++i) {
                    BlockState blockState = provider.get(random, mutable);
                    if (blockState.contains(Properties.WATERLOGGED)) {
                        blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(mutable, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
                    }

                    if (TreeFeature.canReplace(world, mutable.down())){
                        generator.replace(mutable, blockState);
                        mutable.move(Direction.DOWN);
                    }
                }
                generator.replace(mutable, tipProvider.get(random, mutable));
            }
        }
    }

    public static void registerHangingTreeDecorator() {
    }
}
