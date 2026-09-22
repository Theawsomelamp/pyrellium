package com.lankaster.pyrellium.block.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GeodinBlockEntity extends BlockEntity implements Nameable {
    @Nullable
    private Component customName;

    public GeodinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEODIN, pos, state);
    }

    public void setCustomName(@Nullable Component customName) {
        this.customName = customName;
    }

    public Component getName() {
        return this.customName != null ? this.customName : Component.nullToEmpty("Geodin");
    }

    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Component getCustomName() {
        return this.customName;
    }
}