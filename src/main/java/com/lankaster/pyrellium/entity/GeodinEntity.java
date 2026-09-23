package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.GeodinBlock;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.block.entity.GeodinBlockEntity;
import com.lankaster.pyrellium.config.Config;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeodinEntity extends Animal implements VariantHolder<GeodinEntity.Variant> {
    public static final EntityDataSerializer<ResourceLocation> GEODIN_VARIANT_IDENTIFIER = EntityDataSerializer.forValueType(ResourceLocation.STREAM_CODEC);
    private static final EntityDataAccessor<ResourceLocation> VARIANT = SynchedEntityData.defineId(GeodinEntity.class, GEODIN_VARIANT_IDENTIFIER);
    private static final EntityDataAccessor<Integer> CRYSTAL_AGE = SynchedEntityData.defineId(GeodinEntity.class, EntityDataSerializers.INT);

    private int ticksSinceGrowth;
    private int stuckTicks;

    public GeodinEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.25D, Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "crystals"))), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createGeodinAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.instance().entities.geodin.attributes.max_health())
                .add(Attributes.MOVEMENT_SPEED, Config.instance().entities.geodin.attributes.movement_speed())
                .add(Attributes.ARMOR, Config.instance().entities.geodin.attributes.armor());
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CRYSTAL_AGE, 0);
        builder.define(VARIANT, Variant.AMETHYST.id);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("CrystalAge", getCrystalAge());
        nbt.putString("Variant", this.getVariant().id.toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setCrystalAge(nbt.getInt("CrystalAge"));
        this.setVariant(Variant.get(ResourceLocation.parse(nbt.getString("Variant"))));
    }

    @Override
    public void setVariant(Variant variant) {
        this.getEntityData().set(VARIANT, variant.id);
    }

    @Override
    public Variant getVariant() {
        return Variant.get(this.getEntityData().get(VARIANT));
    }

    public void setCrystalAge(int age) {
        this.getEntityData().set(CRYSTAL_AGE, age);
    }

    public int getCrystalAge() {
        return this.getEntityData().get(CRYSTAL_AGE);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends Animal> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return world.getBlockState(pos.below()).is(BlockTags.SCULK_REPLACEABLE);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return world.getBlockState(pos.below()).is(BlockTags.SCULK_REPLACEABLE) ? 10.0F : -1.0F;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        Holder<Biome> registryEntry = world.getBiome(this.blockPosition());
        if (registryEntry.is(ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "crystal_forest")))) {
            this.setVariant(Math.random() >= 0.5 ? Variant.AMETHYST : Variant.OPAL);
        } else if (registryEntry.is(ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "quartz_caverns")))) {
            this.setVariant(Variant.QUARTZ);
        } else {
            this.setVariant(Util.getRandomSafe(Variant.getAll(), world.getRandom()).orElse(Variant.AMETHYST));
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return ModEntities.GEODIN.get().create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    protected void customServerAiStep() {
        if (this.getCrystalAge() < 4) {
            ++this.ticksSinceGrowth;
            if (this.ticksSinceGrowth % 5 == 0 && this.random.nextInt(Mth.clamp(6000 - this.ticksSinceGrowth, 1, 6000)) == 0) {
                this.setCrystalAge(getCrystalAge() + 1);
                this.ticksSinceGrowth = 0;
            }
        }

        ++this.stuckTicks;
        if (this.stuckTicks % 100 == 0) {
            if (canTravel()) {
                this.stuckTicks = 0;
            }

            if (this.stuckTicks >= Config.instance().entities.geodin.conversion_time && !isBlockAtPosSolid(this.blockPosition())) {
                this.level().setBlockAndUpdate(this.blockPosition(), getBlockForm());
                BlockEntity blockEntity = this.level().getBlockEntity(this.blockPosition());
                if (blockEntity instanceof GeodinBlockEntity geodinBlockEntity) {
                    geodinBlockEntity.setCustomName(this.getCustomName());
                }
                this.discard();
            }
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(ItemTags.PICKAXES)) {
            if (getCrystalAge() >= 1) {
                Block.dropResources(getBlockFromAge(getCrystalAge()).defaultBlockState(), player.level(), this.blockPosition(), null, null, player.getItemInHand(hand));
                player.level().playSound(null, this.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS);
                this.setCrystalAge(0);
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }
        return InteractionResult.PASS;
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
        GeodinBlock block = (GeodinBlock) BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "sleeping_" + variant.id.getPath() + "_geodin"));
        return block.defaultBlockState().setValue(GeodinBlock.AGE, getCrystalAge());
    }

    private boolean canTravel() {
        BlockPos origin = this.blockPosition();
        BlockPos.MutableBlockPos mutable = origin.mutable();
        mutable.move(Direction.UP);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos = mutable.relative(direction);
            if (isBlockAtPosSolid(blockPos)) {
                return false;
            }
        }

        return true;
    }

    private boolean isBlockAtPosSolid(BlockPos pos) {
        return this.level().getBlockState(pos).entityCanStandOn(this.level().getChunkForCollisions(pos.getX(), pos.getY()), pos, this);
    }

    public static class Variant {
        private static final Map<ResourceLocation, Variant> instances = new HashMap<>();

        public final ResourceLocation id;
        public final Block smallBud;
        public final Block mediumBud;
        public final Block largeBud;
        public final Block cluster;
        public final ResourceLocation texture;

        public static final Variant AMETHYST = registerSimple(ResourceLocation.fromNamespaceAndPath("minecraft", "amethyst"));
        public static final Variant OPAL = registerSimple(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal"));
        public static final Variant QUARTZ = register(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "quartz"), ModBlocks.SMALL_QUARTZ_BUD.get(), ModBlocks.MEDIUM_QUARTZ_BUD.get(), ModBlocks.LARGE_QUARTZ_BUD.get(), ModBlocks.QUARTZ_CRYSTAL.get(), ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/entity/geodin/quartz.png"));

        protected Variant(ResourceLocation id, Block smallBud, Block mediumBud, Block largeBud, Block cluster, ResourceLocation texture) {
            this.id = id;
            this.smallBud = smallBud;
            this.mediumBud = mediumBud;
            this.largeBud = largeBud;
            this.cluster = cluster;
            this.texture = texture;
        }

        public static Variant registerSimple(ResourceLocation id) {
            String blockName = id.getPath();
            Variant variant = new Variant(id,
                    BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "small_" + blockName + "_bud")),
                    BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "medium_" + blockName + "_bud")),
                    BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "large_" + blockName + "_bud")),
                    BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), blockName + "_cluster")),
                    ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/entity/geodin/" + blockName + ".png"));
            instances.put(id, variant);
            return variant;
        }

        public static Variant register(ResourceLocation id, Block smallBud, Block mediumBud, Block largeBud, Block cluster, ResourceLocation texture) {
            Variant variant = new Variant(id, smallBud, mediumBud, largeBud, cluster, texture);
            instances.put(id, variant);
            return variant;
        }

        public static void clear() {
            instances.clear();
        }

        public static Variant get(ResourceLocation id) {
            return instances.getOrDefault(id, AMETHYST);
        }

        public static List<Variant> getAll() {
            return instances.values().stream().toList();
        }
    }
}