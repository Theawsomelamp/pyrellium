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
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
                ModBlocks.OPAL_BLOCK,
                ModBlocks.SMALL_OPAL_BUD,
                ModBlocks.MEDIUM_OPAL_BUD,
                ModBlocks.LARGE_OPAL_BUD,
                ModBlocks.OPAL_CLUSTER,
                ModBlocks.BUDDING_OPAL,
                ModBlocks.CLEAR_AMETHYST_BLOCK,
                ModBlocks.CLEAR_OPAL_BLOCK,
                ModBlocks.FREEZING_ICE,
                ModBlocks.NETHERRACK_MYCELIUM,
                ModBlocks.BONE
        );
    }
}
