package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider{

    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.ARROWS).add(
                ModItems.AMETHYST_ARROW,
                ModItems.OPAL_ARROW
        );

        getOrCreateTagBuilder(ItemTags.PLANKS).add(
                ModBlocks.BURNING_PLANKS.asItem(),
                ModBlocks.SHADEROOT_PLANKS.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(
                ModBlocks.BURNING_SLAB.asItem(),
                ModBlocks.SHADEROOT_SLAB.asItem()
        );

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "burning_logs"))).add(
                ModBlocks.BURNING_LOG.asItem(),
                ModBlocks.BURNING_WOOD.asItem(),
                ModBlocks.STRIPPED_BURNING_LOG.asItem(),
                ModBlocks.STRIPPED_BURNING_WOOD.asItem()
        );

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "shaderoot_logs"))).add(
                ModBlocks.SHADEROOT_LOG.asItem(),
                ModBlocks.SHADEROOT_WOOD.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_LOG.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_WOOD.asItem()
        );
    }
}