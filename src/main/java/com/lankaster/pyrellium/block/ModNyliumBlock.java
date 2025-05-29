package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ModNyliumBlock extends NyliumBlock {
    public ModNyliumBlock(Settings settings) {
        super(settings);
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState blockState = world.getBlockState(pos);
        BlockPos blockPos = pos.up();
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE);
        if (blockState.isOf(ModBlocks.BURNING_NYLIUM)) {
            this.generate(registry, RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "burning_grove_vegetation_bonemeal")), world, chunkGenerator, random, blockPos);
        } else if (blockState.isOf(ModBlocks.NETHERRACK_MYCELIUM)) {
            this.generate(registry, RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "mushroom_wastes_vegetation_bonemeal")), world, chunkGenerator, random, blockPos);
        }
    }

    private void generate(Registry<ConfiguredFeature<?, ?>> registry, RegistryKey<ConfiguredFeature<?, ?>> key, ServerWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos) {
        registry.getEntry(key).ifPresent((entry) -> (entry.value()).generate(world, chunkGenerator, random, pos));
    }
}
