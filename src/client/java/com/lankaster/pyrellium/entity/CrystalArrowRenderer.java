package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class CrystalArrowRenderer extends ProjectileEntityRenderer<CrystalArrowEntity> {
    public CrystalArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CrystalArrowEntity entity) {
        return entity.opal ? new Identifier(Pyrellium.MOD_ID, "textures/entity/opal_arrow.png") : new Identifier(Pyrellium.MOD_ID, "textures/entity/amethyst_arrow.png");
    }
}
