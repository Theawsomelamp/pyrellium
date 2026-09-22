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
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;


public class HangingTreeDecorator extends TreeDecorator {
    public static final MapCodec<HangingTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((treeDecorator) -> treeDecorator.chance), IntProvider.codec(0, 16).fieldOf("length").forGetter((treeDecorator) -> treeDecorator.length), BlockStateProvider.CODEC.fieldOf("provider").forGetter((treeDecorator) -> treeDecorator.provider), BlockStateProvider.CODEC.fieldOf("tip_provider").forGetter((treeDecorator) -> treeDecorator.tipProvider), IntProvider.codec(0, 16).fieldOf("minOffset").forGetter((treeDecorator) -> treeDecorator.minOffset), IntProvider.codec(0, 24).fieldOf("maxOffset").forGetter((treeDecorator) -> treeDecorator.maxOffset)).apply(instance, HangingTreeDecorator::new));
    public static final TreeDecoratorType<HangingTreeDecorator> HANGING_TREE_DECORATOR = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "hanging_vines"), new TreeDecoratorType<>(HangingTreeDecorator.CODEC));
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
    protected TreeDecoratorType<?> type(){
        return HANGING_TREE_DECORATOR;
    }

    @Override
    public void place(Context generator){
        LevelSimulatedReader world = generator.level();
        RandomSource random = generator.random();
        List<BlockPos> leaves = Util.shuffledCopy(generator.leaves(), random);
        int minY = generator.leaves().get(0).getY() -1;

        for(BlockPos pos : leaves) {
            BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.DOWN);
            boolean valid = !leaves.contains(mutable) && (mutable.getY() >= minY + minOffset.sample(random) && mutable.getY() <= minY + maxOffset.sample(random)) && world.isStateAtPosition(pos, (blockState) -> blockState.isCollisionShapeFullBlock((BlockGetter) world, pos));
            if (random.nextFloat() < chance && valid) {
                for (int i = 0; i < length.sample(random) -1; ++i) {
                    BlockState blockState = provider.getState(random, mutable);
                    if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, world.isFluidAtPosition(mutable, (fluidState) -> fluidState.isSourceOfType(Fluids.WATER)));
                    }

                    if (TreeFeature.validTreePos(world, mutable.below())){
                        generator.setBlock(mutable, blockState);
                        mutable.move(Direction.DOWN);
                    }
                }
                generator.setBlock(mutable, tipProvider.getState(random, mutable));
            }
        }
    }

    public static void registerHangingTreeDecorator() {
    }
}