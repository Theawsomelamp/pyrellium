package com.lankaster.pyrellium.config;

import com.google.gson.*;
import com.lankaster.pyrellium.Pyrellium;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static String noiseToJson(Config.BiomeNoise noise) {
        Pyrellium.LOGGER.info(gson.toJson(noise));
        return gson.toJson(noise);
    }

    public static void load(Path path) {
        if (!Files.isRegularFile(path)) {
            write(path);
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Config.INSTANCE = gson.fromJson(reader, Config.class);
        } catch (JsonParseException e) {
            Pyrellium.LOGGER.error("Couldn't parse config file, resetting to default config");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        write(path);
    }

    private static void write(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(gson.toJson(Config.INSTANCE));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
