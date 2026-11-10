package com.lankaster.pyrellium.client.compat;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.client.util.MushroomCapRenderer;
import com.lankaster.pyrellium.client.util.OpalTiaraRenderer;
import com.lankaster.pyrellium.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Function;
import java.util.function.Supplier;

public class ClientCuriosCompat {

    public static void init() {
        CuriosRendererRegistry.register(ModItems.OPAL_TIARA.get(), () -> new PyrelliumCurioRenderer(
                OpalTiaraRenderer::new,
                OpalTiaraRenderer::getTexturedModelData,
                ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/item/opal_tiara_model.png")
        ));

        CuriosRendererRegistry.register(ModItems.MUSHROOM_CAP.get(), () -> new PyrelliumCurioRenderer(
                MushroomCapRenderer::new,
                MushroomCapRenderer::getTexturedModelData,
                ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/item/mushroom_cap_model.png")
        ));
    }

    private static class PyrelliumCurioRenderer implements ICurioRenderer {
        private final Supplier<LayerDefinition> layer;
        private final ResourceLocation texture;
        private final Function<ModelPart, EntityModel<?>> modelFactory;
        private EntityModel<?> model;

        private PyrelliumCurioRenderer(Function<ModelPart, EntityModel<?>> modelFactory, Supplier<LayerDefinition> layer, ResourceLocation texture) {
            this.layer = layer;
            this.texture = texture;
            this.modelFactory = modelFactory;
        }

        @Override
        public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (slotContext.entity() instanceof AbstractClientPlayer) {
                if (model == null) {
                    model = modelFactory.apply(layer.get().bakeRoot());
                }

                HeadedModel contextModel = (HeadedModel) renderLayerParent.getModel();

                contextModel.getHead().translateAndRotate(matrixStack);
                matrixStack.scale(1.19F, 1.19F, 1.19F);
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, model.renderType(texture), false);
                model.renderToBuffer(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);

            }
        }
    }
}
