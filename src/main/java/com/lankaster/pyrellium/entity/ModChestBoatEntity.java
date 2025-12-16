package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.world.World;

import java.util.function.IntFunction;

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
        nbt.putString("Type", this.getCustomVariant().asString());
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Type", 8)) {
            this.setVariant(ModChestBoatEntity.Type.getType(nbt.getString("Type")));
        }
    }

    public void setVariant(Type type) {
        this.dataTracker.set(BOAT_TYPE, type.ordinal());
    }

    public ModChestBoatEntity.Type getCustomVariant() {
        return ModChestBoatEntity.Type.getType((int) this.dataTracker.get(BOAT_TYPE));
    }

    public Item asItem() {
        return getCustomVariant().getBaseItem();
    }


    public enum Type implements StringIdentifiable {
        BURNING(ModBlocks.BURNING_CHEST_BOAT, "burning");

        private final String name;
        private final Item baseItem;
        public static final StringIdentifiable.Codec<ModChestBoatEntity.Type> CODEC = StringIdentifiable.createCodec(ModChestBoatEntity.Type::values);
        private static final IntFunction<ModChestBoatEntity.Type> BY_ID = ValueLists.createIdToValueFunction(Enum::ordinal, values(), BURNING);

        Type(Item baseItem, String string) {
            this.name = string;
            this.baseItem = baseItem;
        }

        public String asString() {
            return this.name;
        }

        public Item getBaseItem() {
            return this.baseItem;
        }

        public static ModChestBoatEntity.Type getType(int type) {
            return BY_ID.apply(type);
        }

        public static ModChestBoatEntity.Type getType(String id) {
            return CODEC.byId(id, BURNING);
        }
    }
}
