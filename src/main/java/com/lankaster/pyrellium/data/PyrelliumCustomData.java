package com.lankaster.pyrellium.data;

import com.google.gson.*;
import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.config.ConfigHandler;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class PyrelliumCustomData {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public final ResourceLocation target;
    public Function<JsonElement, String> provider;
    public final Supplier<Boolean> enabled;

    public PyrelliumCustomData(ResourceLocation target, Supplier<Boolean> enabled, Function<JsonElement, String> provider) {
        this.target = target;
        this.provider = provider;
        this.enabled = enabled;
    }

    public String apply(@Nullable String original){
        return gson.fromJson(this.provider.apply(gson.fromJson(original == null ? "" : original, JsonElement.class)), JsonElement.class).toString();
    }

    public static List<PyrelliumCustomData> INSTANCES = new LinkedList<>();
    private static final List<Tuple<ResourceLocation, JsonObject>> SURFACE_RULES = new ArrayList<>();
    private static final List<Tuple<ResourceLocation, JsonObject>> BIOMES_NOISE = new ArrayList<>();

    public static @Nullable PyrelliumCustomData get(ResourceLocation id) {
        for (PyrelliumCustomData data : INSTANCES) {
            if (data.target.equals(id)) return data;
        }
        return null;
    }

    public static List<Tuple<ResourceLocation, JsonObject>> getSurfaceRules() {
        return SURFACE_RULES;
    }

    public static void clearSurfaceRules() {
        SURFACE_RULES.clear();
    }

    public static List<Tuple<ResourceLocation, JsonObject>> getBiomesNoise() {
        return BIOMES_NOISE;
    }

    public static void clearBiomeNoise() {
        BIOMES_NOISE.clear();
    }

    protected static void register(ResourceLocation target, Supplier<Boolean> enabled, Function<JsonElement, String> provider) {
        INSTANCES.add(new PyrelliumCustomData(target, enabled, provider));
    }

    public static void register() {
        register(ResourceLocation.fromNamespaceAndPath("minecraft", "worldgen/noise_settings/nether.json"), () -> true, PyrelliumCustomData::changeNoiseRouter);

        // Jadens Nether Expansion crashes with pyrellium cause the way they handle dimension gen
        // Luckily the invasiveness also automatically places the pyrellium biomes, though at the cost of ignoring the config
        if (!ModList.get().isLoaded("netherexp")) {
            register(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension/the_nether.json"), () -> true, PyrelliumCustomData::changeBiomeNoise);
        }
    }

    private static JsonElement getJson(String string) {
        return gson.fromJson(string, JsonElement.class);
    }

    public static void read(ResourceManager manager) {
        for (Map.Entry<ResourceLocation, Resource> entry : manager.listResources("worldgen/surface_rules",
                path -> path.toString().endsWith(".json")).entrySet()) {

            try {
                String content = new String(entry.getValue().open().readAllBytes());
                JsonElement json = gson.fromJson(content, JsonElement.class);

                if (json == null || !json.isJsonObject()) continue;

                addSurfaceRules(json.getAsJsonObject());
            }
            catch (Throwable e) {
                Pyrellium.LOGGER.warn("Could not read data file {}", entry.getKey());
            }
        }

        for (Map.Entry<ResourceLocation, Resource> entry : manager.listResources("worldgen/biome_noise",
                path -> path.toString().endsWith(".json")).entrySet()) {

            try {
                String content = new String(entry.getValue().open().readAllBytes());
                JsonElement json = gson.fromJson(content, JsonElement.class);

                if (json == null || !json.isJsonObject()) continue;

                addBiomeNoise(json.getAsJsonObject());
            }
            catch (Throwable e) {
                Pyrellium.LOGGER.warn("Could not read data file {}", entry.getKey());
            }
        }
    }

    public static void addSurfaceRules(JsonObject json) {
        if (!json.has("dimension") || !json.has("surface_rule")) return;
        SURFACE_RULES.add(new Tuple<>(ResourceLocation.parse(json.get("dimension").getAsString()), json.get("surface_rule").getAsJsonObject()));
    }

    public static void addBiomeNoise(JsonObject json) {
        if (!json.has("biome") || !json.has("parameters")) return;
        BIOMES_NOISE.add(new Tuple<>(ResourceLocation.parse(json.get("biome").getAsString()), json.get("parameters").getAsJsonObject()));
    }

    public static boolean detectModification(JsonElement json) {
        JsonElement defaultNoise = getJson("""
                {"height":128,"min_y":0,"size_horizontal":1,"size_vertical":2}""");
        JsonElement defaultRouter = getJson("""
                {"barrier":0.0,"continents":0.0,"depth":0.0,"erosion":0.0,"final_density":{"type":"minecraft:squeeze","argument":{"type":"minecraft:mul","argument1":0.64,"argument2":{"type":"minecraft:interpolated","argument":{"type":"minecraft:blend_density","argument":{"type":"minecraft:add","argument1":2.5,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":0.0,"from_y":-8,"to_value":1.0,"to_y":24},"argument2":{"type":"minecraft:add","argument1":-2.5,"argument2":{"type":"minecraft:add","argument1":0.9375,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":1.0,"from_y":104,"to_value":0.0,"to_y":128},"argument2":{"type":"minecraft:add","argument1":-0.9375,"argument2":"minecraft:nether/base_3d_noise"}}}}}}}}}},"fluid_level_floodedness":0.0,"fluid_level_spread":0.0,"initial_density_without_jaggedness":0.0,"lava":0.0,"ridges":0.0,"temperature":{"type":"minecraft:shifted_noise","noise":"minecraft:temperature","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vegetation":{"type":"minecraft:shifted_noise","noise":"minecraft:vegetation","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vein_gap":0.0,"vein_ridged":0.0,"vein_toggle":0.0}""");
        return (!json.getAsJsonObject().get("noise").equals(defaultNoise)) || (!json.getAsJsonObject().get("noise_router").equals(defaultRouter));
    }

    public static String changeBiomeNoise(JsonElement json) {
        if (json == null) {
            json = getJson("""
                    {
                      "type": "minecraft:the_nether",
                      "generator": {
                        "type": "minecraft:noise",
                        "biome_source": {
                          "biomes": [
                            {
                              "biome": "minecraft:nether_wastes",
                              "parameters": {
                                "continentalness": 0,
                                "depth": 0,
                                "erosion": 0,
                                "humidity": 0,
                                "offset": 0,
                                "temperature": 0,
                                "weirdness": 0
                              }
                            },
                            {
                              "biome": "minecraft:soul_sand_valley",
                              "parameters": {
                                "continentalness": 0,
                                "depth": 0,
                                "erosion": 0,
                                "humidity": -0.5,
                                "offset": 0,
                                "temperature": 0,
                                "weirdness": 0
                              }
                            },
                            {
                              "biome": "minecraft:crimson_forest",
                              "parameters": {
                                "continentalness": 0,
                                "depth": 0,
                                "erosion": 0,
                                "humidity": 0,
                                "offset": 0,
                                "temperature": 0.4,
                                "weirdness": 0
                              }
                            },
                            {
                              "biome": "minecraft:warped_forest",
                              "parameters": {
                                "continentalness": 0,
                                "depth": 0,
                                "erosion": 0,
                                "humidity": 0.5,
                                "offset": 0.375,
                                "temperature": 0,
                                "weirdness": 0
                              }
                            },
                            {
                              "biome": "minecraft:basalt_deltas",
                              "parameters": {
                                "continentalness": 0,
                                "depth": 0,
                                "erosion": 0,
                                "humidity": 0,
                                "offset": 0.175,
                                "temperature": -0.5,
                                "weirdness": 0
                              }
                            }
                          ],
                          "type": "minecraft:multi_noise"
                        },
                        "settings": "minecraft:nether"
                      }
                    }""");
        }

        JsonArray baseNoise = json.getAsJsonObject().get("generator").getAsJsonObject().get("biome_source").getAsJsonObject().get("biomes").getAsJsonArray();
        JsonArray sequence = new JsonArray();

        for (Tuple<ResourceLocation, JsonObject> noise : getBiomesNoise()) {
            String config = ConfigHandler.biomesToJson(Config.instance().biomes);
            if (getJson(config).getAsJsonObject().get(noise.getA().getPath()).getAsJsonObject().get("enable_biome").getAsBoolean()) {
                JsonObject biome = getJson("{\"biome\": \"" + noise.getA().toString() + "\"}").getAsJsonObject();
                biome.add("parameters", noise.getB());
                sequence.add(biome);
            }
        }
        sequence.addAll(baseNoise);

        JsonObject source = getJson("""
                    {
                      "type": "minecraft:multi_noise"
                    }""").getAsJsonObject();
        source.add("biomes", sequence);
        json.getAsJsonObject().get("generator").getAsJsonObject().asMap().replace("biome_source", source);

        return gson.toJson(json);
    }

    public static String changeNoiseRouter(JsonElement json) {
        if (Config.instance().globalFeatures.raised_nether_height && !detectModification(json)) {
            json.getAsJsonObject().asMap().replace("noise", getJson("""
                    { "min_y": 0, "height": 192, "size_horizontal": 1, "size_vertical": 2 }"""));

            json.getAsJsonObject().get("noise_router").getAsJsonObject().asMap().replace("final_density", new JsonPrimitive("pyrellium:final_density"));
        }

        JsonObject baseRules = json.getAsJsonObject().get("surface_rule").getAsJsonObject();
        JsonArray sequence = new JsonArray();
        for (Tuple<ResourceLocation, JsonObject> surfaceRule : getSurfaceRules()) {
            //TODO: Actually check for dimension
            sequence.add(surfaceRule.getB().getAsJsonObject());
        }
        sequence.add(baseRules);
        JsonObject rules = getJson("""
                    {
                      "type": "minecraft:sequence"
                    }""").getAsJsonObject();
        rules.add("sequence", sequence);
        json.getAsJsonObject().asMap().replace("surface_rule", rules);

        return gson.toJson(json);
    }
}