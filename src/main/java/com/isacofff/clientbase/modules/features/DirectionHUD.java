package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

public class DirectionHUD extends Module {
    private static final String[] DIRECTIONS = {"South", "West", "North", "East"};

    public DirectionHUD() {
        super("Direction", "Displays the direction you are facing.", Category.Render);
    }

    @Override
    public void onRender2D(float partialTicks) {
        if (mc.player == null || mc.gameSettings.hideGUI || mc.gameSettings.showDebugInfo) return;
        FontRenderer font = mc.fontRendererObj;
        if (font == null) return;

        int index = MathHelper.floor(mc.player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        String line = "§bFacing §f" + DIRECTIONS[index];
        int x = 6, y = 54, pad = 5;
        Gui.drawRect(x, y, x + font.getStringWidth(line) + pad * 2 + 2, y + font.FONT_HEIGHT + pad * 2, 0xA9101722);
        Gui.drawRect(x, y, x + 2, y + font.FONT_HEIGHT + pad * 2, 0xFF42C5F5);
        font.drawStringWithShadow(line, x + pad + 2, y + pad, 0xFFFFFFFF);
    }
}
