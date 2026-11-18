package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

/**
 * Displays FPS and player coordinates using the existing KYZXX module manager.
 */
public class FPSDisplay extends Module {

    public FPSDisplay() {
        super("FPS Display", "Displays FPS and your current XYZ coordinates.", Category.Render);
        setEnabled(true);
    }

    @Override
    public void onRender2D(float partialTicks) {
        if (mc.player == null || mc.world == null || mc.gameSettings.showDebugInfo || mc.gameSettings.hideGUI) {
            return;
        }

        FontRenderer font = mc.fontRendererObj;
        if (font == null) return;

        String fpsLine = "§bFPS: §f" + Minecraft.getDebugFPS();
        String xyzLine = "§bXYZ: §f" + MathHelper.floor(mc.player.posX) + ", "
                + MathHelper.floor(mc.player.posY) + ", " + MathHelper.floor(mc.player.posZ);

        int padding = 5;
        int textWidth = Math.max(font.getStringWidth(fpsLine), font.getStringWidth(xyzLine));
        int x = 6;
        int y = 6;
        int width = textWidth + padding * 2 + 3;
        int height = font.FONT_HEIGHT * 2 + padding * 2 + 2;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Gui.drawRect(x, y, x + width, y + height, 0xA9101722);
        Gui.drawRect(x, y, x + 2, y + height, 0xFF42C5F5);
        font.drawStringWithShadow(fpsLine, x + padding + 2, y + padding, 0xFFFFFFFF);
        font.drawStringWithShadow(xyzLine, x + padding + 2, y + padding + font.FONT_HEIGHT + 2, 0xFFFFFFFF);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
