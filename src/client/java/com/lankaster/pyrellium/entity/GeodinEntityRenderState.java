package com.lankaster.pyrellium.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class GeodinEntityRenderState extends LivingEntityRenderState {
    public GeodinEntity.Variant variant;
    public int crystalAge;
}
