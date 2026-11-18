package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Speedometer extends Module {
    public Speedometer() {
        super("Speedometer", "Shows your horizontal movement speed.", Category.Render);
    }

    @Override
    public void onRender2D(float partialTicks) {
        if (mc.player == null || mc.gameSettings.hideGUI || mc.gameSettings.showDebugInfo) return;
        FontRenderer font = mc.fontRendererObj;
        if (font == null) return;

        double dx = mc.player.posX - mc.player.lastTickPosX;
        double dz = mc.player.posZ - mc.player.lastTickPosZ;
        double speed = Math.sqrt(dx * dx + dz * dz) * 20.0D;
        String line = "§bSpeed §f" + Math.round(speed * 100.0D) / 100.0D + " m/s";
        int x = 6, y = 72, pad = 5;
        Gui.drawRect(x, y, x + font.getStringWidth(line) + pad * 2 + 2, y + font.FONT_HEIGHT + pad * 2, 0xA9101722);
        Gui.drawRect(x, y, x + 2, y + font.FONT_HEIGHT + pad * 2, 0xFF42C5F5);
        font.drawStringWithShadow(line, x + pad + 2, y + pad, 0xFFFFFFFF);
    }
}
