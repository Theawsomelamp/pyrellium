package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;

public class PyrelliumReloadListener implements SimpleSynchronousResourceReloadListener {
    public static final PyrelliumReloadListener INSTANCE = new PyrelliumReloadListener();

    public void preload(ResourceManager manager) {
        PyrelliumCustomData.clearSurfaceRules();
        PyrelliumCustomData.clearBiomeNoise();

        PyrelliumCustomData.read(manager);
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {

    }

    @Override
    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "reload_listener");
    }

}