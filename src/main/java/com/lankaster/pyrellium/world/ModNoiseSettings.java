package com.lankaster.pyrellium.world;

import blue.endless.jankson.annotation.Nullable;
import com.google.gson.*;
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


    public static String changeNoiseRouter(JsonElement json) {
        json.getAsJsonObject().asMap().replace("noise", getJson("""
                        { "min_y": 0, "height": 192, "size_horizontal": 1, "size_vertical": 2 }"""));

        json.getAsJsonObject().get("noise_router")
                .getAsJsonObject().asMap().replace("final_density", new JsonPrimitive("pyrellium:final_density"));

        return gson.toJson(json);
    }
}
