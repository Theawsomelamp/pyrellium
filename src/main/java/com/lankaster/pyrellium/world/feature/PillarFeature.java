package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<PillarFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        PillarFeatureConfig pillarFeatureConfig = context.getConfig();
        Random random = context.getRandom();
        BlockStateProvider state = pillarFeatureConfig.toPlace();
        BlockState blockState = state.get(random, blockPos);
        if (!PillarHelper.canGenerate(structureWorldAccess, blockPos)) {
            return false;
        } else {
            Optional<CaveSurface> optional = CaveSurface.create(structureWorldAccess, blockPos, pillarFeatureConfig.floorToCeilingSearchRange(), PillarHelper::canGenerate, PillarHelper::canReplaceOrLava);
            if (optional.isPresent() && (optional.get() instanceof CaveSurface.Bounded bounded)) {
                if (bounded.getHeight() < 4) {
                    return false;
                } else {
                    int i = (int)((float)bounded.getHeight() * pillarFeatureConfig.maxColumnRadiusToCaveHeightRatio());
                    int j = MathHelper.clamp(i, pillarFeatureConfig.columnRadius().getMin(), pillarFeatureConfig.columnRadius().getMax());
                    int k = MathHelper.nextBetween(random, pillarFeatureConfig.columnRadius().getMin(), j);
                    PillarFeature.DripstoneGenerator dripstoneGenerator = createGenerator(blockPos.withY(bounded.getCeiling() - 1), false, random, k, pillarFeatureConfig.stalactiteBluntness(), pillarFeatureConfig.heightScale(), blockState);
                    PillarFeature.DripstoneGenerator dripstoneGenerator2 = createGenerator(blockPos.withY(bounded.getFloor() + 1), true, random, k, pillarFeatureConfig.stalagmiteBluntness(), pillarFeatureConfig.heightScale(), blockState);
                    PillarFeature.WindModifier windModifier;
                    if (dripstoneGenerator.generateWind(pillarFeatureConfig) && dripstoneGenerator2.generateWind(pillarFeatureConfig)) {
                        windModifier = new PillarFeature.WindModifier(blockPos.getY(), random, pillarFeatureConfig.windSpeed());
                    } else {
                        windModifier = PillarFeature.WindModifier.create();
                    }

                    boolean bl = dripstoneGenerator.canGenerate(structureWorldAccess, windModifier);
                    boolean bl2 = dripstoneGenerator2.canGenerate(structureWorldAccess, windModifier);
                    if (bl) {
                        dripstoneGenerator.generate(structureWorldAccess, random, windModifier);
                    }

                    if (bl2) {
                        dripstoneGenerator2.generate(structureWorldAccess, random, windModifier);
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    private static PillarFeature.DripstoneGenerator createGenerator(BlockPos pos, boolean isStalagmite, Random random, int scale, FloatProvider bluntness, FloatProvider heightScale, BlockState toPlace) {
        return new PillarFeature.DripstoneGenerator(pos, isStalagmite, scale, (double)bluntness.get(random), (double)heightScale.get(random), toPlace);
    }

    static final class DripstoneGenerator {
        private BlockPos pos;
        private final boolean isStalagmite;
        private int scale;
        private final double bluntness;
        private final double heightScale;
        private final BlockState toPlace;

        DripstoneGenerator(BlockPos pos, boolean isStalagmite, int scale, double bluntness, double heightScale, BlockState toPlace) {
            this.pos = pos;
            this.isStalagmite = isStalagmite;
            this.scale = scale;
            this.bluntness = bluntness;
            this.heightScale = heightScale;
            this.toPlace = toPlace;
        }

        private int getBaseScale() {
            return this.scale(0.0F);
        }

        boolean canGenerate(StructureWorldAccess world, PillarFeature.WindModifier wind) {
            while(this.scale > 1) {
                BlockPos.Mutable mutable = this.pos.mutableCopy();
                int i = Math.min(10, this.getBaseScale());

                for(int j = 0; j < i; ++j) {
                    if (world.getBlockState(mutable).isOf(Blocks.LAVA)) {
                        return false;
                    }

                    if (PillarHelper.canGenerateBase(world, wind.modify(mutable), this.scale)) {
                        this.pos = mutable;
                        return true;
                    }

                    mutable.move(this.isStalagmite ? Direction.DOWN : Direction.UP);
                }

                this.scale /= 2;
            }

            return false;
        }

        private int scale(float height) {
            return (int)PillarHelper.scaleHeightFromRadius((double)height, (double)this.scale, this.heightScale, this.bluntness);
        }

        void generate(StructureWorldAccess world, Random random, PillarFeature.WindModifier wind) {
            for(int i = -this.scale; i <= this.scale; ++i) {
                for(int j = -this.scale; j <= this.scale; ++j) {
                    float f = MathHelper.sqrt((float)(i * i + j * j));
                    if (!(f > (float)this.scale)) {
                        int k = this.scale(f);
                        if (k > 0) {
                            if ((double)random.nextFloat() < 0.2) {
                                k = (int)((float)k * MathHelper.nextBetween(random, 0.8F, 1.0F));
                            }

                            BlockPos.Mutable mutable = this.pos.add(i, 0, j).mutableCopy();
                            boolean bl = false;
                            int l = this.isStalagmite ? world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;

                            for(int m = 0; m < k && mutable.getY() < l; ++m) {
                                BlockPos blockPos = wind.modify(mutable);
                                if (PillarHelper.canGenerateOrLava(world, blockPos)) {
                                    bl = true;
                                    world.setBlockState(blockPos, toPlace, 2);
                                } else if (bl && !world.getBlockState(blockPos).isAir()) {
                                    break;
                                }

                                mutable.move(this.isStalagmite ? Direction.UP : Direction.DOWN);
                            }
                        }
                    }
                }
            }

        }

        boolean generateWind(PillarFeatureConfig config) {
            return this.scale >= config.minRadiusForWind() && this.bluntness >= (double) config.minBluntnessForWind();
        }
    }

    static final class WindModifier {
        private final int y;
        @Nullable
        private final Vec3d wind;

        WindModifier(int y, Random random, FloatProvider wind) {
            this.y = y;
            float f = wind.get(random);
            float g = MathHelper.nextBetween(random, 0.0F, (float)Math.PI);
            this.wind = new Vec3d((double)(MathHelper.cos(g) * f), (double)0.0F, (double)(MathHelper.sin(g) * f));
        }

        private WindModifier() {
            this.y = 0;
            this.wind = null;
        }

        static PillarFeature.WindModifier create() {
            return new PillarFeature.WindModifier();
        }

        BlockPos modify(BlockPos pos) {
            if (this.wind == null) {
                return pos;
            } else {
                int i = this.y - pos.getY();
                Vec3d vec3d = this.wind.multiply((double)i);
                return pos.add(MathHelper.floor(vec3d.x), 0, MathHelper.floor(vec3d.z));
            }
        }
    }
}
