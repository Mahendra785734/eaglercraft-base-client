package net.eaglercraft.client.module.impl.hud;

import net.eaglercraft.client.module.Module;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

/**
 * Displays FPS and player coordinates in a compact top-left HUD panel.
 */
public class FPSDisplay extends Module {

    private static final int PADDING = 4;
    private static final int BACKGROUND_COLOR = 0x90000000;
    private static final int ACCENT_COLOR = 0xFF55FF55;
    private static final int TEXT_COLOR = 0xFFFFFFFF;

    public FPSDisplay() {
        super("FPS & Coords",
                "Displays current FPS and XYZ coordinates on the HUD.",
                Category.HUD, 0);
        setEnabled(true);
    }

    @Override
    public void onRender2D(float partialTicks) {
        if (mc.player == null || mc.world == null || mc.gameSettings.showDebugInfo
                || mc.gameSettings.hideGUI) {
            return;
        }

        FontRenderer font = mc.fontRendererObj;
        int fps = Minecraft.getDebugFPS();
        int posX = MathHelper.floor(mc.player.posX);
        int posY = MathHelper.floor(mc.player.posY);
        int posZ = MathHelper.floor(mc.player.posZ);

        String fpsLine = "§aFPS: §f" + fps;
        String xyzLine = "§aXYZ: §f" + posX + ", " + posY + ", " + posZ;

        int maxWidth = Math.max(font.getStringWidth(fpsLine), font.getStringWidth(xyzLine));
        int boxX = 4;
        int boxY = 4;
        int boxWidth = maxWidth + PADDING * 2;
        int boxHeight = font.FONT_HEIGHT * 2 + PADDING * 2 + 2;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        Gui.drawRect(boxX, boxY, boxX + boxWidth, boxY + boxHeight, BACKGROUND_COLOR);
        Gui.drawRect(boxX, boxY, boxX + 2, boxY + boxHeight, ACCENT_COLOR);

        int textX = boxX + PADDING + 1;
        int textY = boxY + PADDING;
        font.drawStringWithShadow(fpsLine, textX, textY, TEXT_COLOR);
        font.drawStringWithShadow(xyzLine, textX, textY + font.FONT_HEIGHT + 2, TEXT_COLOR);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
