package net.minecraft.client.gui;

import java.io.IOException;
import net.lax1dude.eaglercraft.Mouse;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import com.isacofff.clientbase.modules.features.ActiveModulesHud;
import com.isacofff.clientbase.Client;
import com.isacofff.clientbase.modules.features.ModuleList;

/**
 * Simple in-game editor for positioning and styling the ActiveModules HUD.
 */
public class HudEditorScreen extends GuiScreen {
    private boolean dragging = false;
    private int dragX;
    private int dragY;

    @Override
    public void initGui() {
        ActiveModulesHud.editing = true;
    }

    @Override
    public void onGuiClosed() {
        ActiveModulesHud.editing = false;
        dragging = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(8, 8, this.width - 8, 62, 0xD810141C);
        drawRect(8, 8, this.width - 8, 10, 0xFFFFC45C);
        fontRendererObj.drawString("KYZXX  /  EDIT HUD", 18, 18, 0xFFFFC45C);
        fontRendererObj.drawString("Drag the ActiveModules panel to move it.", 18, 34, 0xFFE5EAF2);

        drawButtonBox(12, this.height - 34, 100, "COLOR: " + (ActiveModulesHud.rainbow ? "RAINBOW" : "PRESET"));
        drawButtonBox(118, this.height - 34, 100, "NEXT COLOR");
        drawButtonBox(224, this.height - 34, 112, "BACKGROUND: " + (ActiveModulesHud.background ? "ON" : "OFF"));
        drawButtonBox(this.width - 94, this.height - 34, 82, "DONE");

        drawActiveModulesPreview();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawButtonBox(int x, int y, int width, String label) {
        drawRect(x, y, x + width, y + 22, 0xE6222B38);
        drawRect(x, y, x + width, y + 1, 0xFFFFC45C);
        fontRendererObj.drawString(label, x + 5, y + 7, 0xFFFFFFFF);
    }

    private void drawActiveModulesPreview() {
        int x = ActiveModulesHud.x;
        int y = ActiveModulesHud.y;
        String[] preview = new String[] { "ActiveModules", "Fullbright", "AutoSprint", "CreamyKeys", "Coordinates" };
        int width = 0;
        for (String name : preview) width = Math.max(width, fontRendererObj.getStringWidth(name));
        int height = preview.length * 12 + 5;
        if (ActiveModulesHud.background) {
            drawRect(x - 4, y - 4, x + width + 5, y + height, 0xB810141C);
        }
        drawRect(x - 4, y - 4, x + width + 5, y - 3, 0xFFFFC45C);
        for (int i = 0; i < preview.length; ++i) {
            fontRendererObj.drawStringWithShadow(preview[i], x, y + i * 12, ActiveModulesHud.getColor(i));
        }
        drawRect(x - 5, y - 5, x + width + 6, y + height + 1, 0x66FFFFFF);
    }

    private void syncModuleSettings(String color, Boolean background) {
        if (Client.manager == null) return;
        com.isacofff.clientbase.modules.Module found = Client.manager.getModuleByName("ActiveModules");
        if (found instanceof ModuleList) {
            ModuleList module = (ModuleList) found;
            if (color != null) module.color.setValue(color);
            if (background != null) module.background.setValue(background);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton != 0) return;
        int buttonY = this.height - 34;
        if (mouseY >= buttonY && mouseY <= buttonY + 22) {
            if (mouseX >= 12 && mouseX <= 112) {
                String mode = ActiveModulesHud.rainbow ? "GOLD" : "RAINBOW";
                ActiveModulesHud.setColorMode(mode);
                syncModuleSettings(mode, null);
            } else if (mouseX >= 118 && mouseX <= 218) {
                ActiveModulesHud.nextColor();
                String[] modes = new String[] { "GOLD", "TEAL", "BLUE", "PINK", "PURPLE", "WHITE", "GREEN", "ORANGE" };
                syncModuleSettings(modes[ActiveModulesHud.colorIndex], null);
            } else if (mouseX >= 224 && mouseX <= 336) {
                ActiveModulesHud.background = !ActiveModulesHud.background;
                syncModuleSettings(null, Boolean.valueOf(ActiveModulesHud.background));
            } else if (mouseX >= this.width - 94 && mouseX <= this.width - 12) {
                this.mc.displayGuiScreen(null);
                return;
            }
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 0.8F));
            return;
        }

        int x = ActiveModulesHud.x - 6;
        int y = ActiveModulesHud.y - 6;
        if (mouseX >= x && mouseX <= x + 190 && mouseY >= y && mouseY <= y + 90) {
            dragging = true;
            dragX = mouseX - ActiveModulesHud.x;
            dragY = mouseY - ActiveModulesHud.y;
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (dragging && clickedMouseButton == 0) {
            ActiveModulesHud.x = Math.max(2, Math.min(this.width - 120, mouseX - dragX));
            ActiveModulesHud.y = Math.max(16, Math.min(this.height - 90, mouseY - dragY));
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 || keyCode == 54) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
