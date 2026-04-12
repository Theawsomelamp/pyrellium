package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;

public class OpalTiaraRenderer extends EntityModel<EntityRenderState> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "opal_tiara"), "main");
    public final ModelPart hat;

    public OpalTiaraRenderer(ModelPart root) {
        super(root);
        this.hat = root.getChild("hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData hat = partdefinition.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.5F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 32, 32);
    }
}
