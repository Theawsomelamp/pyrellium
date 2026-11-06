package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NetherFlowerBlock extends FlowerBlock {
    public NetherFlowerBlock(Holder<MobEffect> effect, float seconds, Properties properties) {
        super(effect, seconds, properties);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState floor = level.getBlockState(pos.below());
        return floor.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "nether_soils"))) || super.canSurvive(state, level, pos);
    }
}
