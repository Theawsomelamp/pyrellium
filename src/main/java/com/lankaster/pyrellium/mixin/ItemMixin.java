package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.FlowerbedBlock.FACING;
import static net.minecraft.block.FlowerbedBlock.FLOWER_AMOUNT;

@Mixin(Item.class)
public class ItemMixin {


    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void placeableBones(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        Direction direction = context.getSide();
        if (direction == Direction.UP) {
            World world = context.getWorld();
            ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
            BlockPos blockPos = itemPlacementContext.getBlockPos();
            ItemStack itemStack = context.getStack();
            BlockPos posBelow = blockPos.down();
            BlockState blockState = world.getBlockState(posBelow);
            if ((blockState.isSideSolidFullSquare(world, blockPos, direction) || (blockState.isOf(ModBlocks.BONE)) && blockState.get(FLOWER_AMOUNT) < 4) && itemStack.isOf(Items.BONE)) {
                if (world instanceof ServerWorld) {
                    if (blockState.isOf(ModBlocks.BONE)) {
                        world.setBlockState(posBelow, blockState.with(FLOWER_AMOUNT, Math.min(4, blockState.get(FLOWER_AMOUNT) + 1)));
                    } else {
                        world.setBlockState(blockPos, ModBlocks.BONE.getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite()));
                    }
                    world.playSound(null, blockPos, SoundEvents.BLOCK_BONE_BLOCK_PLACE, SoundCategory.BLOCKS);
                }
                itemStack.decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
