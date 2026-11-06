package com.lankaster.pyrellium.block;


import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModRootsBlock extends BushBlock {
    public static final MapCodec<ModRootsBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(propertiesCodec(), Codec.BOOL.fieldOf("sprouts").forGetter(ModRootsBlock::getSprouts)).apply(instance, ModRootsBlock::new));
    protected static final VoxelShape ROOTS_SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 13.0F, 14.0F);
    protected static final VoxelShape SPROUTS_SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 3.0F, 14.0F);
    private final boolean sprouts;

    protected ModRootsBlock(Properties properties, boolean sprouts) {
        super(properties);
        this.sprouts = sprouts;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return sprouts ? SPROUTS_SHAPE : ROOTS_SHAPE;
    }

    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "nether_soils"))) || super.mayPlaceOn(floor, level, pos);
    }

    public boolean getSprouts() {
        return this.sprouts;
    }
}