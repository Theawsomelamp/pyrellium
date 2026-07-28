package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.GeodinBlock;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.block.entity.GeodinBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GeodinEntity extends PathAwareEntity implements VariantHolder<GeodinEntity.Variant> {
    public static final TrackedDataHandler<Identifier> GEODIN_VARIANT_IDENTIFIER = TrackedDataHandler.of(PacketByteBuf::writeIdentifier, PacketByteBuf::readIdentifier);
    private static final TrackedData<Identifier> VARIANT = DataTracker.registerData(GeodinEntity.class, GEODIN_VARIANT_IDENTIFIER);
    private static final TrackedData<Integer> CRYSTAL_AGE = DataTracker.registerData(GeodinEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private int ticksSinceGrowth;
    private int stuckTicks;

    public GeodinEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(1, new TemptGoal(this, 1.25D, Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "crystals"))), false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createGeodinAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(CRYSTAL_AGE, 0);
        this.getDataTracker().startTracking(VARIANT, Variant.AMETHYST.id);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("CrystalAge", getCrystalAge());
        nbt.putString("Variant", this.getVariant().id.toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCrystalAge(nbt.getInt("CrystalAge"));
        this.setVariant(Variant.get(new Identifier(nbt.getString("Variant"))));
    }

    @Override
    public void setVariant(Variant variant) {
        this.getDataTracker().set(VARIANT, variant.id);
    }

    @Override
    public Variant getVariant() {
        return Variant.get(this.getDataTracker().get(VARIANT));
    }

    public void setCrystalAge(int age) {
        this.getDataTracker().set(CRYSTAL_AGE, age);
    }

    public int getCrystalAge() {
        return this.getDataTracker().get(CRYSTAL_AGE);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        if (registryEntry.matchesKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "crystal_forest")))) {
            this.setVariant(Math.random() >= 0.5 ? Variant.AMETHYST : Variant.OPAL);
        } else if (registryEntry.matchesKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "quartz_caverns")))) {
            this.setVariant(Variant.QUARTZ);
        } else {
            this.setVariant(Variant.AMETHYST);
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    protected void mobTick() {
        if (this.getCrystalAge() < 4) {
            ++this.ticksSinceGrowth;
            if (this.ticksSinceGrowth % 5 == 0 && this.random.nextInt(MathHelper.clamp(6000 - this.ticksSinceGrowth, 1, 6000)) == 0) {
                this.setCrystalAge(getCrystalAge() + 1);
                this.ticksSinceGrowth = 0;
            }
        }

        ++this.stuckTicks;
        if (this.stuckTicks % 100 == 0) {
            if (canTravel()) {
                this.stuckTicks = 0;
            }

            if (this.stuckTicks >= 1200 && !isBlockAtPosSolid(this.getBlockPos())) {
                this.getWorld().setBlockState(this.getBlockPos(), getBlockForm());
                BlockEntity blockEntity = this.getWorld().getBlockEntity(this.getBlockPos());
                if (blockEntity instanceof GeodinBlockEntity geodinBlockEntity) {
                    geodinBlockEntity.setCustomName(this.getCustomName());
                }
                this.damage(this.getDamageSources().generic(), this.getHealth());
            }
        }
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isIn(ItemTags.PICKAXES)) {
            if (getCrystalAge() >= 1) {
                Block.dropStacks(getBlockFromAge(getCrystalAge()).getDefaultState(), player.getWorld(), this.getBlockPos(), null, null, player.getStackInHand(hand));
                player.getWorld().playSound(null, this.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.BLOCKS);
                this.setCrystalAge(0);
                return ActionResult.success(player.getWorld().isClient);
            }
        }
        return ActionResult.PASS;
    }

    public Block getBlockFromAge(int age) {
        Variant variant = getVariant();
        return switch (age) {
            case 1 -> variant.smallBud;
            case 2 -> variant.mediumBud;
            case 3 -> variant.largeBud;
            case 4 -> variant.cluster;
            default -> Blocks.AIR;
        };
    }

    public BlockState getBlockForm() {
        Variant variant = getVariant();
        GeodinBlock block = (GeodinBlock) Registries.BLOCK.get(new Identifier(Pyrellium.MOD_ID, "sleeping_" + variant.id.getPath() + "_geodin"));
        return block.getDefaultState().with(GeodinBlock.AGE, getCrystalAge());
    }

    private boolean canTravel() {
        BlockPos origin = this.getBlockPos();
        BlockPos.Mutable mutable = origin.mutableCopy();
        mutable.move(Direction.UP);

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = mutable.offset(direction);
            if (isBlockAtPosSolid(blockPos)) {
                return false;
            }
        }

        return true;
    }

    private boolean isBlockAtPosSolid(BlockPos pos) {
        return this.getWorld().getBlockState(pos).hasSolidTopSurface(this.getWorld().getChunkAsView(pos.getX(), pos.getY()), pos, this);
    }

    public static class Variant {
        private static final Map<Identifier, Variant> instances = new HashMap<>();

        public final Identifier id;
        public final Block smallBud;
        public final Block mediumBud;
        public final Block largeBud;
        public final Block cluster;
        public final Identifier texture;

        public static final Variant AMETHYST = registerSimple(new Identifier("minecraft", "amethyst"));
        public static final Variant OPAL = registerSimple(new Identifier(Pyrellium.MOD_ID, "opal"));
        public static final Variant QUARTZ = register(new Identifier(Pyrellium.MOD_ID, "quartz"), ModBlocks.SMALL_QUARTZ_BUD, ModBlocks.MEDIUM_QUARTZ_BUD, ModBlocks.LARGE_QUARTZ_BUD, ModBlocks.QUARTZ_CRYSTAL, new Identifier(Pyrellium.MOD_ID, "textures/entity/geodin/quartz.png"));

        protected Variant(Identifier id, Block smallBud, Block mediumBud, Block largeBud, Block cluster, Identifier texture) {
            this.id = id;
            this.smallBud = smallBud;
            this.mediumBud = mediumBud;
            this.largeBud = largeBud;
            this.cluster = cluster;
            this.texture = texture;
        }

        public static Variant registerSimple(Identifier id) {
            String blockName = id.getPath();
            Variant variant = new Variant(id,
                    Registries.BLOCK.get(new Identifier(id.getNamespace(), "small_" + blockName + "_bud")),
                    Registries.BLOCK.get(new Identifier(id.getNamespace(), "medium_" + blockName + "_bud")),
                    Registries.BLOCK.get(new Identifier(id.getNamespace(), "large_" + blockName + "_bud")),
                    Registries.BLOCK.get(new Identifier(id.getNamespace(), blockName + "_cluster")),
                    new Identifier(Pyrellium.MOD_ID, "textures/entity/geodin/" + blockName + ".png"));
            instances.put(id, variant);
            return variant;
        }

        public static Variant register(Identifier id, Block smallBud, Block mediumBud, Block largeBud, Block cluster, Identifier texture) {
            Variant variant = new Variant(id, smallBud, mediumBud, largeBud, cluster, texture);
            instances.put(id, variant);
            return variant;
        }

        public static void clear() {
            instances.clear();
        }

        public static Variant get(Identifier id) {
            return instances.getOrDefault(id, AMETHYST);
        }
    }
}
