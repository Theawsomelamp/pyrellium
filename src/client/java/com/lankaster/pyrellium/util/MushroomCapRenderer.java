package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class MushroomCapRenderer<t extends Entity> extends EntityModel<t> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "mushroom_cap"), "main");
    public final ModelPart hat;

    public MushroomCapRenderer(ModelPart root) {
        this.hat = root.getChild("hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData hat = partdefinition.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, 0.0F, -8.0F, 18.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 0.0F, -0.2174F, -0.01887F, 0.08521F));
        ModelPartData hat2 = hat.addChild("hat2", ModelPartBuilder.create().uv(0, 21).cuboid(-9.0F, 0.0F, -8.0F, 18.0F, 3.0F, 18.0F, new Dilation(0.2F)), ModelTransform.NONE);
        ModelPartData hat3 = hat.addChild("hat3", ModelPartBuilder.create().uv(0, 42).cuboid(-6.0F, 0.0F, -5.0F, 12.0F, 5.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, -0.2181F, 0.0F, 0.0F));
        ModelPartData hat4 = hat3.addChild("hat4", ModelPartBuilder.create().uv(0, 59).cuboid(-3.0F, 0.0F, -3.75F, 7.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }

    @Override
    public void setAngles(t entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.hat.yaw = headYaw / (180F / (float) Math.PI);
        this.hat.pitch = headPitch / (180F / (float) Math.PI);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        hat.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
