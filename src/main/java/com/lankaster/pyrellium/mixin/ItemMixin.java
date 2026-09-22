package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.PinkPetalsBlock.FACING;
import static net.minecraft.world.level.block.PinkPetalsBlock.AMOUNT;

@Mixin(Item.class)
public class ItemMixin {


    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void placeableBones(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (Config.instance().blocks.placeable_bones) {
            Direction direction = context.getClickedFace();
            if (direction == Direction.UP) {
                Level world = context.getLevel();
                BlockPlaceContext itemPlacementContext = new BlockPlaceContext(context);
                BlockPos blockPos = itemPlacementContext.getClickedPos();
                BlockState blockStateUp = world.getBlockState(blockPos);
                ItemStack itemStack = context.getItemInHand();
                BlockPos posBelow = blockPos.below();
                BlockState blockState = world.getBlockState(posBelow);
                if (((blockState.isFaceSturdy(world, blockPos, direction) && blockStateUp.isAir()) || (blockState.is(ModBlocks.BONE)) && blockState.getValue(AMOUNT) < 4) && itemStack.is(Items.BONE)) {
                    if (world instanceof ServerLevel) {
                        if (blockState.is(ModBlocks.BONE)) {
                            world.setBlockAndUpdate(posBelow, blockState.setValue(AMOUNT, Math.min(4, blockState.getValue(AMOUNT) + 1)));
                        } else {
                            world.setBlockAndUpdate(blockPos, ModBlocks.BONE.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()));
                        }
                        world.playSound(null, blockPos, SoundEvents.BONE_BLOCK_PLACE, SoundSource.BLOCKS);
                    }
                    itemStack.shrink(1);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
