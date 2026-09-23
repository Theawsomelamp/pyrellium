package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.entity.ModBoatEntity;
import com.lankaster.pyrellium.entity.ModChestBoatEntity;
import com.lankaster.pyrellium.item.ModBoatItem;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.NetherSproutsBlock;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Pyrellium.MOD_ID);

    public static final DeferredBlock<Block> OPAL_BLOCK = registerBlock("opal_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK)));
    public static final DeferredBlock<Block> SMALL_OPAL_BUD = registerBlock("small_opal_bud", () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.SMALL_AMETHYST_BUD)));
    public static final DeferredBlock<Block> MEDIUM_OPAL_BUD = registerBlock("medium_opal_bud", () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD)));
    public static final DeferredBlock<Block> LARGE_OPAL_BUD = registerBlock("large_opal_bud", () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_AMETHYST_BUD)));
    public static final DeferredBlock<Block> OPAL_CLUSTER = registerBlock("opal_cluster", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER)));
    public static final DeferredBlock<Block> BUDDING_OPAL = registerBlock("budding_opal", () -> new BuddingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BUDDING_AMETHYST), SMALL_OPAL_BUD.get(), MEDIUM_OPAL_BUD.get(), LARGE_OPAL_BUD.get(), OPAL_CLUSTER.get()));

    public static final DeferredBlock<Block> CLEAR_AMETHYST_BLOCK = registerBlock("clear_amethyst_block", () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).noOcclusion()));
    public static final DeferredBlock<Block> CLEAR_OPAL_BLOCK = registerBlock("clear_opal_block", () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).noOcclusion()));
    public static final DeferredBlock<Block> SLEEPING_AMETHYST_GEODIN = registerBlockWithoutBlockItem("sleeping_amethyst_geodin", () -> new GeodinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_BASALT), Blocks.SMALL_AMETHYST_BUD, Blocks.MEDIUM_AMETHYST_BUD, Blocks.LARGE_AMETHYST_BUD, Blocks.AMETHYST_CLUSTER, ResourceLocation.fromNamespaceAndPath("minecraft", "amethyst")));
    public static final DeferredBlock<Block> SLEEPING_OPAL_GEODIN = registerBlockWithoutBlockItem("sleeping_opal_geodin", () -> new GeodinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_BASALT), SMALL_OPAL_BUD.get(), MEDIUM_OPAL_BUD.get(), LARGE_OPAL_BUD.get(), OPAL_CLUSTER.get(), ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal")));

    public static final DeferredBlock<Block> ROUGH_QUARTZ_BLOCK = registerBlock("rough_quartz_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)));
    public static final DeferredBlock<Block> SMALL_QUARTZ_BUD = registerBlock("small_quartz_bud", () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)));
    public static final DeferredBlock<Block> MEDIUM_QUARTZ_BUD = registerBlock("medium_quartz_bud", () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)));
    public static final DeferredBlock<Block> LARGE_QUARTZ_BUD = registerBlock("large_quartz_bud", () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)));
    public static final DeferredBlock<Block> QUARTZ_CRYSTAL = registerBlock("quartz_crystal", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)));
    public static final DeferredBlock<Block> BUDDING_QUARTZ = registerBlock("budding_quartz", () -> new BuddingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BUDDING_AMETHYST), SMALL_QUARTZ_BUD.get(), MEDIUM_QUARTZ_BUD.get(), LARGE_QUARTZ_BUD.get(), QUARTZ_CRYSTAL.get()));
    public static final DeferredBlock<Block> SLEEPING_QUARTZ_GEODIN = registerBlockWithoutBlockItem("sleeping_quartz_geodin", () -> new GeodinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_BASALT), SMALL_QUARTZ_BUD.get(), MEDIUM_QUARTZ_BUD.get(), LARGE_QUARTZ_BUD.get(), QUARTZ_CRYSTAL.get(), ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "quartz")));

    public static final DeferredBlock<Block> FREEZING_ICE = registerBlock("freezing_ice", () -> new FreezingIceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE).noOcclusion()));
    public static final DeferredBlock<Block> SILK_BLOCK = registerBlock("silk_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final DeferredBlock<Block> SILK_CARPET = registerBlock("silk_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).sound(SoundType.WOOL).speedFactor(0.4F).jumpFactor(0.5F)));
    public static final DeferredBlock<Block> HANGING_SILK = registerBlock("hanging_silk", () -> new HangingSilkBlock(BlockBehaviour.Properties.of().randomTicks().noCollission().ignitedByLava().strength(0.75F).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> NETHERRACK_MYCELIUM = registerBlock("netherrack_mycelium", () -> new NyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM)));
    public static final DeferredBlock<Block> BROWN_BOUNCESHROOM = registerBlock("brown_bounceshroom", () -> new BounceMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), Config.instance().blocks.brown_bounceshroom_bounce));
    public static final DeferredBlock<Block> RED_BOUNCESHROOM = registerBlock("red_bounceshroom", () -> new BounceMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK), Config.instance().blocks.red_bounceshroom_bounce));
    public static final DeferredBlock<Block> BROWN_WALL_MUSHROOM = registerBlockWithoutBlockItem("brown_wall_mushroom", () -> new WallMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));
    public static final DeferredBlock<Block> RED_WALL_MUSHROOM = registerBlockWithoutBlockItem("red_wall_mushroom", () -> new WallMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM)));
    public static final DeferredBlock<Block> BROWN_SHELF_MUSHROOM = registerBlock("brown_shelf_mushroom", () -> new BrownShelfMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)));
    public static final DeferredBlock<Block> RED_SHELF_MUSHROOM = registerBlock("red_shelf_mushroom", () -> new RedShelfMushroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM)));
    public static final DeferredBlock<Block> SPORES = registerBlock("spores", () -> new SporesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBWEB).strength(-1.0F).sound(SoundType.FROGSPAWN)));

    public static final DeferredBlock<Block> BASALT_IRON_ORE = registerBlock("basalt_iron_ore", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)));
    public static final DeferredBlock<Block> HEADSTONE = registerBlock("headstone", () -> new HeadStoneBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.STONE.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noOcclusion().strength(1.5F, 6.0F)));
    public static final DeferredBlock<Block> CHAIN_FENCE = registerBlock("chain_fence", () -> new ChainFenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS)));
    public static final DeferredBlock<Block> BONE = registerBlockWithoutBlockItem("bone", () -> new BoneItemBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final DeferredBlock<Block> BOMB_PLANT = registerBlock("bomb_plant", () -> new BombPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BLACKSTONE_ROCK = registerPlaceableOnWaterBlock("blackstone_rock", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).noOcclusion()));

    public static final DeferredBlock<Block> BURNING_NYLIUM = registerBlock("burning_nylium", () -> new ModNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM)));
    public static final DeferredBlock<Block> BURNING_LEAVES = registerBlock("burning_leaves", () -> new ModLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), 7));
    public static final DeferredBlock<Block> BURNING_SAPLING = registerBlock("burning_sapling", () -> new SaplingBlock(new ModSaplingGenerator().BURNING, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<Block> POTTED_BURNING_SAPLING = registerBlockWithoutBlockItem("potted_burning_sapling", () -> new FlowerPotBlock(ModBlocks.BURNING_SAPLING.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_OAK_SAPLING)));
    public static final DeferredBlock<Block> BURNING_LOG = registerBlock("burning_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_STEM).sound(SoundType.WOOD)) {
        @Override
        public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                         ItemAbility itemAbility, boolean simulate) {
            if(context.getItemInHand().getItem() instanceof AxeItem) {
                return ModBlocks.STRIPPED_BURNING_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            return super.getToolModifiedState(state, context, itemAbility, simulate);
        }
    });
    public static final DeferredBlock<Block> BURNING_WOOD = registerBlock("burning_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_HYPHAE).sound(SoundType.WOOD)) {
        @Override
        public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                         ItemAbility itemAbility, boolean simulate) {
            if(context.getItemInHand().getItem() instanceof AxeItem) {
                return ModBlocks.STRIPPED_BURNING_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            return super.getToolModifiedState(state, context, itemAbility, simulate);
        }
    });
    public static final DeferredBlock<Block> STRIPPED_BURNING_LOG = registerBlock("stripped_burning_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CRIMSON_STEM).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> STRIPPED_BURNING_WOOD = registerBlock("stripped_burning_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CRIMSON_HYPHAE).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BURNING_PLANKS = registerBlock("burning_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BURNING_STAIRS = registerBlock("burning_stairs", () -> new StairBlock(BURNING_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get())));
    public static final DeferredBlock<Block> BURNING_SLAB = registerBlock("burning_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get())));
    public static final DeferredBlock<Block> BURNING_FENCE = registerBlock("burning_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get())));
    public static final DeferredBlock<Block> BURNING_FENCE_GATE = registerBlock("burning_fence_gate", () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get())));
    public static final DeferredBlock<Block> BURNING_DOOR = registerBlock("burning_door", () -> new DoorBlock(WoodType.OAK.setType(), BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get())));
    public static final DeferredBlock<Block> BURNING_TRAPDOOR = registerBlock("burning_trapdoor", () -> new TrapDoorBlock(WoodType.OAK.setType(), BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get()).noOcclusion()));
    public static final DeferredBlock<Block> BURNING_PRESSURE_PLATE = registerBlock("burning_pressure_plate", () -> new PressurePlateBlock(WoodType.OAK.setType(), BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get()).noCollission().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BURNING_BUTTON = registerBlock("burning_button", () -> new ButtonBlock(WoodType.OAK.setType(), 30, BlockBehaviour.Properties.ofFullCopy(BURNING_PLANKS.get()).noCollission().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> BURNING_SIGN = registerBlockWithoutBlockItem("burning_sign", () -> new StandingSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> BURNING_WALL_SIGN = registerBlockWithoutBlockItem("burning_wall_sign", () -> new WallSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F).dropsLike(BURNING_SIGN.get())));
    public static final DeferredBlock<Block> BURNING_HANGING_SIGN = registerBlockWithoutBlockItem("burning_hanging_sign", () -> new CeilingHangingSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> BURNING_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("burning_wall_hanging_sign", () -> new WallHangingSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F).dropsLike(BURNING_HANGING_SIGN.get())));
    public static final DeferredBlock<Block> BURNING_ROOTS = registerBlock("burning_roots", () -> new RootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_ROOTS)));
    public static final DeferredBlock<Block> BURNING_SPROUTS = registerBlock("burning_sprouts", () -> new NetherSproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS)));
    public static final DeferredBlock<Block> BURNING_VINES = registerBlock("burning_vines", () -> new HangingVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES)));
    public static final DeferredBlock<Block> PYROLILY = registerBlock("pyrolily", () -> new BurningFlowerBlock(MobEffects.FIRE_RESISTANCE, 5, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).lightLevel((state) -> 9)));
    public static final DeferredBlock<Block> POTTED_PYROLILY = registerBlockWithoutBlockItem("potted_pyrolily", () -> new FlowerPotBlock(ModBlocks.PYROLILY.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY).lightLevel((state) -> 7)));

    /// Registry order being an ass
    public static final DeferredItem<Item> BURNING_SIGN_ITEM = ModItems.registerItem("burning_sign", () -> new SignItem((new Item.Properties().stacksTo(16)), ModBlocks.BURNING_SIGN.get(), ModBlocks.BURNING_WALL_SIGN.get()));
    public static final DeferredItem<Item> BURNING_HANGING_SIGN_ITEM = ModItems.registerItem("burning_hanging_sign", () -> new HangingSignItem(ModBlocks.BURNING_HANGING_SIGN.get(), ModBlocks.BURNING_WALL_HANGING_SIGN.get(), (new net.minecraft.world.item.Item.Properties().stacksTo(16))));
    public static final DeferredItem<Item> BURNING_BOAT = ModItems.registerItem("burning_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.BURNING, ModChestBoatEntity.Type.BURNING, (new net.minecraft.world.item.Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> BURNING_CHEST_BOAT = ModItems.registerItem("burning_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.BURNING, ModChestBoatEntity.Type.BURNING, (new net.minecraft.world.item.Item.Properties()).stacksTo(1)));

    /// Back to the blocks
    public static final DeferredBlock<Block> DRAINED_SOUL_SOIL = registerBlock("drained_soul_soil", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL)));
    public static final DeferredBlock<Block> GHOSTLY_LEAVES = registerBlock("ghostly_leaves", () -> new ModLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), 10));
    public static final DeferredBlock<Block> FLOWERING_GHOSTLY_LEAVES = registerBlock("flowering_ghostly_leaves", () -> new ModLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).lightLevel((state) -> 4), 10));
    public static final DeferredBlock<Block> SHADEROOT_SAPLING = registerBlock("shaderoot_sapling", () -> new SaplingBlock(new ModSaplingGenerator().SHADEROOT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<Block> POTTED_SHADEROOT_SAPLING = registerBlockWithoutBlockItem("potted_shaderoot_sapling", () -> new FlowerPotBlock(ModBlocks.SHADEROOT_SAPLING.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_OAK_SAPLING)));
    public static final DeferredBlock<Block> SHADEROOT_LOG = registerBlock("shaderoot_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_STEM).sound(SoundType.WOOD)) {
        @Override
        public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                         ItemAbility itemAbility, boolean simulate) {
            if(context.getItemInHand().getItem() instanceof AxeItem) {
                return ModBlocks.STRIPPED_SHADEROOT_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            return super.getToolModifiedState(state, context, itemAbility, simulate);
        }
    });
    public static final DeferredBlock<Block> SHADEROOT_WOOD = registerBlock("shaderoot_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_HYPHAE).sound(SoundType.WOOD)) {
        @Override
        public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context,
                                                         ItemAbility itemAbility, boolean simulate) {
            if(context.getItemInHand().getItem() instanceof AxeItem) {
                return ModBlocks.STRIPPED_SHADEROOT_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            return super.getToolModifiedState(state, context, itemAbility, simulate);
        }
    });
    public static final DeferredBlock<Block> STRIPPED_SHADEROOT_LOG = registerBlock("stripped_shaderoot_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_WARPED_STEM).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> STRIPPED_SHADEROOT_WOOD = registerBlock("stripped_shaderoot_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_WARPED_HYPHAE).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> SHADEROOT_PLANKS = registerBlock("shaderoot_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> SHADEROOT_STAIRS = registerBlock("shaderoot_stairs", () -> new StairBlock(SHADEROOT_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SHADEROOT_SLAB = registerBlock("shaderoot_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SHADEROOT_FENCE = registerBlock("shaderoot_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SHADEROOT_FENCE_GATE = registerBlock("shaderoot_fence_gate", () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SHADEROOT_DOOR = registerBlock("shaderoot_door", () -> new DoorBlock(WoodType.OAK.setType(), BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get()).noOcclusion()));
    public static final DeferredBlock<Block> SHADEROOT_TRAPDOOR = registerBlock("shaderoot_trapdoor", () -> new TrapDoorBlock(WoodType.OAK.setType(), BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get()).noOcclusion()));
    public static final DeferredBlock<Block> SHADEROOT_PRESSURE_PLATE = registerBlock("shaderoot_pressure_plate", () -> new PressurePlateBlock(WoodType.OAK.setType(), BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get())));
    public static final DeferredBlock<Block> SHADEROOT_BUTTON = registerBlock("shaderoot_button", () -> new ButtonBlock(WoodType.OAK.setType(), 30, BlockBehaviour.Properties.ofFullCopy(SHADEROOT_PLANKS.get()).noCollission().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SHADEROOT_SIGN = registerBlockWithoutBlockItem("shaderoot_sign", () -> new StandingSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> SHADEROOT_WALL_SIGN = registerBlockWithoutBlockItem("shaderoot_wall_sign", () -> new WallSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F).dropsLike(SHADEROOT_SIGN.get())));
    public static final DeferredBlock<Block> SHADEROOT_HANGING_SIGN = registerBlockWithoutBlockItem("shaderoot_hanging_sign", () -> new CeilingHangingSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F)));
    public static final DeferredBlock<Block> SHADEROOT_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("shaderoot_wall_hanging_sign", () -> new WallHangingSignBlock(WoodType.OAK, BlockBehaviour.Properties.of().mapColor(Blocks.WARPED_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().strength(1.0F).dropsLike(SHADEROOT_HANGING_SIGN.get())));
    public static final DeferredBlock<Block> DEAD_ROOTS = registerBlock("dead_roots", () -> new RootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_ROOTS)));
    public static final DeferredBlock<Block> DEAD_SPROUTS = registerBlock("dead_sprouts", () -> new NetherSproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS)));
    public static final DeferredBlock<Block> GHOSTLY_VINES = registerBlock("ghostly_vines", () -> new HangingVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).lightLevel((state) -> 4)));
    public static final DeferredBlock<Block> WISPBLOOM = registerBlock("wispbloom", () -> new PinkPetalsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).lightLevel((state) -> 4)));

    /// More Items
    public static final DeferredItem<Item> SHADEROOT_SIGN_ITEM = ModItems.registerItem("shaderoot_sign", () -> new SignItem((new net.minecraft.world.item.Item.Properties().stacksTo(16)), ModBlocks.SHADEROOT_SIGN.get(), ModBlocks.SHADEROOT_WALL_SIGN.get()));
    public static final DeferredItem<Item> SHADEROOT_HANGING_SIGN_ITEM = ModItems.registerItem("shaderoot_hanging_sign", () -> new HangingSignItem(ModBlocks.SHADEROOT_HANGING_SIGN.get(), ModBlocks.SHADEROOT_WALL_HANGING_SIGN.get(), (new net.minecraft.world.item.Item.Properties().stacksTo(16))));
    public static final DeferredItem<Item> SHADEROOT_BOAT = ModItems.registerItem("shaderoot_boat", () -> new ModBoatItem(false, ModBoatEntity.Type.SHADEROOT, ModChestBoatEntity.Type.SHADEROOT, (new net.minecraft.world.item.Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> SHADEROOT_CHEST_BOAT = ModItems.registerItem("shaderoot_chest_boat", () -> new ModBoatItem(true, ModBoatEntity.Type.SHADEROOT, ModChestBoatEntity.Type.SHADEROOT, (new net.minecraft.world.item.Item.Properties()).stacksTo(1)));

    private static <T extends Block> DeferredBlock<T> registerBlockWithoutBlockItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerPlaceableOnWaterBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerPlaceableOnWaterBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerPlaceableOnWaterBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));
    }

    public static void registerModBlocks(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
