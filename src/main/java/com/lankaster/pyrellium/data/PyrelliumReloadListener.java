package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class PyrelliumReloadListener implements SimpleSynchronousResourceReloadListener {
    public static final PyrelliumReloadListener INSTANCE = new PyrelliumReloadListener();

    public void preload(ResourceManager manager) {
        PyrelliumCustomData.clearSurfaceRules();

        PyrelliumCustomData.read(manager);
    }

    @Override
    public void reload(ResourceManager manager) {

    }

    @Override
    public Identifier getFabricId() {
        return Identifier.of(Pyrellium.MOD_ID, "reload_listener");
    }

}