package com.lankaster.pyrellium.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;

@Environment(EnvType.CLIENT)
public class CrystalArrowEntityRenderState extends ProjectileEntityRenderState {
    public boolean opal;
}