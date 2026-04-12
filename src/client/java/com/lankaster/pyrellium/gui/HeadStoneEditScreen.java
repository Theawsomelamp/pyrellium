package com.lankaster.pyrellium.gui;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class HeadStoneEditScreen extends AbstractSignEditScreen {
    private static final Vector3f TEXT_SCALE = new Vector3f(0.8765628F, 0.8765628F, 089765628F);
    private final Identifier texture;

    public HeadStoneEditScreen(SignBlockEntity sign, boolean filtered, boolean bl) {
        super(sign, filtered, bl, Text.translatable("headstone.edit"));
        this.texture = Identifier.of(Pyrellium.MOD_ID, "textures/gui/headstone/headstone.png");
    }

    protected void init() {
        super.init();
    }

    @Override
    protected void renderSignBackground(DrawContext context) {
        context.getMatrices().scale(5.0F, 5.0F);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.texture, -8, -8, 0.0F, 0.0F, 16, 16, 16, 16);
    }

    @Override
    protected Vector3f getTextScale() {
        return TEXT_SCALE;
    }

    @Override
    protected float getYOffset() {
        return 90.0F;
    }
}