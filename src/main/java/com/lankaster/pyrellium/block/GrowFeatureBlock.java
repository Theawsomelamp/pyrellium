package com.lankaster.pyrellium.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GrowFeatureBlock extends Block implements BonemealableBlock {
    ResourceKey<ConfiguredFeature<?, ?>> feature;

    public GrowFeatureBlock(Properties settings, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        super(settings);
        this.feature = feature;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.above();
        ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        this.generate(registry, this.feature, world, chunkGenerator, random, blockPos);
    }

    private void generate(Registry<ConfiguredFeature<?, ?>> registry, ResourceKey<ConfiguredFeature<?, ?>> key, ServerLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        registry.getHolder(key).ifPresent((entry) -> (entry.value()).place(world, chunkGenerator, random, pos));
    }
}