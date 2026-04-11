package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class CrystalArrowRenderer extends ProjectileEntityRenderer<CrystalArrowEntity, CrystalArrowEntityRenderState> {
    public static final Identifier AMETHYST_TEXTURE = Identifier.of(Pyrellium.MOD_ID, "textures/entity/amethyst_arrow.png");
    public static final Identifier OPAL_TEXTURE = Identifier.of(Pyrellium.MOD_ID, "textures/entity/opal_arrow.png");

    public CrystalArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected Identifier getTexture(CrystalArrowEntityRenderState state) {
        return state.opal ? OPAL_TEXTURE : AMETHYST_TEXTURE;
    }

    @Override
    public CrystalArrowEntityRenderState createRenderState() {
        return new CrystalArrowEntityRenderState();
    }

    public void updateRenderState(CrystalArrowEntity arrowEntity, CrystalArrowEntityRenderState arrowEntityRenderState, float f) {
        super.updateRenderState(arrowEntity, arrowEntityRenderState, f);
        arrowEntityRenderState.opal = CrystalArrowEntity.opal;
    }
}
