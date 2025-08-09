package com.lankaster.pyrellium.gui;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class HeadStoneEditScreen extends AbstractSignEditScreen {
    private static final Vector3f TEXT_SCALE = new Vector3f(0.9765628F, 0.9765628F, 0.9765628F);
    private final Identifier texture;

    public HeadStoneEditScreen(SignBlockEntity sign, boolean filtered, boolean bl) {
        super(sign, filtered, bl, Text.translatable("headstone.edit"));
        this.texture = Identifier.of(Pyrellium.MOD_ID, "textures/gui/headstone/headstone.png");
    }

    protected void init() {
        super.init();
    }

    protected void translateForRender(DrawContext context, BlockState state) {
        context.getMatrices().translate((float)this.width / 2.0F, 125.0F, 50.0F);
    }

    protected void renderSignBackground(DrawContext context, BlockState state) {
        context.getMatrices().scale(6.0F, 6.0F, 1.0F);
        context.drawTexture(this.texture, -8, -8, 0.0F, 0.0F, 16, 16, 16, 16);
    }

    @Override
    protected Vector3f getTextScale() {
        return TEXT_SCALE;
    }
}