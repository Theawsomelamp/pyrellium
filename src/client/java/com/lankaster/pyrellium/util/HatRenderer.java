package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class HatRenderer<t extends Entity> extends EntityModel<t> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "opal_tiara"), "main");
    public final ModelPart hat;

    public HatRenderer(ModelPart root) {
        this.hat = root.getChild("hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData hat = partdefinition.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.5F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 32, 32);
    }

    @Override
    public void setAngles(t entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.hat.yaw = headYaw / (180F / (float) Math.PI);
        this.hat.pitch = headPitch / (180F / (float) Math.PI);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        hat.render(matrices, vertices, light, overlay, color);
    }
}
