package com.lankaster.pyrellium.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.MathHelper;

public class GeodinModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart geodin;
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public GeodinModel(ModelPart root) {
        super(root);
        this.geodin = root.getChild("geodin");
        this.body = geodin.getChild("body");
        this.leftLeg = body.getChild("left_leg");
        this.rightLeg = body.getChild("right_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData geodin = modelPartData.addChild("geodin", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 16.0F, 0.0F));

        ModelPartData body = geodin.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -6.0F, -8.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create().uv(0, 24).cuboid(1.0F, 2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create().uv(16, 24).cuboid(-5.0F, 2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {
        this.rightLeg.pitch = MathHelper.cos(state.limbSwingAnimationProgress * 0.6662F + (float)Math.PI) * 1.4F * state.limbSwingAmplitude;
        this.leftLeg.pitch = MathHelper.cos(state.limbSwingAnimationProgress * 0.6662F) * 1.4F * state.limbSwingAmplitude;
    }
}