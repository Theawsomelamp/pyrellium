package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagDataGen extends FabricTagProvider.BlockTagProvider{
    public BlockTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.OPAL_BLOCK)
                .add(ModBlocks.SMALL_OPAL_BUD)
                .add(ModBlocks.MEDIUM_OPAL_BUD)
                .add(ModBlocks.LARGE_OPAL_BUD)
                .add(ModBlocks.OPAL_CLUSTER)
                .add(ModBlocks.BUDDING_OPAL)
                .add(ModBlocks.CLEAR_AMETHYST_BLOCK)
                .add(ModBlocks.CLEAR_OPAL_BLOCK)
                .add(ModBlocks.FREEZING_ICE)
                .add(ModBlocks.NETHERRACK_MYCELIUM)
                .add(ModBlocks.BONE);
    }
}
