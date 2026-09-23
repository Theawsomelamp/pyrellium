package com.lankaster.pyrellium.client.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrystalArrowRenderer extends ArrowRenderer<CrystalArrowEntity> {
    public CrystalArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalArrowEntity entity) {
        return CrystalArrowEntity.opal ? ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/entity/opal_arrow.png") : ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/entity/amethyst_arrow.png");
    }
}
