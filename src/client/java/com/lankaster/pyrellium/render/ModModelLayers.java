package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static EntityModelLayer HEADSTONE =
            new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "sign/headstone"), "main");
}