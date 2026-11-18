package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

public class CoordinatesHUD extends Module {
    public CoordinatesHUD() {
        super("Coordinates", "Shows your current X, Y and Z coordinates.", Category.Render);
    }

    @Override
    public void onRender2D(float partialTicks) {
        if (mc.player == null || mc.world == null || mc.gameSettings.hideGUI || mc.gameSettings.showDebugInfo) return;
        FontRenderer font = mc.fontRendererObj;
        if (font == null) return;

        String line = "§bXYZ §f" + MathHelper.floor(mc.player.posX) + " "
                + MathHelper.floor(mc.player.posY) + " " + MathHelper.floor(mc.player.posZ);
        int x = 6, y = 36, pad = 5;
        Gui.drawRect(x, y, x + font.getStringWidth(line) + pad * 2 + 2, y + font.FONT_HEIGHT + pad * 2, 0xA9101722);
        Gui.drawRect(x, y, x + 2, y + font.FONT_HEIGHT + pad * 2, 0xFF42C5F5);
        font.drawStringWithShadow(line, x + pad + 2, y + pad, 0xFFFFFFFF);
    }
}
