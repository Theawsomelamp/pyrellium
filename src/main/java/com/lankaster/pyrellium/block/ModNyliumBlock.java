package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModNyliumBlock extends NyliumBlock {
    public ModNyliumBlock(Properties settings) {
        super(settings);
    }

    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockState blockState = world.getBlockState(pos);
        BlockPos blockPos = pos.above();
        ChunkGenerator chunkGenerator = world.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockState.is(ModBlocks.BURNING_NYLIUM)) {
            this.generate(registry, ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "burning_grove_vegetation_bonemeal")), world, chunkGenerator, random, blockPos);
        } else if (blockState.is(ModBlocks.NETHERRACK_MYCELIUM)) {
            this.generate(registry, ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "mushroom_wastes_vegetation_bonemeal")), world, chunkGenerator, random, blockPos);
        }
    }

    private void generate(Registry<ConfiguredFeature<?, ?>> registry, ResourceKey<ConfiguredFeature<?, ?>> key, ServerLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
        registry.getHolder(key).ifPresent((entry) -> (entry.value()).place(world, chunkGenerator, random, pos));
    }
}