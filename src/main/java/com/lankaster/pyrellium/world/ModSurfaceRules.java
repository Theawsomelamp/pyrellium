package com.lankaster.pyrellium.world;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.mixin.ChunkGeneratorSettingsAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRules {

    private static final MaterialRules.MaterialRule BEDROCK = MaterialRules.block(Blocks.BEDROCK.getDefaultState());
    private static final MaterialRules.MaterialRule GRAVEL = MaterialRules.block(Blocks.GRAVEL.getDefaultState());
    private static final MaterialRules.MaterialRule LAVA = MaterialRules.block(Blocks.LAVA.getDefaultState());
    private static final MaterialRules.MaterialRule BLACKSTONE = MaterialRules.block(Blocks.BLACKSTONE.getDefaultState());
    private static final MaterialRules.MaterialRule SMOOTH_BASALT = MaterialRules.block(Blocks.SMOOTH_BASALT.getDefaultState());
    private static final MaterialRules.MaterialRule FREEZING_ICE = MaterialRules.block(ModBlocks.FREEZING_ICE.getDefaultState());
    private static final MaterialRules.MaterialRule BURNING_NYLIUM = MaterialRules.block(ModBlocks.BURNING_NYLIUM.getDefaultState());
    private static final MaterialRules.MaterialRule NETHERRACK_MYCELIUM = MaterialRules.block(ModBlocks.NETHERRACK_MYCELIUM.getDefaultState());
    private static final MaterialRules.MaterialRule SOUL_SOIL = MaterialRules.block(Blocks.SOUL_SOIL.getDefaultState());
    private static final MaterialRules.MaterialRule SOUL_SAND = MaterialRules.block(Blocks.SOUL_SAND.getDefaultState());
    private static final MaterialRules.MaterialRule NETHER_WART_BLOCK = MaterialRules.block(Blocks.NETHER_WART_BLOCK.getDefaultState());
    private static final MaterialRules.MaterialRule CRIMSON_NYLIUM = MaterialRules.block(Blocks.CRIMSON_NYLIUM.getDefaultState());
    private static final MaterialRules.MaterialRule WARPED_WART_BLOCK = MaterialRules.block(Blocks.WARPED_WART_BLOCK.getDefaultState());
    private static final MaterialRules.MaterialRule WARPED_NYLIUM = MaterialRules.block(Blocks.WARPED_NYLIUM.getDefaultState());

    public static MaterialRules.MaterialRule createPyrelliumSurfaceRule(){
        MaterialRules.MaterialRule gravel = MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.PATCH, -0.012), MaterialRules.condition(MaterialRules.aboveYWithStoneDepth(YOffset.fixed(30), 0), MaterialRules.condition(MaterialRules.not(MaterialRules.aboveYWithStoneDepth(YOffset.fixed(35), 0)), GRAVEL)));

        return MaterialRules.sequence(MaterialRules.condition(MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)), BEDROCK),
                MaterialRules.condition(MaterialRules.not(MaterialRules.verticalGradient("bedrock_roof", YOffset.belowTop(5), YOffset.getTop())), BEDROCK),
                MaterialRules.condition(MaterialRules.biome(
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "frostburn_valley"))),
                        MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING_WITH_SURFACE_DEPTH,
                                MaterialRules.sequence(MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_STATE_SELECTOR, 0.0F),
                                        FREEZING_ICE), SOUL_SOIL)), MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                MaterialRules.sequence(gravel, MaterialRules.condition(
                                        MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_STATE_SELECTOR, 0.0F),
                                        FREEZING_ICE), SOUL_SOIL)))),
                MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(32), 0)),
                                MaterialRules.condition(MaterialRules.hole(), LAVA)), MaterialRules.condition(
                                MaterialRules.biome(
                                        RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "monolith_plains"))),
                                MaterialRules.condition(MaterialRules.not(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHERRACK, 0.54)),
                                        MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(31), 0), MaterialRules.sequence(
                                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_WART, 1.17),
                                                        NETHER_WART_BLOCK), CRIMSON_NYLIUM)))))),
                MaterialRules.condition(MaterialRules.biome(
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "blackstone_springs"))),
                        MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING_WITH_SURFACE_DEPTH,
                                BLACKSTONE), MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                BLACKSTONE))),
                MaterialRules.condition(MaterialRules.biome(
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "infested_valley"))),
                        MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING_WITH_SURFACE_DEPTH,
                                SOUL_SAND), MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                MaterialRules.sequence(gravel, SOUL_SAND)))),
                MaterialRules.condition(MaterialRules.biome(
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "quartz_caverns"))),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                MaterialRules.sequence(gravel, SMOOTH_BASALT))),
                MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(32), 0)),
                                MaterialRules.condition(MaterialRules.hole(), LAVA)), MaterialRules.condition(
                                MaterialRules.biome(
                                        RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "crystal_forest"))),
                                MaterialRules.condition(MaterialRules.not(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHERRACK, 0.54)),
                                        MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(31), 0), MaterialRules.sequence(
                                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_WART, 1.17),
                                                        WARPED_WART_BLOCK), WARPED_NYLIUM)))))),
                MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(32), 0)),
                                MaterialRules.condition(MaterialRules.hole(), LAVA)), MaterialRules.condition(
                                MaterialRules.biome(
                                        RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "burning_grove"))),
                                MaterialRules.sequence(MaterialRules.condition(MaterialRules.not(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHERRACK, 0.54)),
                                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_STATE_SELECTOR, -0.075F, 0.075F),
                                                        BLACKSTONE))), MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(31), 0),
                                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, BURNING_NYLIUM)))))),
                MaterialRules.condition(MaterialRules.biome(
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "ghostly_woods"))),
                        MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING_WITH_SURFACE_DEPTH,
                                SOUL_SOIL), MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                MaterialRules.sequence(gravel, SOUL_SOIL)))),
                MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(32), 0)),
                                MaterialRules.condition(MaterialRules.hole(), LAVA)), MaterialRules.condition(
                                MaterialRules.biome(
                                        RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "mushroom_wastes"))),
                                MaterialRules.condition(MaterialRules.not(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHERRACK, 0.54)),
                                        MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(31), 0), MaterialRules.sequence(
                                                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.NETHER_WART, 1.17),
                                                        WARPED_WART_BLOCK), NETHERRACK_MYCELIUM))))))
        );
    }

    public static void addModMaterialRules(MinecraftServer server, RegistryKey<DimensionOptions> dimensionKey) {
        if (FabricLoader.getInstance().isModLoaded("terrablender")) return;

        DimensionOptions levelStem = server.getCombinedDynamicRegistries().getCombinedRegistryManager()
                .get(RegistryKeys.DIMENSION).get(dimensionKey);
        if (levelStem == null) {
            Pyrellium.LOGGER.error("Couldn't find the Nether noise generation provider");
            return;
        }

        ChunkGenerator chunkGenerator = levelStem.chunkGenerator();
        boolean hasPyrelliumBiomes = chunkGenerator.getBiomeSource().getBiomes().stream().anyMatch(
                biomeHolder -> biomeHolder.getKey().orElseThrow().getValue().getNamespace().equals(Pyrellium.MOD_ID));
        if (hasPyrelliumBiomes) {
            if (chunkGenerator instanceof NoiseChunkGenerator noiseGenerator) {
                ChunkGeneratorSettings settings = noiseGenerator.getSettings().value();
                ((ChunkGeneratorSettingsAccessor) (Object) settings).addSurfaceRule(
                        MaterialRules.sequence(
                                ModSurfaceRules.createPyrelliumSurfaceRule(), settings.surfaceRule()
                        )
                );
                Pyrellium.LOGGER.info("Successfully added Pyrellium Surface Rules");
            }
        }
    }
}