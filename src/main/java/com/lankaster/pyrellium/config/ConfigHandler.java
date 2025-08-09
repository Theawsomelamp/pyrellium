package com.lankaster.pyrellium.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.lankaster.pyrellium.Pyrellium;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.JsonHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

public class ConfigHandler {
    private static ConfigCodec LOADED_CONFIG = new ConfigCodec(
            ConfigCodec.BlocksConfig.DEFAULT,
            ConfigCodec.BiomeConfig.DEFAULT,
            ConfigCodec.GlobalFeatureConfig.DEFAULT,
            ConfigCodec.BiomeFeatureConfig.DEFAULT
    );

    public static ConfigCodec getConfig() {
        return LOADED_CONFIG;
    }

    public static void load(Path path) {
        if (!Files.isRegularFile(path)) {
            write(path);
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            JsonElement json = JsonParser.parseReader(reader);
            Optional<ConfigCodec> result = ConfigCodec.CODEC.parse(JsonOps.INSTANCE, json).result();
            if (result.isPresent()) {
                LOADED_CONFIG = result.get();
            } else {
                throw new JsonParseException("Invalid codec");
            }
        } catch (JsonParseException e) {
            Pyrellium.LOGGER.error("Couldn't parse config file, resetting to default config");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        write(path);
    }

    private static void write(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            JsonElement element = ConfigCodec.CODEC.encodeStart(JsonOps.INSTANCE, LOADED_CONFIG).result().get();
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.setIndent("  ");
            JsonHelper.writeSorted(jsonWriter, element, Comparator.naturalOrder());
            writer.write(stringWriter.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}