package com.lankaster.pyrellium.gui;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class HeadStoneEditScreen extends AbstractSignEditScreen {
    private static final Vector3f TEXT_SCALE = new Vector3f(0.9765628F, 0.9765628F, 0.9765628F);
    private final ResourceLocation texture;

    public HeadStoneEditScreen(SignBlockEntity sign, boolean filtered, boolean bl) {
        super(sign, filtered, bl, Component.translatable("headstone.edit"));
        this.texture = ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/gui/headstone/headstone.png");
    }

    protected void init() {
        super.init();
    }

    protected void offsetSign(GuiGraphics context, BlockState state) {
        context.pose().translate((float)this.width / 2.0F, 125.0F, 50.0F);
    }

    protected void renderSignBackground(GuiGraphics context, BlockState state) {
        context.pose().scale(6.0F, 6.0F, 1.0F);
        context.blit(this.texture, -8, -8, 0.0F, 0.0F, 16, 16, 16, 16);
    }

    @Override
    protected Vector3f getSignTextScale() {
        return TEXT_SCALE;
    }
}