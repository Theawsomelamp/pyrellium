package com.lankaster.pyrellium.world;

import blue.endless.jankson.annotation.Nullable;
import com.google.gson.*;
import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModNoiseSettings {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public final Identifier target;
    public Function<JsonElement, String> provider;
    public final Supplier<Boolean> enabled;

    public ModNoiseSettings(Identifier target, Supplier<Boolean> enabled, Function<JsonElement, String> provider) {
        this.target = target;
        this.provider = provider;
        this.enabled = enabled;
    }

    public String apply(@Nullable String original){
        return gson.fromJson(this.provider.apply(gson.fromJson(original == null ? "" : original, JsonElement.class)), JsonElement.class).toString();
    }

    private static JsonElement getJson(String string) {
        return gson.fromJson(string, JsonElement.class);
    }

    public static boolean detectModification(JsonElement json) {
        JsonElement defaultNoise = getJson("""
                {"height":128,"min_y":0,"size_horizontal":1,"size_vertical":2}""");
        JsonElement defaultRouter = getJson("""
                {"barrier":0.0,"continents":0.0,"depth":0.0,"erosion":0.0,"final_density":{"type":"minecraft:squeeze","argument":{"type":"minecraft:mul","argument1":0.64,"argument2":{"type":"minecraft:interpolated","argument":{"type":"minecraft:blend_density","argument":{"type":"minecraft:add","argument1":2.5,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":0.0,"from_y":-8,"to_value":1.0,"to_y":24},"argument2":{"type":"minecraft:add","argument1":-2.5,"argument2":{"type":"minecraft:add","argument1":0.9375,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":1.0,"from_y":104,"to_value":0.0,"to_y":128},"argument2":{"type":"minecraft:add","argument1":-0.9375,"argument2":"minecraft:nether/base_3d_noise"}}}}}}}}}},"fluid_level_floodedness":0.0,"fluid_level_spread":0.0,"initial_density_without_jaggedness":0.0,"lava":0.0,"ridges":0.0,"temperature":{"type":"minecraft:shifted_noise","noise":"minecraft:temperature","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vegetation":{"type":"minecraft:shifted_noise","noise":"minecraft:vegetation","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vein_gap":0.0,"vein_ridged":0.0,"vein_toggle":0.0}""");
        Pyrellium.LOGGER.info(String.valueOf((!json.getAsJsonObject().get("noise").equals(defaultNoise)) || (!json.getAsJsonObject().get("noise_router").equals(defaultRouter))));
        return (!json.getAsJsonObject().get("noise").equals(defaultNoise)) || (!json.getAsJsonObject().get("noise_router").equals(defaultRouter));
    }

    public static String changeNoiseRouter(JsonElement json) {
        json.getAsJsonObject().asMap().replace("noise", getJson("""
                        { "min_y": 0, "height": 192, "size_horizontal": 1, "size_vertical": 2 }"""));

        json.getAsJsonObject().get("noise_router")
                .getAsJsonObject().asMap().replace("final_density", new JsonPrimitive("pyrellium:final_density"));

        return gson.toJson(json);
    }
}
