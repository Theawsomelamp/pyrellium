package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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

    private FabricTagBuilder getOrCreateTagBuilder(Identifier id) {
        return this.getOrCreateTagBuilder(tag(id));
    }

    private TagKey<Item> tag(Identifier id) {
        return TagKey.of(RegistryKeys.ITEM, id);
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

        getOrCreateTagBuilder(ItemTags.SIGNS).add(
                ModBlocks.BURNING_SIGN_ITEM,
                ModBlocks.SHADEROOT_SIGN_ITEM
        );

        getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(
                ModBlocks.BURNING_HANGING_SIGN_ITEM,
                ModBlocks.SHADEROOT_HANGING_SIGN_ITEM
        );

        getOrCreateTagBuilder(ItemTags.BOATS).add(
                ModBlocks.BURNING_BOAT,
                ModBlocks.SHADEROOT_BOAT
        );

        getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(
                ModBlocks.BURNING_CHEST_BOAT,
                ModBlocks.SHADEROOT_CHEST_BOAT
        );

        getOrCreateTagBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
                ModBlocks.DRAINED_SOUL_SOIL.asItem()
        );

        getOrCreateTagBuilder(Identifier.of(Pyrellium.MOD_ID, "burning_logs")).add(
                ModBlocks.BURNING_LOG.asItem(),
                ModBlocks.BURNING_WOOD.asItem(),
                ModBlocks.STRIPPED_BURNING_LOG.asItem(),
                ModBlocks.STRIPPED_BURNING_WOOD.asItem()
        );

        getOrCreateTagBuilder(Identifier.of(Pyrellium.MOD_ID, "shaderoot_logs")).add(
                ModBlocks.SHADEROOT_LOG.asItem(),
                ModBlocks.SHADEROOT_WOOD.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_LOG.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_WOOD.asItem()
        );

        getOrCreateTagBuilder(Identifier.of(Pyrellium.MOD_ID, "crystals")).add(
                Items.AMETHYST_SHARD,
                ModItems.OPAL,
                Items.QUARTZ
        );

        getOrCreateTagBuilder(Identifier.of(Pyrellium.MOD_ID, "hats")).add(
                ModItems.OPAL_TIARA,
                ModItems.MUSHROOM_CAP
        );

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).addOptionalTag(
                tag(Identifier.of(Pyrellium.MOD_ID, "hats"))
        );

        getOrCreateTagBuilder(ConventionalItemTags.BUDDING_BLOCKS).add(
                ModBlocks.BUDDING_OPAL.asItem(),
                ModBlocks.BUDDING_QUARTZ.asItem()
        );

        getOrCreateTagBuilder(ConventionalItemTags.BUDS).add(
                ModBlocks.SMALL_OPAL_BUD.asItem(),
                ModBlocks.MEDIUM_OPAL_BUD.asItem(),
                ModBlocks.LARGE_OPAL_BUD.asItem(),
                ModBlocks.SMALL_QUARTZ_BUD.asItem(),
                ModBlocks.MEDIUM_QUARTZ_BUD.asItem(),
                ModBlocks.LARGE_QUARTZ_BUD.asItem()
        );

        getOrCreateTagBuilder(ConventionalItemTags.CLUSTERS).add(
                ModBlocks.OPAL_CLUSTER.asItem(),
                ModBlocks.QUARTZ_CRYSTAL.asItem()
        );

        getOrCreateTagBuilder(ConventionalItemTags.GEMS).add(
                ModItems.OPAL
        );
    }
}