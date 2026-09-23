package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.level.Level;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class ModChestBoatEntity extends ChestBoat {

    private static final EntityDataAccessor<? super Integer> BOAT_TYPE = SynchedEntityData.defineId(ModChestBoatEntity.class, EntityDataSerializers.INT);

    public ModChestBoatEntity(EntityType<? extends Boat> entityType, Level world) {
        super(entityType, world);
    }

    public ModChestBoatEntity(Level world, double x, double y, double z) {
        this(ModEntities.BURNING_CHEST_BOAT.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOAT_TYPE, Type.BURNING.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putString("Type", this.getCustomVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag nbt) {
        if (nbt.contains("Type", 8)) {
            this.setVariant(ModChestBoatEntity.Type.getType(nbt.getString("Type")));
        }
    }

    public void setVariant(Type type) {
        this.entityData.set(BOAT_TYPE, type.ordinal());
    }

    public ModChestBoatEntity.Type getCustomVariant() {
        return ModChestBoatEntity.Type.getType((int) this.entityData.get(BOAT_TYPE));
    }

    public Item getDropItem() {
        return getCustomVariant().getBaseItem();
    }


    public enum Type implements StringRepresentable {
        BURNING(ModBlocks.BURNING_CHEST_BOAT, "burning"),
        SHADEROOT(ModBlocks.SHADEROOT_CHEST_BOAT, "shaderoot");

        private final String name;
        private final Supplier<Item> baseItem;
        public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(ModChestBoatEntity.Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.sparse(Enum::ordinal, values(), BURNING);

        Type(Supplier<Item> baseItem, String string) {
            this.name = string;
            this.baseItem = baseItem;
        }

        public String getSerializedName() {
            return this.name;
        }

        public Item getBaseItem() {
            return this.baseItem.get();
        }

        public static ModChestBoatEntity.Type getType(int type) {
            return BY_ID.apply(type);
        }

        public static ModChestBoatEntity.Type getType(String id) {
            return CODEC.byName(id, BURNING);
        }
    }
}