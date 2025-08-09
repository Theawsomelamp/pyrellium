package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ModBlockEntityRenderer {
    public static void register() {
        BlockEntityRendererFactories.register(ModBlockEntities.HEADSTONE, HeadStoneBlockEntityRenderer::new);
    }
}