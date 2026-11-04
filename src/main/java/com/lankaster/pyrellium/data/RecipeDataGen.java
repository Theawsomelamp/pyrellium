package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RecipeDataGen extends FabricRecipeProvider {

    public RecipeDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            public void offer2x2WithCount(RecipeCategory category, ItemConvertible output, int count, ItemConvertible input) {
                createShaped(category, output, count)
                        .input('#', input)
                        .pattern("##")
                        .pattern("##")
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void offerCompassRecipe(RecipeCategory category, ItemConvertible result, int count,
                                                   ItemConvertible a, ItemConvertible b) {
                createShaped(category, result, count)
                        .pattern(" B ")
                        .pattern("BAB")
                        .pattern(" B ")
                        .input('A', a).input('B', b)
                        .criterion(hasItem(a), conditionsFromItem(a))
                        .criterion(hasItem(b), conditionsFromItem(b))
                        .group(Registries.ITEM.getId(result.asItem()).getPath())
                        .offerTo(exporter);
            }

            private void offerClearBlockRecipe(RecipeCategory category, ItemConvertible result, int count,
                                                      ItemConvertible input) {
                createShaped(category, result, count)
                        .pattern(" # ")
                        .pattern("# #")
                        .pattern(" # ")
                        .input('#', input)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .group(Registries.ITEM.getId(result.asItem()).getPath())
                        .offerTo(exporter);
            }

            private void offerArrowRecipe(ItemConvertible result, int count,
                                                 ItemConvertible a, ItemConvertible b, ItemConvertible c) {
                createShaped(RecipeCategory.MISC, result, count)
                        .pattern("A")
                        .pattern("B")
                        .pattern("C")
                        .input('A', a).input('B', b).input('C', c)
                        .criterion(hasItem(a), conditionsFromItem(a))
                        .criterion(hasItem(b), conditionsFromItem(b))
                        .criterion(hasItem(c), conditionsFromItem(c))
                        .group(Registries.ITEM.getId(result.asItem()).getPath())
                        .offerTo(exporter);
            }

            private void offerHatRecipe(RecipeCategory category, ItemConvertible result,
                                               ItemConvertible a, ItemConvertible b, ItemConvertible c) {
                createShaped(category, result, 1)
                        .pattern(" A ")
                        .pattern("BCB")
                        .input('A', a).input('B', b).input('C', c)
                        .criterion(hasItem(a), conditionsFromItem(a))
                        .criterion(hasItem(b), conditionsFromItem(b))
                        .criterion(hasItem(c), conditionsFromItem(c))
                        .group(Registries.ITEM.getId(result.asItem()).getPath())
                        .offerTo(exporter);
            }

            private void offerWoodSetRecipes(ItemConvertible plank,
                                                    ItemConvertible stair, ItemConvertible slab, ItemConvertible fence, ItemConvertible fenceGate, ItemConvertible door, ItemConvertible trapdoor, ItemConvertible pressurePlate, ItemConvertible button, ItemConvertible sign) {
                createStairsRecipe(stair, Ingredient.ofItems(plank))
                        .criterion(hasItem(plank), conditionsFromItem(plank))
                        .offerTo(exporter);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab, plank);
                createFenceRecipe(fence, Ingredient.ofItems(plank))
                        .criterion(hasItem(plank), conditionsFromItem(plank))
                        .offerTo(exporter);
                createFenceGateRecipe(fenceGate, Ingredient.ofItems(plank))
                        .criterion(hasItem(plank), conditionsFromItem(plank))
                        .offerTo(exporter);
                createDoorRecipe(door, Ingredient.ofItems(plank))
                        .criterion(hasItem(plank), conditionsFromItem(plank))
                        .offerTo(exporter);
                createTrapdoorRecipe(trapdoor, Ingredient.ofItems(plank))
                        .criterion(hasItem(plank), conditionsFromItem(plank))
                        .offerTo(exporter);
                offerPressurePlateRecipe(pressurePlate, plank);
                offerSingleOutputShapelessRecipe(button, plank, "wooden_button");
                createSignRecipe(sign, Ingredient.ofItems(plank))
                        .criterion(hasItem(plank), conditionsFromItem(plank))
                        .offerTo(exporter);
            }


            @Override
            public void generate() {
                offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OPAL_BLOCK, ModItems.OPAL);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, Items.QUARTZ, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROUGH_QUARTZ_BLOCK);
                offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_FREEZING_ICE, ModBlocks.FREEZING_ICE);

                offerClearBlockRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLEAR_AMETHYST_BLOCK, 1, Items.AMETHYST_SHARD);
                offerClearBlockRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLEAR_OPAL_BLOCK, 1, ModItems.OPAL);

                offerCompassRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILK_BLOCK, 2, Items.SPIDER_EYE, Items.STRING);

                offerCarpetRecipe(ModBlocks.SILK_CARPET, ModBlocks.SILK_BLOCK);
                offerCarpetRecipe(ModBlocks.BLACKSTONE_ROCK, Blocks.BLACKSTONE);

                offer2x2WithCount(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BURNING_WOOD, 3, ModBlocks.BURNING_LOG);
                offerPlanksRecipe(ModBlocks.BURNING_PLANKS, TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "burning_logs")), 4);
                offerWoodSetRecipes(ModBlocks.BURNING_PLANKS,
                        ModBlocks.BURNING_STAIRS, ModBlocks.BURNING_SLAB, ModBlocks.BURNING_FENCE, ModBlocks.BURNING_FENCE_GATE, ModBlocks.BURNING_DOOR, ModBlocks.BURNING_TRAPDOOR, ModBlocks.BURNING_PRESSURE_PLATE, ModBlocks.BURNING_BUTTON, ModBlocks.BURNING_SIGN_ITEM);
                offerHangingSignRecipe(ModBlocks.BURNING_HANGING_SIGN_ITEM, ModBlocks.STRIPPED_BURNING_LOG);
                offerShelfRecipe(ModBlocks.BURNING_SHELF, ModBlocks.STRIPPED_BURNING_LOG);
                offerBoatRecipe(ModBlocks.BURNING_BOAT, ModBlocks.BURNING_PLANKS);
                offerChestBoatRecipe(ModBlocks.BURNING_CHEST_BOAT, ModBlocks.BURNING_BOAT);

                offer2x2WithCount(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SHADEROOT_WOOD, 3, ModBlocks.SHADEROOT_LOG);
                offerPlanksRecipe(ModBlocks.SHADEROOT_PLANKS, TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "shaderoot_logs")), 4);
                offerWoodSetRecipes(ModBlocks.SHADEROOT_PLANKS,
                        ModBlocks.SHADEROOT_STAIRS, ModBlocks.SHADEROOT_SLAB, ModBlocks.SHADEROOT_FENCE, ModBlocks.SHADEROOT_FENCE_GATE, ModBlocks.SHADEROOT_DOOR, ModBlocks.SHADEROOT_TRAPDOOR, ModBlocks.SHADEROOT_PRESSURE_PLATE, ModBlocks.SHADEROOT_BUTTON, ModBlocks.SHADEROOT_SIGN_ITEM);
                offerHangingSignRecipe(ModBlocks.SHADEROOT_HANGING_SIGN_ITEM, ModBlocks.STRIPPED_SHADEROOT_LOG);
                offerShelfRecipe(ModBlocks.SHADEROOT_SHELF, ModBlocks.STRIPPED_SHADEROOT_LOG);
                offerBoatRecipe(ModBlocks.SHADEROOT_BOAT, ModBlocks.SHADEROOT_PLANKS);
                offerChestBoatRecipe(ModBlocks.SHADEROOT_CHEST_BOAT, ModBlocks.SHADEROOT_BOAT);

                offerArrowRecipe(ModItems.AMETHYST_ARROW, 4, Items.AMETHYST_SHARD, Items.STICK, Items.FEATHER);
                offerArrowRecipe(ModItems.OPAL_ARROW, 4, ModItems.OPAL, Items.STICK, Items.FEATHER);

                offerHatRecipe(RecipeCategory.COMBAT, ModItems.OPAL_TIARA, ModItems.OPAL, Items.GOLD_INGOT, Items.GHAST_TEAR);
                offerHatRecipe(RecipeCategory.COMBAT, ModItems.MUSHROOM_CAP, Blocks.RED_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM, Items.LEATHER_HELMET);

                offerShapelessRecipe(Items.LIGHT_BLUE_DYE, ModBlocks.PYROLILY, "light_blue_dye", 1);
                offerShapelessRecipe(Items.BLUE_DYE, ModBlocks.WISPBLOOM, "blue_dye", 1);

                // Opal Spyglass
                createShaped(RecipeCategory.TOOLS, ModItems.OPAL_SPYGLASS)
                        .input('A', ModItems.OPAL)
                        .input('B', Items.COPPER_INGOT)
                        .pattern("A")
                        .pattern("B")
                        .pattern("B")
                        .criterion(hasItem(ModItems.OPAL), conditionsFromItem(ModItems.OPAL))
                        .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter);

                // Chain Fence
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHAIN_FENCE, 16)
                        .input('#', Blocks.IRON_CHAIN)
                        .pattern("###")
                        .pattern("###")
                        .criterion(hasItem(Blocks.IRON_CHAIN), conditionsFromItem(Blocks.IRON_CHAIN))
                        .offerTo(exporter);

                // Headstone
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HEADSTONE, 3)
                        .input('#', Blocks.STONE_BRICKS)
                        .pattern(" # ")
                        .pattern("###")
                        .pattern("###")
                        .criterion(hasItem(Blocks.STONE_BRICKS), conditionsFromItem(Blocks.STONE_BRICKS))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return Pyrellium.MOD_ID + " Recipes";
    }
}