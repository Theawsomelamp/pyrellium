package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

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
                ModBlocks.BONE,
                ModBlocks.QUARTZ_CRYSTAL,
                ModBlocks.BLACKSTONE_ROCK,
                ModBlocks.BURNING_NYLIUM,
                ModBlocks.HEADSTONE
        );

        getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(
                ModBlocks.HANGING_SILK
        );

        getOrCreateTagBuilder(BlockTags.DIRT).add(
                ModBlocks.NETHERRACK_MYCELIUM,
                ModBlocks.BURNING_NYLIUM
        );

        getOrCreateTagBuilder(BlockTags.LEAVES).add(
                ModBlocks.BURNING_LEAVES
        );

        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(
                ModBlocks.BURNING_SAPLING
        );

        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(
                ModBlocks.PYROLILY
        );

        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(
                ModBlocks.POTTED_BURNING_SAPLING,
                ModBlocks.POTTED_PYROLILY
        );

        getOrCreateTagBuilder(BlockTags.LOGS).add(
                ModBlocks.BURNING_LOG,
                ModBlocks.BURNING_WOOD,
                ModBlocks.STRIPPED_BURNING_LOG,
                ModBlocks.STRIPPED_BURNING_WOOD
        );

        getOrCreateTagBuilder(BlockTags.PLANKS).add(
                ModBlocks.BURNING_PLANKS
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(
                ModBlocks.BURNING_STAIRS
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(
                ModBlocks.BURNING_SLAB
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(
                ModBlocks.BURNING_FENCE
        );

        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(
                ModBlocks.BURNING_FENCE_GATE
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(
                ModBlocks.BURNING_DOOR
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(
                ModBlocks.BURNING_TRAPDOOR
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(
                ModBlocks.BURNING_PRESSURE_PLATE
        );

        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(
                ModBlocks.BURNING_BUTTON
        );

        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(
                ModBlocks.BURNING_SIGN
        );

        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(
                ModBlocks.BURNING_WALL_SIGN
        );

        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(
                ModBlocks.BURNING_HANGING_SIGN
        );

        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(
                ModBlocks.BURNING_WALL_HANGING_SIGN
        );

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(Pyrellium.MOD_ID, "burning_logs"))).add(
                ModBlocks.BURNING_LOG,
                ModBlocks.BURNING_WOOD,
                ModBlocks.STRIPPED_BURNING_LOG,
                ModBlocks.STRIPPED_BURNING_WOOD
        );
    }
}
