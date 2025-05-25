package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class ModChestBoatEntity extends ChestBoatEntity {

    private static final TrackedData<? super Integer> BOAT_TYPE = DataTracker.registerData(ModChestBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ModChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public ModChestBoatEntity(World world, double x, double y, double z) {
        this(ModEntities.BURNING_CHEST_BOAT, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOAT_TYPE, Type.BURNING.ordinal());
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("Type", Type.getType().name);
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Type", 8)) {
            this.setVariant(ModChestBoatEntity.Type.getType());
        }
    }

    public void setVariant(Type type) {
        this.dataTracker.set(BOAT_TYPE, type.ordinal());
    }

    public Item asItem() {
        return ModBlocks.BURNING_CHEST_BOAT;
    }


    public enum Type {
        BURNING(ModBlocks.BURNING_PLANKS, "burning");

        private final String name;
        private final Block baseBlock;
        Type(Block baseBlock, String string) {
            this.name = string;
            this.baseBlock = baseBlock;
        }

        public String getName() {
            return this.name;
        }

        public Block getBaseBlock() {
            return this.baseBlock;
        }

        public static ModChestBoatEntity.Type getType() {
            return BURNING;
        }
    }
}
