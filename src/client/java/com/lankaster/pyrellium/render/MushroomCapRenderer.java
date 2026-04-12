package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;

public class MushroomCapRenderer extends EntityModel<EntityRenderState> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "mushroom_cap"), "main");
    public final ModelPart hat;

    public MushroomCapRenderer(ModelPart root) {
        super(root);
        this.hat = root.getChild("hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData hat = partdefinition.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, 0.0F, -8.0F, 18.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 0.0F, -0.2174F, -0.01887F, 0.08521F));
        ModelPartData hat2 = hat.addChild("hat2", ModelPartBuilder.create().uv(0, 21).cuboid(-9.0F, 0.0F, -8.0F, 18.0F, 3.0F, 18.0F, new Dilation(0.2F)), ModelTransform.NONE);
        ModelPartData hat3 = hat.addChild("hat3", ModelPartBuilder.create().uv(0, 42).cuboid(-6.0F, 0.0F, -5.0F, 12.0F, 5.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, -0.2181F, 0.0F, 0.0F));
        ModelPartData hat4 = hat3.addChild("hat4", ModelPartBuilder.create().uv(0, 59).cuboid(-3.0F, 0.0F, -3.75F, 7.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -3.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }
}