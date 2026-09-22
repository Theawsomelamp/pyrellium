package com.lankaster.pyrellium.world.feature;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;

public class PillarHelper {
    public PillarHelper() {
    }

    protected static double scaleHeightFromRadius(double radius, double scale, double heightScale, double bluntness) {
        if (radius < bluntness) {
            radius = bluntness;
        }

        double e = radius / scale * 0.384;
        double f = (double)0.75F * Math.pow(e, 1.3333333333333333);
        double g = Math.pow(e, 0.6666666666666666);
        double h = 0.3333333333333333 * Math.log(e);
        double i = heightScale * (f - g - h);
        i = Math.max(i, (double)0.0F);
        return i / 0.384 * scale;
    }

    protected static boolean canGenerateBase(WorldGenLevel world, BlockPos pos, int height) {
        if (canGenerateOrLava(world, pos)) {
            return false;
        } else {
            float g = 6.0F / (float)height;

            for(float h = 0.0F; h < ((float)Math.PI * 2F); h += g) {
                int i = (int)(Mth.cos(h) * (float)height);
                int j = (int)(Mth.sin(h) * (float)height);
                if (canGenerateOrLava(world, pos.offset(i, 0, j))) {
                    return false;
                }
            }

            return true;
        }
    }

    protected static boolean canGenerate(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, PillarHelper::canGenerate);
    }

    protected static boolean canGenerateOrLava(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, PillarHelper::canGenerateOrLava);
    }

    public static boolean canReplaceOrLava(BlockState state) {
        return canReplace(state) || state.is(Blocks.LAVA);
    }

    //TODO: add tag to check for certain block
    public static boolean canReplace(BlockState state) {
        return !state.isAir();
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    public static boolean canGenerateOrLava(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
    }
}
