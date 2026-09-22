package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean place(FeaturePlaceContext<PillarFeatureConfig> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        PillarFeatureConfig pillarFeatureConfig = context.config();
        RandomSource random = context.random();
        BlockStateProvider state = pillarFeatureConfig.toPlace();
        BlockState blockState = state.getState(random, blockPos);
        if (!PillarHelper.canGenerate(structureWorldAccess, blockPos)) {
            return false;
        } else {
            Optional<Column> optional = Column.scan(structureWorldAccess, blockPos, pillarFeatureConfig.floorToCeilingSearchRange(), PillarHelper::canGenerate, PillarHelper::canReplaceOrLava);
            if (optional.isPresent() && (optional.get() instanceof Column.Range bounded)) {
                if (bounded.height() < 4) {
                    return false;
                } else {
                    int i = (int)((float)bounded.height() * pillarFeatureConfig.maxColumnRadiusToCaveHeightRatio());
                    int j = Mth.clamp(i, pillarFeatureConfig.columnRadius().getMinValue(), pillarFeatureConfig.columnRadius().getMaxValue());
                    int k = Mth.randomBetweenInclusive(random, pillarFeatureConfig.columnRadius().getMinValue(), j);
                    PillarFeature.DripstoneGenerator dripstoneGenerator = createGenerator(blockPos.atY(bounded.ceiling() - 1), false, random, k, pillarFeatureConfig.stalactiteBluntness(), pillarFeatureConfig.heightScale(), blockState);
                    PillarFeature.DripstoneGenerator dripstoneGenerator2 = createGenerator(blockPos.atY(bounded.floor() + 1), true, random, k, pillarFeatureConfig.stalagmiteBluntness(), pillarFeatureConfig.heightScale(), blockState);
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

    private static PillarFeature.DripstoneGenerator createGenerator(BlockPos pos, boolean isStalagmite, RandomSource random, int scale, FloatProvider bluntness, FloatProvider heightScale, BlockState toPlace) {
        return new PillarFeature.DripstoneGenerator(pos, isStalagmite, scale, (double)bluntness.sample(random), (double)heightScale.sample(random), toPlace);
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

        boolean canGenerate(WorldGenLevel world, PillarFeature.WindModifier wind) {
            while(this.scale > 1) {
                BlockPos.MutableBlockPos mutable = this.pos.mutable();
                int i = Math.min(10, this.getBaseScale());

                for(int j = 0; j < i; ++j) {
                    if (world.getBlockState(mutable).is(Blocks.LAVA)) {
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

        void generate(WorldGenLevel world, RandomSource random, PillarFeature.WindModifier wind) {
            for(int i = -this.scale; i <= this.scale; ++i) {
                for(int j = -this.scale; j <= this.scale; ++j) {
                    float f = Mth.sqrt((float)(i * i + j * j));
                    if (!(f > (float)this.scale)) {
                        int k = this.scale(f);
                        if (k > 0) {
                            if ((double)random.nextFloat() < 0.2) {
                                k = (int)((float)k * Mth.randomBetween(random, 0.8F, 1.0F));
                            }

                            BlockPos.MutableBlockPos mutable = this.pos.offset(i, 0, j).mutable();
                            boolean bl = false;
                            int l = this.isStalagmite ? world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;

                            for(int m = 0; m < k && mutable.getY() < l; ++m) {
                                BlockPos blockPos = wind.modify(mutable);
                                if (PillarHelper.canGenerateOrLava(world, blockPos)) {
                                    bl = true;
                                    world.setBlock(blockPos, toPlace, 2);
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
        private final Vec3 wind;

        WindModifier(int y, RandomSource random, FloatProvider wind) {
            this.y = y;
            float f = wind.sample(random);
            float g = Mth.randomBetween(random, 0.0F, (float)Math.PI);
            this.wind = new Vec3((double)(Mth.cos(g) * f), (double)0.0F, (double)(Mth.sin(g) * f));
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
                Vec3 vec3d = this.wind.scale((double)i);
                return pos.offset(Mth.floor(vec3d.x), 0, Mth.floor(vec3d.z));
            }
        }
    }
}
