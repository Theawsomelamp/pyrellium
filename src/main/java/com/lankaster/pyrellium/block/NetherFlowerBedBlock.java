package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerbedBlock;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class NetherFlowerBedBlock extends FlowerbedBlock {
    public NetherFlowerBedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState floor = world.getBlockState(pos.down());
        return super.canPlaceAt(state, world, pos) || floor.isIn(TagKey.of(RegistryKeys.BLOCK, new Identifier(Pyrellium.MOD_ID, "nether_soils")));
    }
}
