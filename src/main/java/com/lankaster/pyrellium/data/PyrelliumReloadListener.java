package com.lankaster.pyrellium.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.Map;

public class PyrelliumReloadListener extends SimpleJsonResourceReloadListener {
    public static final PyrelliumReloadListener INSTANCE = new PyrelliumReloadListener(new GsonBuilder().setPrettyPrinting().create(), Pyrellium.MOD_ID);

    public PyrelliumReloadListener(Gson gson, String directory) {
        super(gson, directory);
    }

    public void preload(ResourceManager manager) {
        PyrelliumCustomData.clearSurfaceRules();
        PyrelliumCustomData.clearBiomeNoise();

        PyrelliumCustomData.read(manager);
    }

    @Override
    public String getName() {
        return ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "reload_listener").toString();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

    }

    public static void registerServerDatapack(AddReloadListenerEvent event) {
        event.addListener(INSTANCE);
    }
}