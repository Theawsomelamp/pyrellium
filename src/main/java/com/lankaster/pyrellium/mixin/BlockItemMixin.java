package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.config.Config;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.PinkPetalsBlock.FACING;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void wallMushrooms(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Direction direction = context.getClickedFace();
        Level world = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        BlockPlaceContext itemPlacementContext = new BlockPlaceContext(context);
        BlockPos blockPos = itemPlacementContext.getClickedPos();
        BlockState blockStateInit = world.getBlockState(blockPos);
        ItemStack itemStack = context.getItemInHand();
        if (Config.instance().blocks.placeable_wall_mushrooms) {
            if (direction != Direction.DOWN && direction != Direction.UP) {
                BlockPos posSide = blockPos.relative(direction.getOpposite());
                BlockState blockState = world.getBlockState(posSide);
                BlockPos posDown = blockPos.relative(Direction.DOWN);
                BlockState blockStateDown = world.getBlockState(posDown);
                if ((blockState.isFaceSturdy(world, blockPos, direction) && blockStateInit.isAir()) && !blockStateDown.isFaceSturdy(world, blockPos, Direction.DOWN)) {
                    if (world instanceof ServerLevel) {
                        if (itemStack.is(Items.BROWN_MUSHROOM)) {
                            world.setBlockAndUpdate(blockPos, ModBlocks.BROWN_WALL_MUSHROOM.defaultBlockState().setValue(FACING, direction.getOpposite()));
                            world.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS);
                            itemStack.shrink(1);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                        } else if (itemStack.is(Items.RED_MUSHROOM)) {
                            world.setBlockAndUpdate(blockPos, ModBlocks.RED_WALL_MUSHROOM.defaultBlockState().setValue(FACING, direction.getOpposite()));
                            world.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS);
                            itemStack.shrink(1);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                        }
                    }
                }
            }
        }

        if (itemStack.is(ModBlocks.SPORES.asItem())) {
            if (itemStack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
        }
    }
}
