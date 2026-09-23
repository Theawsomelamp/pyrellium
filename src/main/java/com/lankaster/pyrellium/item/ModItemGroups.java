package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemGroups {
    public static final DeferredRegister<CreativeModeTab> PYRELLIUM =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pyrellium.MOD_ID);

    public static final Supplier<CreativeModeTab> PYRELLIUM_TAB = PYRELLIUM.register("pyrellium",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.OPAL_BLOCK))
                    .title(Component.translatable("itemgroup.pyrellium"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.OPAL);
                        output.accept(ModItems.OPAL_SPYGLASS);
                        output.accept(ModItems.OPAL_TIARA);
                        output.accept(ModItems.AMETHYST_ARROW);
                        output.accept(ModItems.OPAL_ARROW);
                        output.accept(ModItems.BOMB_FLOWER);
                        output.accept(ModItems.MUSHROOM_CAP);
                        output.accept(ModItems.GEODIN_SPAWN_EGG);

                        output.accept(ModBlocks.OPAL_BLOCK);
                        output.accept(ModBlocks.SMALL_OPAL_BUD);
                        output.accept(ModBlocks.MEDIUM_OPAL_BUD);
                        output.accept(ModBlocks.LARGE_OPAL_BUD);
                        output.accept(ModBlocks.OPAL_CLUSTER);
                        output.accept(ModBlocks.BUDDING_OPAL);
                        output.accept(ModBlocks.CLEAR_AMETHYST_BLOCK);
                        output.accept(ModBlocks.CLEAR_OPAL_BLOCK);
                        output.accept(ModBlocks.ROUGH_QUARTZ_BLOCK);
                        output.accept(ModBlocks.SMALL_QUARTZ_BUD);
                        output.accept(ModBlocks.MEDIUM_QUARTZ_BUD);
                        output.accept(ModBlocks.LARGE_QUARTZ_BUD);
                        output.accept(ModBlocks.QUARTZ_CRYSTAL);
                        output.accept(ModBlocks.BUDDING_QUARTZ);
                        output.accept(ModBlocks.FREEZING_ICE);
                        output.accept(ModBlocks.SILK_BLOCK);
                        output.accept(ModBlocks.SILK_CARPET);
                        output.accept(ModBlocks.HANGING_SILK);
                        output.accept(ModBlocks.NETHERRACK_MYCELIUM);
                        output.accept(ModBlocks.BROWN_BOUNCESHROOM);
                        output.accept(ModBlocks.RED_BOUNCESHROOM);
                        output.accept(ModBlocks.BROWN_SHELF_MUSHROOM);
                        output.accept(ModBlocks.RED_SHELF_MUSHROOM);
                        output.accept(ModBlocks.SPORES);
                        output.accept(ModBlocks.BASALT_IRON_ORE);
                        output.accept(ModBlocks.HEADSTONE);
                        output.accept(ModBlocks.CHAIN_FENCE);
                        output.accept(ModBlocks.BOMB_PLANT);
                        output.accept(ModBlocks.BLACKSTONE_ROCK);
                        output.accept(ModBlocks.BURNING_NYLIUM);
                        output.accept(ModBlocks.BURNING_LEAVES);
                        output.accept(ModBlocks.BURNING_SAPLING);
                        output.accept(ModBlocks.BURNING_LOG);
                        output.accept(ModBlocks.BURNING_WOOD);
                        output.accept(ModBlocks.STRIPPED_BURNING_LOG);
                        output.accept(ModBlocks.STRIPPED_BURNING_WOOD);
                        output.accept(ModBlocks.BURNING_PLANKS);
                        output.accept(ModBlocks.BURNING_STAIRS);
                        output.accept(ModBlocks.BURNING_SLAB);
                        output.accept(ModBlocks.BURNING_FENCE);
                        output.accept(ModBlocks.BURNING_FENCE_GATE);
                        output.accept(ModBlocks.BURNING_DOOR);
                        output.accept(ModBlocks.BURNING_TRAPDOOR);
                        output.accept(ModBlocks.BURNING_PRESSURE_PLATE);
                        output.accept(ModBlocks.BURNING_BUTTON);
                        output.accept(ModBlocks.BURNING_SIGN_ITEM);
                        output.accept(ModBlocks.BURNING_HANGING_SIGN_ITEM);
                        output.accept(ModBlocks.BURNING_BOAT);
                        output.accept(ModBlocks.BURNING_CHEST_BOAT);
                        output.accept(ModBlocks.BURNING_ROOTS);
                        output.accept(ModBlocks.BURNING_SPROUTS);
                        output.accept(ModBlocks.BURNING_VINES);
                        output.accept(ModBlocks.PYROLILY);
                        output.accept(ModBlocks.DRAINED_SOUL_SOIL);
                        output.accept(ModBlocks.GHOSTLY_LEAVES);
                        output.accept(ModBlocks.FLOWERING_GHOSTLY_LEAVES);
                        output.accept(ModBlocks.SHADEROOT_SAPLING);
                        output.accept(ModBlocks.SHADEROOT_LOG);
                        output.accept(ModBlocks.SHADEROOT_WOOD);
                        output.accept(ModBlocks.STRIPPED_SHADEROOT_LOG);
                        output.accept(ModBlocks.STRIPPED_SHADEROOT_WOOD);
                        output.accept(ModBlocks.SHADEROOT_PLANKS);
                        output.accept(ModBlocks.SHADEROOT_STAIRS);
                        output.accept(ModBlocks.SHADEROOT_SLAB);
                        output.accept(ModBlocks.SHADEROOT_FENCE);
                        output.accept(ModBlocks.SHADEROOT_FENCE_GATE);
                        output.accept(ModBlocks.SHADEROOT_DOOR);
                        output.accept(ModBlocks.SHADEROOT_TRAPDOOR);
                        output.accept(ModBlocks.SHADEROOT_PRESSURE_PLATE);
                        output.accept(ModBlocks.SHADEROOT_BUTTON);
                        output.accept(ModBlocks.SHADEROOT_SIGN_ITEM);
                        output.accept(ModBlocks.SHADEROOT_HANGING_SIGN_ITEM);
                        output.accept(ModBlocks.SHADEROOT_BOAT);
                        output.accept(ModBlocks.SHADEROOT_CHEST_BOAT);
                        output.accept(ModBlocks.DEAD_ROOTS);
                        output.accept(ModBlocks.DEAD_SPROUTS);
                        output.accept(ModBlocks.OPAL_BLOCK);
                        output.accept(ModBlocks.GHOSTLY_VINES);
                        output.accept(ModBlocks.WISPBLOOM);
                    })
                    .build());

    public static void registerItemGroups(IEventBus eventBus) {
        PYRELLIUM.register(eventBus);
    }
}
