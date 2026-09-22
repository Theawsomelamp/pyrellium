package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static ModelLayerLocation HEADSTONE =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "sign/headstone"), "main");
    public static ModelLayerLocation GEODIN =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "geodin"), "main");
}