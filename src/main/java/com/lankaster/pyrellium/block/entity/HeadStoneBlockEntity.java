package com.lankaster.pyrellium.block.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.core.BlockPos;

public class HeadStoneBlockEntity extends SignBlockEntity {
    public HeadStoneBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEADSTONE.get(), pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.HEADSTONE.get();
    }
}