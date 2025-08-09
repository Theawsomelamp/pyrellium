package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.FlowerbedBlock.FACING;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void wallMushrooms(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        Direction direction = context.getSide();
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        BlockState blockStateInit = world.getBlockState(blockPos);
        ItemStack itemStack = context.getStack();
        if (direction != Direction.DOWN && direction != Direction.UP){
            BlockPos posSide = blockPos.offset(direction.getOpposite());
            BlockState blockState = world.getBlockState(posSide);
            BlockPos posDown = blockPos.offset(Direction.DOWN);
            BlockState blockStateDown = world.getBlockState(posDown);
            if ((blockState.isSideSolidFullSquare(world, blockPos, direction) && blockStateInit.isAir()) && !blockStateDown.isSideSolidFullSquare(world, blockPos, Direction.DOWN) ) {
                if (world instanceof ServerWorld) {
                    if (itemStack.isOf(Items.BROWN_MUSHROOM)) {
                        world.setBlockState(blockPos, ModBlocks.BROWN_WALL_MUSHROOM.getDefaultState().with(FACING, direction.getOpposite()));
                        world.playSound(null, blockPos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS);
                        itemStack.decrement(1);
                        cir.setReturnValue(ActionResult.SUCCESS);
                    } else if (itemStack.isOf(Items.RED_MUSHROOM)) {
                        world.setBlockState(blockPos, ModBlocks.RED_WALL_MUSHROOM.getDefaultState().with(FACING, direction.getOpposite()));
                        world.playSound(null, blockPos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS);
                        itemStack.decrement(1);
                        cir.setReturnValue(ActionResult.SUCCESS);
                    }
                }
            }
        }

        if (itemStack.isOf(ModBlocks.SPORES.asItem())) {
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
            }
        }
    }
}
