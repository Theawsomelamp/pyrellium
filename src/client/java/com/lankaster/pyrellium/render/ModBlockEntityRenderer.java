package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;

public class ModBlockEntityRenderer {
    public static void register() {
        BlockEntityRendererFactories.register(ModBlockEntities.BURNING_SIGN, SignBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.BURNING_HANGING_SIGN, HangingSignBlockEntityRenderer::new);
    }
}
