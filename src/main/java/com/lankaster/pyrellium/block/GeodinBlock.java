package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.block.entity.GeodinBlockEntity;
import com.lankaster.pyrellium.entity.GeodinEntity;
import com.lankaster.pyrellium.entity.ModEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class GeodinBlock extends Block implements EntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
    private final Block smallBud;
    private final Block mediumBud;
    private final Block largeBud;
    private final Block cluster;
    private final ResourceLocation variant;


    public GeodinBlock(Properties settings, Block smallBud, Block mediumBud, Block largeBud, Block cluster, ResourceLocation variant) {
        super(settings);
        this.smallBud = smallBud;
        this.mediumBud = mediumBud;
        this.largeBud = largeBud;
        this.cluster = cluster;
        this.variant = variant;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 4;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = state.getValue(AGE);
        if (i < 4 && random.nextInt(5) == 0) {
            BlockState blockState = state.setValue(AGE, i + 1);
            world.setBlock(pos, blockState, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.PICKAXES)) {
            int age = state.getValue(AGE);
            if (age >= 1) {
                dropResources(getBlockFromAge(age).defaultBlockState(), world, pos, null, null, player.getItemInHand(InteractionHand.MAIN_HAND));
                world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS);
                world.setBlockAndUpdate(pos, state.setValue(AGE, 0));
            }
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        int age = state.getValue(AGE);
        dropResources(getBlockFromAge(age).defaultBlockState(), world, pos, null, null, player.getItemInHand(InteractionHand.MAIN_HAND));

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GeodinBlockEntity geodinBlockEntity) {
            GeodinEntity geodin = ModEntities.GEODIN.get().create(world);
            geodin.setVariant(GeodinEntity.Variant.get(variant));
            geodin.setPos(pos.getCenter());
            if (geodinBlockEntity.hasCustomName()) {
                geodin.setCustomName(geodinBlockEntity.getCustomName());
            }
            geodin.setPersistenceRequired();
            world.addFreshEntity(geodin);
        }
        super.playerWillDestroy(world, pos, state, player);
        return state;
    }

    private Block getBlockFromAge(int age) {
        return switch (age) {
            case 1 -> smallBud;
            case 2 -> mediumBud;
            case 3 -> largeBud;
            case 4 -> cluster;
            default -> Blocks.AIR;
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeodinBlockEntity(pos, state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}