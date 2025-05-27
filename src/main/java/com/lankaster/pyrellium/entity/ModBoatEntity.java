package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class ModBoatEntity extends BoatEntity {

    private static final TrackedData<? super Integer> BOAT_TYPE = DataTracker.registerData(ModBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ModBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public ModBoatEntity(World world, double x, double y, double z) {
        this(ModEntities.BURNING_BOAT, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(BOAT_TYPE, Type.BURNING.ordinal());
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("Type", Type.getType().name);
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Type", 8)) {
            this.setVariant(ModBoatEntity.Type.getType());
        }
    }

    public void setVariant(Type type) {
        this.dataTracker.set(BOAT_TYPE, type.ordinal());
    }

    public Item asItem() {
        return ModBlocks.BURNING_BOAT;
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

        public static ModBoatEntity.Type getType() {
            return BURNING;
        }
    }
}