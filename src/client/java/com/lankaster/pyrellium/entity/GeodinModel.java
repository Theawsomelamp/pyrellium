// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.lankaster.pyrellium.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class GeodinModel<T extends GeodinEntity> extends SinglePartEntityModel<T> {
	private final ModelPart geodin;
	private final ModelPart body;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public GeodinModel(ModelPart root) {
		this.geodin = root.getChild("geodin");
		this.body = geodin.getChild("body");
		this.leftLeg = body.getChild("left_leg");
		this.rightLeg = body.getChild("right_leg");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData geodin = modelPartData.addChild("geodin", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData body = geodin.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -6.0F, -8.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create().uv(0, 24).cuboid(1.0F, 2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create().uv(16, 24).cuboid(-5.0F, 2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(GeodinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		geodin.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return geodin;
	}
}