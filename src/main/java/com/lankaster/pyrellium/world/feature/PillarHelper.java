package com.lankaster.pyrellium.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;

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

    protected static boolean canGenerateBase(StructureWorldAccess world, BlockPos pos, int height) {
        if (canGenerateOrLava(world, pos)) {
            return false;
        } else {
            float g = 6.0F / (float)height;

            for(float h = 0.0F; h < ((float)Math.PI * 2F); h += g) {
                int i = (int)(MathHelper.cos(h) * (float)height);
                int j = (int)(MathHelper.sin(h) * (float)height);
                if (canGenerateOrLava(world, pos.add(i, 0, j))) {
                    return false;
                }
            }

            return true;
        }
    }

    protected static boolean canGenerate(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, PillarHelper::canGenerate);
    }

    protected static boolean canGenerateOrLava(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, PillarHelper::canGenerateOrLava);
    }

    public static boolean canReplaceOrLava(BlockState state) {
        return canReplace(state) || state.isOf(Blocks.LAVA);
    }

    //TODO: add tag to check for certain block
    public static boolean canReplace(BlockState state) {
        return !state.isAir();
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }

    public static boolean canGenerateOrLava(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA);
    }
}
