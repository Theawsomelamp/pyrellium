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
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class AlterSurfaceTreeDecorator extends TreeDecorator {
    public static final Codec<AlterSurfaceTreeDecorator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockState.CODEC.fieldOf("target").forGetter((treeDecorator) -> treeDecorator.target), BlockStateProvider.TYPE_CODEC.fieldOf("provider").forGetter((treeDecorator) -> treeDecorator.provider)).apply(instance, AlterSurfaceTreeDecorator::new));
    public static final TreeDecoratorType<AlterSurfaceTreeDecorator> ALTER_SURFACE = Registry.register(Registries.TREE_DECORATOR_TYPE, Identifier.of(Pyrellium.MOD_ID, "alter_surface"), new TreeDecoratorType<>(AlterSurfaceTreeDecorator.CODEC));
    private final BlockState target;
    private final BlockStateProvider provider;

    public AlterSurfaceTreeDecorator(BlockState target, BlockStateProvider provider) {
        this.target = target;
        this.provider = provider;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ALTER_SURFACE;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        List<BlockPos> positions = Lists.newArrayList();
        List<BlockPos> rootPositions = generator.getRootPositions();
        List<BlockPos> logPositions = generator.getLogPositions();
        if (rootPositions.isEmpty()) {
            positions.addAll(logPositions);
        } else if (!logPositions.isEmpty() && rootPositions.get(0).getY() == logPositions.get(0).getY()) {
            positions.addAll(logPositions);
            positions.addAll(rootPositions);
        } else {
            positions.addAll(rootPositions);
        }

        if (!positions.isEmpty()) {
            int yPos = positions.get(0).getY();
            positions.stream().filter((pos) -> pos.getY() == yPos).forEach((pos) -> {
                this.setArea(generator, pos.west().north());
                this.setArea(generator, pos.east(2).north());
                this.setArea(generator, pos.west().south(2));
                this.setArea(generator, pos.east(2).south(2));

                for(int i = 0; i < 5; ++i) {
                    int j = generator.getRandom().nextInt(64);
                    int k = j % 8;
                    int l = j / 8;
                    if (k == 0 || k == 7 || l == 0 || l == 7) {
                        this.setArea(generator, pos.add(-3 + k, 0, -3 + l));
                    }
                }

            });
        }
    }

    private void setArea(TreeDecorator.Generator generator, BlockPos origin) {
        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                    this.setColumn(generator, origin.add(i, 0, j));
                }
            }
        }

    }

    private void setColumn(TreeDecorator.Generator generator, BlockPos origin) {
        for (int i = 2; i >= -3; --i) {
            BlockPos blockPos = origin.up(i);
            if (generator.getWorld().testBlockState(blockPos, (state) -> state.isOf(target.getBlock()))) {
                generator.replace(blockPos, this.provider.get(generator.getRandom(), origin));
                break;
            }

            if (!generator.isAir(blockPos) && i < 0) {
                break;
            }

        }
    }

    public static void registerAlterSurfaceTreeDecorator() {
    }
}