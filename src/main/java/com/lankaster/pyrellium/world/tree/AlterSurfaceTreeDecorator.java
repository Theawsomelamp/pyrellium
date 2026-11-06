package com.lankaster.pyrellium.world.tree;

import com.google.common.collect.Lists;
import com.lankaster.pyrellium.world.ModWorldGeneration;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class AlterSurfaceTreeDecorator extends TreeDecorator {
    public static final MapCodec<AlterSurfaceTreeDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(BlockState.CODEC.fieldOf("target").forGetter((treeDecorator) -> treeDecorator.target), BlockStateProvider.CODEC.fieldOf("provider").forGetter((treeDecorator) -> treeDecorator.provider)).apply(instance, AlterSurfaceTreeDecorator::new));
    private final BlockState target;
    private final BlockStateProvider provider;

    public AlterSurfaceTreeDecorator(BlockState target, BlockStateProvider provider) {
        this.target = target;
        this.provider = provider;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModWorldGeneration.ALTER_SURFACE.get();
    }

    @Override
    public void place(Context generator) {
        List<BlockPos> positions = Lists.newArrayList();
        List<BlockPos> rootPositions = generator.roots();
        List<BlockPos> logPositions = generator.logs();
        if (rootPositions.isEmpty()) {
            positions.addAll(logPositions);
        } else if (!logPositions.isEmpty() && rootPositions.getFirst().getY() == logPositions.getFirst().getY()) {
            positions.addAll(logPositions);
            positions.addAll(rootPositions);
        } else {
            positions.addAll(rootPositions);
        }

        if (!positions.isEmpty()) {
            int yPos = positions.getFirst().getY();
            positions.stream().filter((pos) -> pos.getY() == yPos).forEach((pos) -> {
                this.setArea(generator, pos.west().north());
                this.setArea(generator, pos.east(2).north());
                this.setArea(generator, pos.west().south(2));
                this.setArea(generator, pos.east(2).south(2));

                for(int i = 0; i < 5; ++i) {
                    int j = generator.random().nextInt(64);
                    int k = j % 8;
                    int l = j / 8;
                    if (k == 0 || k == 7 || l == 0 || l == 7) {
                        this.setArea(generator, pos.offset(-3 + k, 0, -3 + l));
                    }
                }

            });
        }
    }

    private void setArea(Context generator, BlockPos origin) {
        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.setColumn(generator, origin.offset(i, 0, j));
                }
            }
        }

    }

    private void setColumn(Context generator, BlockPos origin) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockPos = origin.above(i);
            if (generator.level().isStateAtPosition(blockPos, (state) -> state.is(target.getBlock()))) {
                generator.setBlock(blockPos, this.provider.getState(generator.random(), origin));
                break;
            }

            if (!generator.isAir(blockPos) && i < 0) {
                break;
            }

        }
    }
}
