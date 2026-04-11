package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;


public class ModBlocks {
    public static final Block OPAL_BLOCK = registerBlock("opal_block", Block::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
    public static final Block SMALL_OPAL_BUD = registerBlock("small_opal_bud", properties -> new AmethystClusterBlock(5, 3, properties), AbstractBlock.Settings.copy(Blocks.SMALL_AMETHYST_BUD));
    public static final Block MEDIUM_OPAL_BUD = registerBlock("medium_opal_bud", properties -> new AmethystClusterBlock(4, 3, properties), AbstractBlock.Settings.copy(Blocks.MEDIUM_AMETHYST_BUD));
    public static final Block LARGE_OPAL_BUD = registerBlock("large_opal_bud", properties -> new AmethystClusterBlock(3, 4, properties), AbstractBlock.Settings.copy(Blocks.LARGE_AMETHYST_BUD));
    public static final Block OPAL_CLUSTER = registerBlock("opal_cluster", properties -> new AmethystClusterBlock(7, 3, properties), AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER));
    public static final Block BUDDING_OPAL = registerBlock("budding_opal", properties -> new BuddingBlock(properties, SMALL_OPAL_BUD, MEDIUM_OPAL_BUD, LARGE_OPAL_BUD, OPAL_CLUSTER), AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST));

    public static final Block CLEAR_AMETHYST_BLOCK = registerBlock("clear_amethyst_block", TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque());
    public static final Block CLEAR_OPAL_BLOCK = registerBlock("clear_opal_block", TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque());

    public static final Block FREEZING_ICE = registerBlock("freezing_ice", FreezingIceBlock::new, AbstractBlock.Settings.copy(Blocks.PACKED_ICE).nonOpaque());
    public static final Block SILK_BLOCK = registerBlock("silk_block", Block::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL));
    public static final Block SILK_CARPET = registerBlock("silk_carpet", CarpetBlock::new, AbstractBlock.Settings.copy(Blocks.MOSS_CARPET).sounds(BlockSoundGroup.WOOL).velocityMultiplier(0.4F).jumpVelocityMultiplier(0.5F));
    public static final Block HANGING_SILK = registerBlock("hanging_silk", HangingSilkBlock::new, AbstractBlock.Settings.create().ticksRandomly().noCollision().burnable().strength(0.75F).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block NETHERRACK_MYCELIUM = registerBlock("netherrack_mycelium", NyliumBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM));
    public static final Block BROWN_BOUNCESHROOM = registerBlock("brown_bounceshroom", properties -> new BounceMushroomBlock(properties, Config.instance().blocks.brown_bounceshroom_bounce), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK));
    public static final Block RED_BOUNCESHROOM = registerBlock("red_bounceshroom", properties -> new BounceMushroomBlock(properties, Config.instance().blocks.red_bounceshroom_bounce), AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK));
    public static final Block BROWN_WALL_MUSHROOM = registerBlockWithoutBlockItem("brown_wall_mushroom", WallMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM));
    public static final Block RED_WALL_MUSHROOM = registerBlockWithoutBlockItem("red_wall_mushroom", WallMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM));
    public static final Block BROWN_SHELF_MUSHROOM = registerBlock("brown_shelf_mushroom", BrownShelfMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM));
    public static final Block RED_SHELF_MUSHROOM = registerBlock("red_shelf_mushroom", RedShelfMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM));
    public static final Block SPORES = registerBlock("spores", SporesBlock::new, AbstractBlock.Settings.copy(Blocks.COBWEB).strength(-1.0F).sounds(BlockSoundGroup.FROGSPAWN));

    public static final Block HEADSTONE = registerBlock("headstone", properties -> new HeadStoneBlock(WoodType.OAK, properties), AbstractBlock.Settings.create().mapColor(Blocks.STONE.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().nonOpaque().strength(1.5F, 6.0F));
    public static final Block BONE = registerBlockWithoutBlockItem("bone", BoneItemBlock::new, AbstractBlock.Settings.copy(Blocks.BONE_BLOCK));
    public static final Block BOMB_PLANT = registerBlock("bomb_plant", BombPlantBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY));
    public static final Block QUARTZ_CRYSTAL = registerBlock("quartz_crystal", properties -> new AmethystClusterBlock(7, 3, properties), AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));
    public static final Block BLACKSTONE_ROCK = registerPlaceableOnWaterBlock("blackstone_rock", CarpetBlock::new, AbstractBlock.Settings.copy(Blocks.BLACKSTONE).nonOpaque());

    public static final WoodType BURNING = WoodTypeBuilder.copyOf(WoodType.OAK).register(Identifier.of(Pyrellium.MOD_ID, "burning"), new BlockSetType("burning"));
    public static final Block BURNING_NYLIUM = registerBlock("burning_nylium", ModNyliumBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM));
    public static final Block BURNING_LEAVES = registerBlock("burning_leaves", properties -> new ModLeavesBlock(properties, 7, 0.1F), AbstractBlock.Settings.copy(Blocks.OAK_LEAVES));
    public static final Block BURNING_SAPLING = registerBlock("burning_sapling", properties -> new SaplingBlock(new ModSaplingGenerator().BURNING, properties), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
    public static final Block POTTED_BURNING_SAPLING = registerBlockWithoutBlockItem("potted_burning_sapling", properties -> new FlowerPotBlock(ModBlocks.BURNING_SAPLING, properties), AbstractBlock.Settings.copy(Blocks.POTTED_OAK_SAPLING));
    public static final Block BURNING_LOG = registerBlock("burning_log", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_STEM).sounds(BlockSoundGroup.WOOD));
    public static final Block BURNING_WOOD = registerBlock("burning_wood", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_HYPHAE).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_BURNING_LOG = registerBlock("stripped_burning_log", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_STEM).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_BURNING_WOOD = registerBlock("stripped_burning_wood", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_HYPHAE).sounds(BlockSoundGroup.WOOD));
    public static final Block BURNING_PLANKS = registerBlock("burning_planks", Block::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).sounds(BlockSoundGroup.WOOD));
    public static final Block BURNING_STAIRS = registerBlock("burning_stairs", properties -> new StairsBlock(BURNING_PLANKS.getDefaultState(), properties), AbstractBlock.Settings.copy(BURNING_PLANKS));
    public static final Block BURNING_SLAB = registerBlock("burning_slab", SlabBlock::new, AbstractBlock.Settings.copy(BURNING_PLANKS));
    public static final Block BURNING_FENCE = registerBlock("burning_fence", FenceBlock::new, AbstractBlock.Settings.copy(BURNING_PLANKS));
    public static final Block BURNING_FENCE_GATE = registerBlock("burning_fence_gate", properties -> new FenceGateBlock(BURNING, properties), AbstractBlock.Settings.copy(BURNING_PLANKS));
    public static final Block BURNING_DOOR = registerBlock("burning_door", properties -> new DoorBlock(BURNING.setType(), properties), AbstractBlock.Settings.copy(BURNING_PLANKS));
    public static final Block BURNING_TRAPDOOR = registerBlock("burning_trapdoor", properties -> new TrapdoorBlock(BURNING.setType(), properties), AbstractBlock.Settings.copy(BURNING_PLANKS).nonOpaque());
    public static final Block BURNING_PRESSURE_PLATE = registerBlock("burning_pressure_plate", properties -> new PressurePlateBlock(BURNING.setType(), properties), AbstractBlock.Settings.copy(BURNING_PLANKS).noCollision().pistonBehavior(PistonBehavior.DESTROY));
    public static final Block BURNING_BUTTON = registerBlock("burning_button", properties -> new ButtonBlock(BURNING.setType(), 30, properties), AbstractBlock.Settings.copy(BURNING_PLANKS).noCollision().pistonBehavior(PistonBehavior.DESTROY));
    public static final Block BURNING_SIGN = registerBlockWithoutBlockItem("burning_sign", properties -> new SignBlock(BURNING, properties), AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F));
    public static final Block BURNING_WALL_SIGN = registerBlockWithoutBlockItem("burning_wall_sign", properties -> new WallSignBlock(BURNING, properties), AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F).lootTable(BURNING_SIGN.getLootTableKey()));
    public static final Block BURNING_HANGING_SIGN = registerBlockWithoutBlockItem("burning_hanging_sign", properties -> new HangingSignBlock(BURNING, properties), AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F));
    public static final Block BURNING_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("burning_wall_hanging_sign", properties -> new WallHangingSignBlock(BURNING, properties), AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F).lootTable(BURNING_HANGING_SIGN.getLootTableKey()));
    public static final Block BURNING_ROOTS = registerBlock("burning_roots", RootsBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_ROOTS));
    public static final Block BURNING_SPROUTS = registerBlock("burning_sprouts", SproutsBlock::new, AbstractBlock.Settings.copy(Blocks.NETHER_SPROUTS));
    public static final Block BURNING_VINES = registerBlock("burning_vines", HangingVinesBlock::new, AbstractBlock.Settings.copy(Blocks.TWISTING_VINES));
    public static final Block PYROLILY = registerBlock("pyrolily", properties -> new BurningFlowerBlock(StatusEffects.FIRE_RESISTANCE, 5, properties), AbstractBlock.Settings.copy(Blocks.POPPY).luminance((state) -> 9));
    public static final Block POTTED_PYROLILY = registerBlockWithoutBlockItem("potted_pyrolily", properties -> new FlowerPotBlock(ModBlocks.PYROLILY, properties), AbstractBlock.Settings.copy(Blocks.POTTED_POPPY).luminance((state) -> 7));

    /// Registry order being an ass
    public static final Item BURNING_SIGN_ITEM = ModItems.registerItem("burning_sign", settings -> new SignItem(ModBlocks.BURNING_SIGN, ModBlocks.BURNING_WALL_SIGN, (settings.maxCount(16))));
    public static final Item BURNING_HANGING_SIGN_ITEM = ModItems.registerItem("burning_hanging_sign", settings -> new HangingSignItem(ModBlocks.BURNING_HANGING_SIGN, ModBlocks.BURNING_WALL_HANGING_SIGN, (settings.maxCount(16))));
    public static final Item BURNING_BOAT = ModItems.registerItem("burning_boat", settings -> new BoatItem(ModEntities.BURNING_BOAT, (settings.maxCount(1))));
    public static final Item BURNING_CHEST_BOAT = ModItems.registerItem("burning_chest_boat", settings -> new BoatItem(ModEntities.BURNING_CHEST_BOAT, (settings.maxCount(1))));

    /// Back to the blocks
    public static final WoodType SHADEROOT = WoodTypeBuilder.copyOf(WoodType.OAK).register(Identifier.of(Pyrellium.MOD_ID, "shaderoot"), new BlockSetType("shaderoot"));
    public static final Block DRAINED_SOUL_SOIL = registerBlock("drained_soul_soil", Block::new, AbstractBlock.Settings.copy(Blocks.SOUL_SOIL));
    public static final Block GHOSTLY_LEAVES = registerBlock("ghostly_leaves", properties -> new ModLeavesBlock(properties, 10, 0.1F), AbstractBlock.Settings.copy(Blocks.OAK_LEAVES));
    public static final Block FLOWERING_GHOSTLY_LEAVES = registerBlock("flowering_ghostly_leaves", properties -> new ModLeavesBlock(properties, 10, 0.1F), AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).luminance((state) -> 4));
    public static final Block SHADEROOT_SAPLING = registerBlock("shaderoot_sapling", properties -> new SaplingBlock(new ModSaplingGenerator().SHADEROOT, properties), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
    public static final Block POTTED_SHADEROOT_SAPLING = registerBlockWithoutBlockItem("potted_shaderoot_sapling", properties -> new FlowerPotBlock(ModBlocks.SHADEROOT_SAPLING, properties), AbstractBlock.Settings.copy(Blocks.POTTED_OAK_SAPLING));
    public static final Block SHADEROOT_LOG = registerBlock("shaderoot_log", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_STEM).sounds(BlockSoundGroup.WOOD));
    public static final Block SHADEROOT_WOOD = registerBlock("shaderoot_wood", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_HYPHAE).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_SHADEROOT_LOG = registerBlock("stripped_shaderoot_log", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.STRIPPED_WARPED_STEM).sounds(BlockSoundGroup.WOOD));
    public static final Block STRIPPED_SHADEROOT_WOOD = registerBlock("stripped_shaderoot_wood", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.STRIPPED_WARPED_HYPHAE).sounds(BlockSoundGroup.WOOD));
    public static final Block SHADEROOT_PLANKS = registerBlock("shaderoot_planks", Block::new, AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).sounds(BlockSoundGroup.WOOD));
    public static final Block SHADEROOT_STAIRS = registerBlock("shaderoot_stairs", properties -> new StairsBlock(SHADEROOT_PLANKS.getDefaultState(), properties), AbstractBlock.Settings.copy(SHADEROOT_PLANKS));
    public static final Block SHADEROOT_SLAB = registerBlock("shaderoot_slab", SlabBlock::new, AbstractBlock.Settings.copy(SHADEROOT_PLANKS));
    public static final Block SHADEROOT_FENCE = registerBlock("shaderoot_fence", FenceBlock::new, AbstractBlock.Settings.copy(SHADEROOT_PLANKS));
    public static final Block SHADEROOT_FENCE_GATE = registerBlock("shaderoot_fence_gate", properties -> new FenceGateBlock(SHADEROOT, properties), AbstractBlock.Settings.copy(SHADEROOT_PLANKS));
    public static final Block SHADEROOT_DOOR = registerBlock("shaderoot_door", properties -> new DoorBlock(SHADEROOT.setType(), properties), AbstractBlock.Settings.copy(SHADEROOT_PLANKS));
    public static final Block SHADEROOT_TRAPDOOR = registerBlock("shaderoot_trapdoor", properties -> new TrapdoorBlock(SHADEROOT.setType(), properties), AbstractBlock.Settings.copy(SHADEROOT_PLANKS).nonOpaque());
    public static final Block SHADEROOT_PRESSURE_PLATE = registerBlock("shaderoot_pressure_plate", properties -> new PressurePlateBlock(SHADEROOT.setType(), properties), AbstractBlock.Settings.copy(SHADEROOT_PLANKS));
    public static final Block SHADEROOT_BUTTON = registerBlock("shaderoot_button", properties -> new ButtonBlock(SHADEROOT.setType(), 30, properties), AbstractBlock.Settings.copy(SHADEROOT_PLANKS).noCollision().pistonBehavior(PistonBehavior.DESTROY));
    public static final Block SHADEROOT_SIGN = registerBlockWithoutBlockItem("shaderoot_sign", properties -> new SignBlock(SHADEROOT, properties), AbstractBlock.Settings.create().mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F));
    public static final Block SHADEROOT_WALL_SIGN = registerBlockWithoutBlockItem("shaderoot_wall_sign", properties -> new WallSignBlock(SHADEROOT, properties), AbstractBlock.Settings.create().mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F).lootTable(SHADEROOT_SIGN.getLootTableKey()));
    public static final Block SHADEROOT_HANGING_SIGN = registerBlockWithoutBlockItem("shaderoot_hanging_sign", properties -> new HangingSignBlock(SHADEROOT, properties), AbstractBlock.Settings.create().mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F));
    public static final Block SHADEROOT_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("shaderoot_wall_hanging_sign", properties -> new WallHangingSignBlock(SHADEROOT, properties), AbstractBlock.Settings.create().mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F).lootTable(SHADEROOT_HANGING_SIGN.getLootTableKey()));
    public static final Block DEAD_ROOTS = registerBlock("dead_roots", RootsBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_ROOTS));
    public static final Block DEAD_SPROUTS = registerBlock("dead_sprouts", SproutsBlock::new, AbstractBlock.Settings.copy(Blocks.NETHER_SPROUTS));
    public static final Block GHOSTLY_VINES = registerBlock("ghostly_vines", HangingVinesBlock::new, AbstractBlock.Settings.copy(Blocks.TWISTING_VINES).luminance((state) -> 4));
    public static final Block WISPBLOOM = registerBlock("wispbloom", FlowerbedBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_PETALS).luminance((state) -> 4));

    /// More Items
    public static final Item SHADEROOT_SIGN_ITEM = ModItems.registerItem("shaderoot_sign", settings -> new SignItem(ModBlocks.SHADEROOT_SIGN, ModBlocks.SHADEROOT_WALL_SIGN, (settings.maxCount(16))));
    public static final Item SHADEROOT_HANGING_SIGN_ITEM = ModItems.registerItem("shaderoot_hanging_sign", settings -> new HangingSignItem(ModBlocks.SHADEROOT_HANGING_SIGN, ModBlocks.SHADEROOT_WALL_HANGING_SIGN, (settings.maxCount(16))));
    public static final Item SHADEROOT_BOAT = ModItems.registerItem("shaderoot_boat", settings -> new BoatItem(ModEntities.SHADEROOT_BOAT, (settings.maxCount(1))));
    public static final Item SHADEROOT_CHEST_BOAT = ModItems.registerItem("shaderoot_chest_boat", settings -> new BoatItem(ModEntities.SHADEROOT_CHEST_BOAT, (settings.maxCount(1))));

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
        Block toRegister = function.apply(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Pyrellium.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(Pyrellium.MOD_ID, name), toRegister);
    }

    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
        return Registry.register(Registries.BLOCK, Identifier.of(Pyrellium.MOD_ID, name),
                function.apply(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Pyrellium.MOD_ID, name)))));
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Pyrellium.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, name)))));
    }

    private static Block registerPlaceableOnWaterBlock(String name, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
        Block toRegister = function.apply(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Pyrellium.MOD_ID, name))));
        registerPlaceableOnWaterBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(Pyrellium.MOD_ID, name), toRegister);
    }

    private static void registerPlaceableOnWaterBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Pyrellium.MOD_ID, name),
                new PlaceableOnWaterItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, name)))));
    }

    public static void registerStrippables() {
        StrippableBlockRegistry.register(BURNING_LOG, STRIPPED_BURNING_LOG);
        StrippableBlockRegistry.register(BURNING_WOOD, STRIPPED_BURNING_WOOD);
        StrippableBlockRegistry.register(SHADEROOT_LOG, STRIPPED_SHADEROOT_LOG);
        StrippableBlockRegistry.register(SHADEROOT_WOOD, STRIPPED_SHADEROOT_WOOD);
    }

    public static void registerSigns() {
        BlockEntityType.SIGN.addSupportedBlock(ModBlocks.BURNING_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(ModBlocks.BURNING_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.BURNING_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.BURNING_WALL_HANGING_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(ModBlocks.SHADEROOT_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(ModBlocks.SHADEROOT_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.SHADEROOT_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.SHADEROOT_WALL_HANGING_SIGN);
    }

    public static void registerModBlocks() {
        registerStrippables();
        registerSigns();
    }
}
