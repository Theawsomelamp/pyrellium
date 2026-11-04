package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class ModRootsBlock extends PlantBlock {
    public static final MapCodec<ModRootsBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(createSettingsCodec(), Codec.BOOL.fieldOf("sprouts").forGetter(ModRootsBlock::getSprouts)).apply(instance, ModRootsBlock::new));
    protected static final VoxelShape ROOTS_SHAPE = Block.createCuboidShape(2.0F, 0.0F, 2.0F, 14.0F, 13.0F, 14.0F);
    protected static final VoxelShape SPROUTS_SHAPE = Block.createCuboidShape(2.0F, 0.0F, 2.0F, 14.0F, 3.0F, 14.0F);
    private final boolean sprouts;

    public ModRootsBlock(Settings settings, boolean sprouts) {
        super(settings);
        this.sprouts = sprouts;
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return sprouts ? SPROUTS_SHAPE : ROOTS_SHAPE;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(Pyrellium.MOD_ID, "nether_soils"))) || super.canPlantOnTop(floor, world, pos);
    }

    public boolean getSprouts() {
        return this.sprouts;
    }
}