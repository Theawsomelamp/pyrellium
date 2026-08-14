package com.lankaster.pyrellium.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GeodinBlockEntity extends BlockEntity implements Nameable {
    @Nullable
    private Text customName;

    public GeodinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEODIN, pos, state);
    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    public Text getName() {
        return this.customName != null ? this.customName : Text.of("Geodin");
    }

    public Text getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Text getCustomName() {
        return this.customName;
    }
}