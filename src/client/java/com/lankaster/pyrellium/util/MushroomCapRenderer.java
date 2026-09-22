package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

public class MushroomCapRenderer<t extends Entity> extends EntityModel<t> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "mushroom_cap"), "main");
    public final ModelPart hat;

    public MushroomCapRenderer(ModelPart root) {
        this.hat = root.getChild("hat");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 0.0F, -8.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.2174F, -0.01887F, 0.08521F));
        PartDefinition hat2 = hat.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(0, 21).addBox(-9.0F, 0.0F, -8.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
        PartDefinition hat3 = hat.addOrReplaceChild("hat3", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, 0.0F, -5.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.2181F, 0.0F, 0.0F));
        PartDefinition hat4 = hat3.addOrReplaceChild("hat4", CubeListBuilder.create().texOffs(0, 59).addBox(-3.0F, 0.0F, -3.75F, 7.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(t entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.hat.yRot = headYaw / (180F / (float) Math.PI);
        this.hat.xRot = headPitch / (180F / (float) Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        hat.render(matrices, vertices, light, overlay, color);
    }
}