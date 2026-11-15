package net.minecraft.client.gui;

import com.isacofff.clientbase.Client;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import com.isacofff.clientbase.settings.Setting.BooleanSetting;
import com.isacofff.clientbase.settings.Setting.ModeSetting;
import com.isacofff.clientbase.settings.Setting.NumberSetting;
import com.isacofff.clientbase.Category;
import net.lax1dude.eaglercraft.Keyboard;
import net.lax1dude.eaglercraft.KeyboardConstants;
import net.lax1dude.eaglercraft.Mouse;
import java.io.IOException;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.client.gui.HudEditorScreen;
import java.util.ArrayList;

public class ClickGuiScreen extends GuiScreen {

    //I won't go into too much detail here but if you need help contact me on discord, reach out to me.
    //Discord : isacofff

    private static final int PANEL_BG = 0xE6121722;
    private static final int PANEL_OUTLINE = 0xFFB88A3B;
    private static final int MODULE_ENABLED = 0xFF73501F;
    private static final int MODULE_DISABLED = 0xD9232B38;
    private static final int TEXT_COLOR = -1;
    private static final int ACCENT_COLOR = 0xFFFFC45C;
    private static final int PANEL_MOVE_SPEED = 15;
    private static final int SLIDER_BG = 0xFF414B59;
    private static final int SLIDER_FILL = 0xFFFFB84D;
    private static final int SLIDER_KNOB = -1;

    private final ArrayList<Panel> panels = new ArrayList<>();
    private Module listeningForKeybind = null;

    public ClickGuiScreen() {
        int x = 1;
        int y = 1;

        for (Category cat : Category.values()) {
            //Cat :3
            panels.add(new Panel(cat, x, y));
            x += 112;
        }
    }



    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        for (Panel panel : panels) {
            panel.mouseDragged(mouseX, mouseY, clickedMouseButton);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }


    @Override
    public void updateScreen() {

        int movex = 0;
        int movey = 0;

        if (Keyboard.isKeyDown(KeyboardConstants.KEY_UP))
            movey = - PANEL_MOVE_SPEED;
        if (Keyboard.isKeyDown(KeyboardConstants.KEY_DOWN))
            movey =  PANEL_MOVE_SPEED;
        if (Keyboard.isKeyDown(KeyboardConstants.KEY_LEFT))
            movex = - PANEL_MOVE_SPEED;
        if (Keyboard.isKeyDown(KeyboardConstants.KEY_RIGHT))
            movex =  PANEL_MOVE_SPEED;

        if (movex != 0 || movey != 0) {
            for (Panel panel : panels) {
                panel.x += movex;
                panel.y += movey;
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getDWheel();
        if (wheel != 0) {
            int mouseX = Mouse.getX() * this.width / this.mc.displayWidth;
            int mouseY = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
            for (Panel panel : panels) {
                if (mouseX >= panel.x && mouseX <= panel.x + panel.width
                        && mouseY >= panel.y + panel.height && mouseY <= this.height - 24) {
                    panel.scroll(wheel);
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        this.drawDefaultBackground();

        for (Panel panel : panels) {
            panel.draw(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawRect(6, this.height - 19, this.width - 6, this.height - 5, 0xB810141C);
        fontRendererObj.drawString("KYZXX CLIENT  |  RIGHT CLICK MODULE FOR SETTINGS", 12, this.height - 15, 0xFFFFC45C);
        fontRendererObj.drawString("EDIT HUD", this.width / 2 - 28, this.height - 15, 0xFFFFFFFF);
        fontRendererObj.drawString("RSHIFT: GUI", this.width - 78, this.height - 15, 0xFFD7DCE5);
        if (listeningForKeybind != null) {
            drawRect(6, 6, this.width - 6, 20, 0xD910141C);
            fontRendererObj.drawString("PRESS A KEY FOR " + listeningForKeybind.getName() + "  |  ESC CANCEL  |  BACKSPACE CLEAR", 12, 10, ACCENT_COLOR);
        }

        String descriptionn = null;

        for (int panelSize = panels.size() - 1; panelSize >= 0; panelSize--) {
            String description = panels.get(panelSize).getHoveredDescription(mouseX, mouseY);
            if (description != null) {
                descriptionn = description;
                break;
            }
        }

        if (descriptionn != null) {
            drawDescription(descriptionn, mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        if (mouseButton == 0 || mouseButton == 1) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 0.65F));
        }

        if (mouseButton == 0 && mouseY >= this.height - 19 && mouseY <= this.height - 5
                && mouseX >= this.width / 2 - 34 && mouseX <= this.width / 2 + 34) {
            mc.displayGuiScreen(new HudEditorScreen());
            return;
        }

        for (Panel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (listeningForKeybind != null) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                listeningForKeybind = null;
            } else {
                listeningForKeybind.setKeybind((keyCode == 14 || keyCode == 211) ? 0 : keyCode);
                listeningForKeybind = null;
            }
            return;
        }
        if (keyCode == KeyboardConstants.KEY_ESCAPE || keyCode == 54) {
            Module clickGui = Client.INSTANCE.manager.getModuleByName("ClickGUI");
            if (clickGui != null && clickGui.isEnabled()) {
                clickGui.toggle();
            } else {
                mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawDescription(String text, int mouseX, int mouseY) {

        int padding = 5;
        int textWidth = fontRendererObj.getStringWidth(text);
        int boxX = mouseX + 8;
        int boxY = mouseY + 8;

        drawRect(boxX, boxY, boxX + textWidth + padding * 2, boxY + 12 + padding, 0x90000000);
        fontRendererObj.drawString(text, boxX + padding, boxY + padding, TEXT_COLOR);
    }

    private void drawOutline(int x1, int y1, int x2, int y2, int color) {
        drawRect(x1, y1, x2, y1 + 1, color);
        drawRect(x1, y2 - 1, x2, y2, color);
        drawRect(x1, y1, x1 + 1, y2, color);
        drawRect(x2 - 1, y1, x2, y2, color);
    }


    public class Panel {
        public Category category;
        public int x, y, width = 108, height = 18;
        public boolean dragging = false;
        public int dragX, dragY;
        public boolean open = true;
        private NumberSetting draggingSlider = null;
        private int sliderX = 0;
        private int sliderWidth = 0;
        private int scrollOffset = 0;
        public Panel(Category category, int x, int y) {
            this.category = category;
            this.x = x;
            this.y = y;
        }

        private boolean hasSettings(Module module) {
            return module.getSettings() != null && !module.getSettings().isEmpty();
        }

        public String getHoveredDescription(int mouseX, int mouseY) {
            if (!open) return null;

            int offset = height - scrollOffset;

            for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {

                if (isHover(mouseX, mouseY, x, y + offset, width, 14)) {
                    return module.getDescription();
                }
                offset += 14;

                if (module.open && hasSettings(module)) {
                    for (Setting<?> setting : module.getSettings()) {
                        if (isHover(mouseX, mouseY, x, y + offset, width, 14)) {
                            return setting.getName();
                        }
                        offset += 14;
                    }
                }
            }

            return null;
        }

        public void draw(int mouseX, int mouseY) {

            if (!Mouse.isButtonDown(0)) {
                dragging = false;
                draggingSlider = null;
            }
            if (dragging) {
                this.x = mouseX - dragX;
                this.y = mouseY - dragY;
            }

            if (open) {
                drawOutline(x, y, x + width, y + getTotalHeight(), PANEL_OUTLINE);
            } else {
                drawOutline(x, y, x + width, y + height, PANEL_OUTLINE);
            }

            drawRect(x, y, x + width, y + height, PANEL_BG);

            fontRendererObj.drawString(category.name(), x + 4, y + 4, TEXT_COLOR);

            if (open) {

                int offset = height - scrollOffset;
                int visibleBottom = ClickGuiScreen.this.height - 24;

                for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {
                    int rowY = y + offset;
                    if (rowY + 14 >= y + height && rowY <= visibleBottom) {
                        drawRect(x, rowY, x + width, rowY + 14, module.isEnabled() ? MODULE_ENABLED : MODULE_DISABLED);
                        String symbol = module.open ? "-" : "+";
                        fontRendererObj.drawString(symbol, x + width - 10, rowY + 4, ACCENT_COLOR);
                        String bindLabel = listeningForKeybind == module ? "..." : (module.getKeybind() == 0 ? "K" : Keyboard.getKeyName(module.getKeybind()));
                        fontRendererObj.drawString(bindLabel, x + width - 34, rowY + 4, listeningForKeybind == module ? ACCENT_COLOR : 0xFFD7DCE5);
                        fontRendererObj.drawString(module.getName(), x + 4, rowY + 4, TEXT_COLOR);
                    }
                    offset += 14;

                    if (module.open && hasSettings(module)) {
                        for (Setting<?> setting : module.getSettings()) {
                            int settingY = y + offset;
                            if (settingY + 14 >= y + height && settingY <= visibleBottom) {
                                drawRect(x, settingY, x + width, settingY + 14, MODULE_DISABLED);
                                drawSetting(setting, x, settingY);
                            }
                            offset += 14;
                        }
                    }
                }
            }
        }

        private void drawSetting(Setting<?> s, int x, int y) {

            if (s instanceof BooleanSetting bs) {
                fontRendererObj.drawString(bs.getName() + " : " + bs.getValue(), x + 4, y + 4, TEXT_COLOR);
                return;
            }

            if (s instanceof ModeSetting ms) {
                fontRendererObj.drawString(ms.getName() + " : " + ms.getValue() + " ...", x + 4, y + 4, TEXT_COLOR);
                return;
            }

            if (s instanceof NumberSetting number) {
                drawRect(x, y, x + width, y + 14, MODULE_DISABLED);
                fontRendererObj.drawString(number.getName() + " : " + number.getValue(), x + 4, y + 4, TEXT_COLOR);
                int barX = x + 4;
                int barY = y + 12;
                int barW = width - 8;
                int barH = 2;
                drawRect(barX, barY, barX + barW, barY + barH, SLIDER_BG);
                double percent = (number.getValue() - number.getMin()) / (number.getMax() - number.getMin());
                int fillW = (int)(percent * barW);
                drawRect(barX, barY, barX + fillW, barY + barH, SLIDER_FILL);
                int knobX = barX + fillW;
                drawRect(knobX - 1, barY, knobX + 1, barY + barH, SLIDER_KNOB);
            }
        }

        public void mouseDragged(int mouseX, int mouseY, int mouseButton) {
            if (draggingSlider != null && mouseButton == 0) {
                double percent = (mouseX - sliderX) / (double) sliderWidth;
                percent = Math.max(0.0, Math.min(1.0, percent));
                double rawValue = draggingSlider.getMin() + percent * (draggingSlider.getMax() - draggingSlider.getMin());
                double increment = draggingSlider.getIncrement();
                if (increment > 0) {
                    double steps = (rawValue - draggingSlider.getMin()) / increment;
                    double snapped = draggingSlider.getMin() + Math.round(steps) * increment;
                    snapped = Math.round(snapped * 10000.0) / 10000.0;
                    snapped = Math.max(draggingSlider.getMin(), Math.min(draggingSlider.getMax(), snapped));
                    draggingSlider.setValue(snapped);
                } else {
                    draggingSlider.setValue(Math.max(draggingSlider.getMin(), Math.min(draggingSlider.getMax(), rawValue)));
                }
            }

            if (dragging && mouseButton == 0) {
                this.x = mouseX - dragX;
                this.y = mouseY - dragY;
            }
        }

        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            if (isHover(mouseX, mouseY, x, y, width, height)) {

                if (mouseButton == 0) {
                    dragging = true;
                    dragX = mouseX - x;
                    dragY = mouseY - y;
                }

                if (mouseButton == 1) {
                    dragging = false;
                    open = !open;
                }

                return;
            }

            if (open) {

                int offset = height - scrollOffset;

                for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {

                    if (isHover(mouseX, mouseY, x, y + offset, width, 14)) {

                        int gearSize = 12;
                        int gearX = x + width - gearSize - 4;
                        int gearY = y + offset + 1;

                        boolean overGear = mouseX >= gearX && mouseX <= gearX + gearSize && mouseY >= gearY && mouseY <= gearY + gearSize;
                        boolean overKeybind = mouseX >= x + width - 38 && mouseX < gearX && mouseY >= rowY && mouseY <= rowY + 14;

                        if (overKeybind && mouseButton == 0) {
                            listeningForKeybind = module;
                            return;
                        }

                        if (overGear && mouseButton == 0) {
                            module.open = !module.open;
                            return;
                        }

                        if (mouseButton == 0) {
                            module.toggle();
                        }

                        if (mouseButton == 1 || (hasSettings(module) && mouseX >= x + width - 12)) {
                            module.open = !module.open;
                        }

                        return;
                    }

                    offset += 14;

                    if (module.open && hasSettings(module)) {
                        for (Setting<?> setting : module.getSettings()) {

                            if (isHover(mouseX, mouseY, x, y + offset, width, 14)) {

                                if (setting instanceof BooleanSetting && mouseButton == 0) {
                                    ((BooleanSetting) setting).toggle();
                                }

                                if (setting instanceof ModeSetting && mouseButton == 0) {
                                    ((ModeSetting) setting).cycle();
                                }

                                if (setting instanceof NumberSetting && mouseButton == 0) {
                                    draggingSlider = (NumberSetting) setting;
                                    sliderX = x;
                                    sliderWidth = width;
                                    double snapped = getSnapped(mouseX);
                                    draggingSlider.setValue(snapped);
                                }

                                return;
                            }

                            offset += 14;
                        }
                    }
                }
            }
        }

        private double getSnapped(int mouseX) {
            double percent = (mouseX - x) / (double) width;
            percent = Math.max(0.0, Math.min(1.0, percent));
            double rawValue = draggingSlider.getMin() + percent * (draggingSlider.getMax() - draggingSlider.getMin());
            double increment = draggingSlider.getIncrement();
            double steps = (rawValue - draggingSlider.getMin()) / increment;
            double snapped = draggingSlider.getMin() + Math.round(steps) * increment;
            snapped = Math.round(snapped * 10000.0) / 10000.0;
            snapped = Math.max(draggingSlider.getMin(), Math.min(draggingSlider.getMax(), snapped));
            return snapped;
        }

        private boolean isHover(int mx, int my, int x, int y, int w, int h) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }

        public void scroll(int wheel) {
            if (!open || wheel == 0) return;
            int total = height;
            for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {
                total += 14;
                if (module.open && hasSettings(module)) total += module.getSettings().size() * 14;
            }
            int maxScroll = Math.max(0, total - Math.max(0, ClickGuiScreen.this.height - 24 - y));
            scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset + (wheel > 0 ? -28 : 28)));
        }

        private int getTotalHeight() {
            int total = height;
            for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {
                total += 14;
                if (module.open && hasSettings(module)) {
                    total += module.getSettings().size() * 14;
                }
            }
            return Math.min(total, Math.max(height, ClickGuiScreen.this.height - 24 - y));
        }
    }

}

