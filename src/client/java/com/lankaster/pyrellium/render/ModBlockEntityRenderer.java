package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class ModBlockEntityRenderer {
    public static void register() {
        BlockEntityRenderers.register(ModBlockEntities.HEADSTONE, HeadStoneBlockEntityRenderer::new);
    }
}