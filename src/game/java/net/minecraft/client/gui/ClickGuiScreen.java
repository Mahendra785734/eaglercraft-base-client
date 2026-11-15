package net.minecraft.client.gui;

import com.isacofff.clientbase.Client;
import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.modules.features.CatalogModule;
import com.isacofff.clientbase.settings.Setting;
import com.isacofff.clientbase.settings.Setting.BooleanSetting;
import com.isacofff.clientbase.settings.Setting.ModeSetting;
import com.isacofff.clientbase.settings.Setting.NumberSetting;
import net.lax1dude.eaglercraft.Keyboard;
import net.lax1dude.eaglercraft.KeyboardConstants;
import net.lax1dude.eaglercraft.Mouse;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Full-screen KYZXX module browser.
 *
 * Real modules can be enabled and configured. CatalogModule entries are
 * intentionally displayed as planned features and are never presented as
 * working toggles.
 */
public class ClickGuiScreen extends GuiScreen {

    private static final int GOLD = 0xFFFFC15A;
    private static final int GOLD_DIM = 0xFF936B38;
    private static final int BG = 0xF00A0F18;
    private static final int PANEL = 0xF0141B27;
    private static final int PANEL_ALT = 0xF01A2432;
    private static final int BORDER = 0xFF2B3748;
    private static final int TEXT = 0xFFF0F3F8;
    private static final int MUTED = 0xFF9AA7B8;
    private static final int GREEN = 0xFF54C58A;
    private static final int RED = 0xFFDF7777;
    private static final int ROW_HEIGHT = 31;
    private static final int SETTING_HEIGHT = 40;

    private Category selectedCategory = Category.Client;
    private Module selectedModule;
    private String searchText = "";
    private boolean searchFocused;
    private Module listeningForKeybind;
    private NumberSetting draggingSetting;
    private int moduleScroll;
    private int settingScroll;

    public ClickGuiScreen() {
        selectFirstVisibleModule();
    }

    private int frameX() { return 12; }
    private int frameY() { return 9; }
    private int frameW() { return Math.max(300, this.width - 24); }
    private int frameH() { return Math.max(200, this.height - 18); }
    private int tabsY() { return 42; }
    private int listX() { return frameX() + 16; }
    private int listW() { return Math.max(130, (frameW() - 48) / 2); }
    private int detailsX() { return listX() + listW() + 14; }
    private int detailsW() { return Math.max(120, frameX() + frameW() - 16 - detailsX()); }
    private int contentTop() { return 84; }
    private int contentBottom() { return this.height - 43; }
    private int listTop() { return contentTop() + 27; }
    private int listBottom() { return contentBottom(); }
    private int settingsTop() { return contentTop() + 137; }
    private int settingsBottom() { return contentBottom(); }

    private boolean isCatalog(Module module) {
        return module instanceof CatalogModule;
    }

    private ArrayList<Module> getVisibleModules(Category category) {
        ArrayList<Module> visible = new ArrayList<Module>();
        if (Client.INSTANCE == null || Client.INSTANCE.manager == null) return visible;

        String query = searchText.trim().toLowerCase();
        for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {
            if (query.isEmpty()
                    || module.getName().toLowerCase().contains(query)
                    || module.getDescription().toLowerCase().contains(query)) {
                visible.add(module);
            }
        }
        Collections.sort(visible, new Comparator<Module>() {
            @Override
            public int compare(Module a, Module b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
        return visible;
    }

    private int countModules(Category category) {
        return Client.INSTANCE == null || Client.INSTANCE.manager == null
                ? 0 : Client.INSTANCE.manager.getModulesByCategory(category).size();
    }

    private int countImplemented(Category category) {
        int count = 0;
        if (Client.INSTANCE == null || Client.INSTANCE.manager == null) return 0;
        for (Module module : Client.INSTANCE.manager.getModulesByCategory(category)) {
            if (!isCatalog(module)) count++;
        }
        return count;
    }

    private int countAllModules() {
        return Client.INSTANCE == null || Client.INSTANCE.manager == null
                ? 0 : Client.INSTANCE.manager.getModules().size();
    }

    private int countAllImplemented() {
        int count = 0;
        if (Client.INSTANCE == null || Client.INSTANCE.manager == null) return 0;
        for (Module module : Client.INSTANCE.manager.getModules()) {
            if (!isCatalog(module)) count++;
        }
        return count;
    }

    private void selectFirstVisibleModule() {
        ArrayList<Module> visible = getVisibleModules(selectedCategory);
        selectedModule = visible.isEmpty() ? null : visible.get(0);
        moduleScroll = 0;
        settingScroll = 0;
    }

    private void playClick() {
        mc.getSoundHandler().playSound(
                PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 0.65F));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, this.width, this.height, 0xB9080D15);

        int x = frameX();
        int y = frameY();
        int w = frameW();
        int h = frameH();

        drawRect(x, y, x + w, y + h, BG);
        drawRect(x, y, x + w, y + 2, GOLD);
        drawRect(x, y + 2, x + 1, y + h, GOLD_DIM);
        drawRect(x + w - 1, y + 2, x + w, y + h, BORDER);

        drawHeader(mouseX, mouseY);
        drawCategoryTabs(mouseX, mouseY);
        drawModuleList(mouseX, mouseY);
        drawSelectedModule(mouseX, mouseY);
        drawFooter(mouseX, mouseY);

        if (listeningForKeybind != null) {
            drawRect(x + 8, y + 7, x + w - 8, y + 30, 0xF02B2113);
            drawRect(x + 8, y + 7, x + w - 8, y + 8, GOLD);
            fontRendererObj.drawString("PRESS A KEY FOR " + listeningForKeybind.getName()
                    + "   |   ESC CANCEL   |   BACKSPACE CLEAR", x + 16, y + 15, GOLD);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawHeader(int mouseX, int mouseY) {
        int x = frameX() + 16;
        int y = frameY() + 9;
        fontRendererObj.drawString("KYZXX", x, y, GOLD);
        fontRendererObj.drawString("CLIENT", x + 42, y, TEXT);
        fontRendererObj.drawString("1.12.2", x + 83, y, MUTED);
        fontRendererObj.drawString("MODULE CONTROL CENTER", x, y + 14, MUTED);

        int searchW = Math.min(250, Math.max(120, this.width / 3));
        int searchX = frameX() + frameW() - searchW - 110;
        int searchY = frameY() + 8;
        drawRect(searchX, searchY, searchX + searchW, searchY + 25, BORDER);
        drawRect(searchX + 1, searchY + 1, searchX + searchW - 1, searchY + 24, PANEL);
        String label = searchText.isEmpty() ? "Search modules..." : searchText;
        fontRendererObj.drawString((searchFocused ? "> " : "") + trimToWidth(label, searchW - 16),
                searchX + 8, searchY + 9, searchText.isEmpty() ? MUTED : TEXT);

        int cosmeticsX = frameX() + frameW() - 94;
        drawButton(cosmeticsX, searchY, 78, 25, "COSMETICS",
                mouseX >= cosmeticsX && mouseX <= cosmeticsX + 78 && mouseY >= searchY && mouseY <= searchY + 25);
    }

    private void drawCategoryTabs(int mouseX, int mouseY) {
        Category[] categories = Category.values();
        int x = frameX() + 16;
        int y = tabsY();
        int totalW = frameW() - 32;
        int tabW = totalW / categories.length;

        for (int i = 0; i < categories.length; i++) {
            Category category = categories[i];
            int tabX = x + i * tabW;
            boolean selected = category == selectedCategory;
            boolean hovered = mouseX >= tabX && mouseX < tabX + tabW - 3
                    && mouseY >= y && mouseY <= y + 25;
            int background = selected ? 0xFF29251E : (hovered ? PANEL_ALT : PANEL);
            drawRect(tabX, y, tabX + tabW - 3, y + 25, background);
            drawRect(tabX, y, tabX + tabW - 3, y + 1, selected ? GOLD : BORDER);
            fontRendererObj.drawString(category.name().toUpperCase(), tabX + 7, y + 9,
                    selected ? GOLD : (hovered ? TEXT : MUTED));
            String count = String.valueOf(countModules(category));
            int countX = tabX + tabW - 11 - fontRendererObj.getStringWidth(count);
            fontRendererObj.drawString(count, countX, y + 9, selected ? TEXT : MUTED);
        }
    }

    private void drawModuleList(int mouseX, int mouseY) {
        int x = listX();
        int w = listW();
        int headerY = contentTop();
        int top = listTop();
        int bottom = listBottom();

        drawRect(x, headerY, x + w, bottom, PANEL);
        drawRect(x, headerY, x + w, headerY + 23, PANEL_ALT);
        fontRendererObj.drawString("MODULES", x + 9, headerY + 8, TEXT);
        String countText = getVisibleModules(selectedCategory).size() + " SHOWN";
        fontRendererObj.drawString(countText, x + w - fontRendererObj.getStringWidth(countText) - 9,
                headerY + 8, MUTED);

        ArrayList<Module> modules = getVisibleModules(selectedCategory);
        int availableRows = Math.max(1, (bottom - top) / ROW_HEIGHT);
        int maxScroll = Math.max(0, modules.size() - availableRows);
        moduleScroll = Math.max(0, Math.min(moduleScroll, maxScroll));

        for (int i = moduleScroll; i < modules.size() && i < moduleScroll + availableRows + 1; i++) {
            Module module = modules.get(i);
            int rowY = top + (i - moduleScroll) * ROW_HEIGHT;
            if (rowY + ROW_HEIGHT > bottom) break;

            boolean selected = module == selectedModule;
            boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= rowY && mouseY < rowY + ROW_HEIGHT;
            int rowColor = selected ? 0xFF29251E : (hovered ? 0xFF202B3A : PANEL);
            drawRect(x + 1, rowY, x + w - 1, rowY + ROW_HEIGHT - 1, rowColor);
            if (selected) drawRect(x + 1, rowY, x + 3, rowY + ROW_HEIGHT - 1, GOLD);
            if (!isCatalog(module) && module.isEnabled()) {
                drawRect(x + 4, rowY + 5, x + 5, rowY + ROW_HEIGHT - 6, GREEN);
            }

            int textColor = isCatalog(module) ? MUTED : TEXT;
            fontRendererObj.drawString(trimToWidth(module.getName(), w - 115), x + 10, rowY + 5, textColor);
            fontRendererObj.drawString(trimToWidth(module.getDescription(), w - 122), x + 10, rowY + 17, MUTED);

            String status = isCatalog(module) ? "PLANNED" : (module.isEnabled() ? "ON" : "OFF");
            int statusColor = isCatalog(module) ? GOLD_DIM : (module.isEnabled() ? GREEN : MUTED);
            int statusX = x + w - fontRendererObj.getStringWidth(status) - 11;
            fontRendererObj.drawString(status, statusX, rowY + 10, statusColor);
        }

        if (modules.isEmpty()) {
            fontRendererObj.drawString("No modules match this search.", x + 12, top + 12, MUTED);
        }
        if (modules.size() > availableRows) {
            String scrollText = "SCROLL TO BROWSE  " + (moduleScroll + 1) + "-" +
                    Math.min(modules.size(), moduleScroll + availableRows) + " / " + modules.size();
            fontRendererObj.drawString(scrollText, x + 8, bottom - 12, MUTED);
        }
    }

    private void drawSelectedModule(int mouseX, int mouseY) {
        int x = detailsX();
        int w = detailsW();
        int top = contentTop();
        int bottom = contentBottom();

        drawRect(x, top, x + w, bottom, PANEL);
        drawRect(x, top, x + w, top + 23, PANEL_ALT);
        fontRendererObj.drawString("MODULE DETAILS", x + 9, top + 8, TEXT);

        if (selectedModule == null) {
            fontRendererObj.drawString("Select a module from the list.", x + 14, top + 48, MUTED);
            return;
        }

        int y = top + 35;
        fontRendererObj.drawString(trimToWidth(selectedModule.getName(), w - 24), x + 12, y, GOLD);
        drawWrappedText(selectedModule.getDescription(), x + 12, y + 16, w - 24, MUTED, 11, 2);

        if (isCatalog(selectedModule)) {
            int badgeY = y + 53;
            drawRect(x + 12, badgeY, x + w - 12, badgeY + 23, 0xFF2A241A);
            drawRect(x + 12, badgeY, x + 14, badgeY + 23, GOLD_DIM);
            fontRendererObj.drawString("PLANNED FEATURE", x + 21, badgeY + 8, GOLD);
            drawWrappedText("This entry is part of the 100+ feature catalogue. It is not implemented yet, so it cannot be enabled or configured.", x + 12, badgeY + 36, w - 24, MUTED, 12, 4);
            return;
        }

        int buttonY = y + 55;
        boolean enabled = selectedModule.isEnabled();
        drawButton(x + 12, buttonY, 104, 25, enabled ? "ENABLED" : "DISABLED",
                mouseX >= x + 12 && mouseX <= x + 116 && mouseY >= buttonY && mouseY <= buttonY + 25);
        drawRect(x + 12, buttonY, x + 15, buttonY + 25, enabled ? GREEN : BORDER);

        int keyX = x + 126;
        int keyW = Math.max(72, w - 138);
        String keyName = selectedModule.getKeybind() == 0 ? "NONE" : Keyboard.getKeyName(selectedModule.getKeybind());
        String keyText = listeningForKeybind == selectedModule ? "PRESS KEY..." : "KEY: " + keyName;
        drawButton(keyX, buttonY, keyW, 25, keyText,
                mouseX >= keyX && mouseX <= keyX + keyW && mouseY >= buttonY && mouseY <= buttonY + 25);

        int settingHeaderY = top + 113;
        drawRect(x + 8, settingHeaderY, x + w - 8, settingHeaderY + 20, PANEL_ALT);
        fontRendererObj.drawString("SETTINGS", x + 15, settingHeaderY + 6, TEXT);

        ArrayList<Setting<?>> settings = selectedModule.getSettings();
        int startY = settingsTop();
        int available = Math.max(1, (settingsBottom() - startY) / SETTING_HEIGHT);
        int maxScroll = Math.max(0, settings.size() - available);
        settingScroll = Math.max(0, Math.min(settingScroll, maxScroll));

        if (settings.isEmpty()) {
            fontRendererObj.drawString("No additional settings for this module.", x + 14, startY + 10, MUTED);
            return;
        }

        for (int i = settingScroll; i < settings.size() && i < settingScroll + available + 1; i++) {
            int rowY = startY + (i - settingScroll) * SETTING_HEIGHT;
            if (rowY + SETTING_HEIGHT > bottom - 2) break;
            drawSetting(settings.get(i), x + 9, rowY, w - 18, mouseX, mouseY);
        }
    }

    private void drawSetting(Setting<?> setting, int x, int y, int w, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY < y + SETTING_HEIGHT - 3;
        drawRect(x, y, x + w, y + SETTING_HEIGHT - 3, hovered ? 0xFF202B3A : PANEL_ALT);
        fontRendererObj.drawString(setting.getName(), x + 8, y + 6, TEXT);

        if (setting instanceof BooleanSetting) {
            BooleanSetting value = (BooleanSetting) setting;
            String state = value.getValue().booleanValue() ? "ON" : "OFF";
            fontRendererObj.drawString(state, x + w - fontRendererObj.getStringWidth(state) - 10, y + 6,
                    value.getValue().booleanValue() ? GREEN : MUTED);
            fontRendererObj.drawString("CLICK TO TOGGLE", x + 8, y + 21, MUTED);
        } else if (setting instanceof ModeSetting) {
            ModeSetting value = (ModeSetting) setting;
            String mode = String.valueOf(value.getValue());
            fontRendererObj.drawString(trimToWidth(mode + "  >", w - 18), x + 8, y + 21, GOLD);
        } else if (setting instanceof NumberSetting) {
            NumberSetting value = (NumberSetting) setting;
            String number = String.valueOf(value.getValue());
            fontRendererObj.drawString(number, x + w - fontRendererObj.getStringWidth(number) - 10, y + 6, GOLD);
            int barX = x + 8;
            int barY = y + 29;
            int barW = w - 16;
            double range = value.getMax() - value.getMin();
            double percent = range <= 0.0 ? 0.0 : (value.getValue() - value.getMin()) / range;
            percent = Math.max(0.0, Math.min(1.0, percent));
            int fill = (int) (percent * barW);
            drawRect(barX, barY, barX + barW, barY + 3, BORDER);
            drawRect(barX, barY, barX + fill, barY + 3, GOLD);
            drawRect(barX + fill - 1, barY - 2, barX + fill + 2, barY + 5, TEXT);
        }
    }

    private void drawFooter(int mouseX, int mouseY) {
        int y = this.height - 31;
        drawRect(frameX() + 1, y - 3, frameX() + frameW() - 1, y + 17, 0xF0090E16);
        String counts = countAllModules() + " MODULE ENTRIES   |   " + countAllImplemented()
                + " IMPLEMENTED   |   " + (countAllModules() - countAllImplemented()) + " PLANNED";
        fontRendererObj.drawString(counts, frameX() + 12, y + 3, MUTED);

        int resetX = frameX() + frameW() - 250;
        drawButton(resetX, y - 1, 78, 19, "RESET ALL",
                mouseX >= resetX && mouseX <= resetX + 78 && mouseY >= y - 1 && mouseY <= y + 18);
        int hudX = resetX + 86;
        drawButton(hudX, y - 1, 82, 19, "HUD EDITOR",
                mouseX >= hudX && mouseX <= hudX + 82 && mouseY >= y - 1 && mouseY <= y + 18);
        int closeX = hudX + 90;
        drawButton(closeX, y - 1, 62, 19, "CLOSE",
                mouseX >= closeX && mouseX <= closeX + 62 && mouseY >= y - 1 && mouseY <= y + 18);
    }

    private void drawButton(int x, int y, int w, int h, String label, boolean hovered) {
        drawRect(x, y, x + w, y + h, hovered ? 0xFF303B4B : 0xFF202A38);
        drawRect(x, y, x + w, y + 1, hovered ? GOLD : BORDER);
        drawCenteredString(fontRendererObj, label, x + w / 2, y + (h - 8) / 2,
                hovered ? 0xFFFFD88A : TEXT);
    }

    private String trimToWidth(String text, int width) {
        if (text == null) return "";
        if (width <= 0) return "";
        String result = text;
        while (result.length() > 1 && fontRendererObj.getStringWidth(result) > width) {
            result = result.substring(0, result.length() - 1);
        }
        if (!result.equals(text) && result.length() > 3) result = result.substring(0, result.length() - 3) + "...";
        return result;
    }

    private void drawWrappedText(String text, int x, int y, int maxWidth, int color, int lineHeight, int maxLines) {
        if (text == null || text.isEmpty()) return;
        String[] words = text.split(" ");
        String line = "";
        int lineNumber = 0;
        for (int i = 0; i < words.length; i++) {
            String candidate = line.isEmpty() ? words[i] : line + " " + words[i];
            if (fontRendererObj.getStringWidth(candidate) > maxWidth && !line.isEmpty()) {
                fontRendererObj.drawString(line, x, y + lineNumber * lineHeight, color);
                lineNumber++;
                line = words[i];
                if (lineNumber >= maxLines) return;
            } else {
                line = candidate;
            }
        }
        if (!line.isEmpty() && lineNumber < maxLines) {
            fontRendererObj.drawString(trimToWidth(line, maxWidth), x, y + lineNumber * lineHeight, color);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton != 0 && mouseButton != 1) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }

        playClick();

        int searchW = Math.min(250, Math.max(120, this.width / 3));
        int searchX = frameX() + frameW() - searchW - 110;
        int searchY = frameY() + 8;
        if (mouseButton == 0 && mouseX >= searchX && mouseX <= searchX + searchW
                && mouseY >= searchY && mouseY <= searchY + 25) {
            searchFocused = true;
            return;
        }
        searchFocused = false;

        int cosmeticsX = frameX() + frameW() - 94;
        if (mouseButton == 0 && mouseX >= cosmeticsX && mouseX <= cosmeticsX + 78
                && mouseY >= searchY && mouseY <= searchY + 25) {
            mc.displayGuiScreen(new net.lax1dude.eaglercraft.profile.GuiScreenEditProfile(this));
            return;
        }

        if (mouseButton == 0 && mouseY >= tabsY() && mouseY <= tabsY() + 25
                && mouseX >= frameX() + 16 && mouseX <= frameX() + frameW() - 16) {
            int tabW = (frameW() - 32) / Category.values().length;
            int index = (mouseX - (frameX() + 16)) / tabW;
            index = Math.max(0, Math.min(Category.values().length - 1, index));
            selectedCategory = Category.values()[index];
            selectFirstVisibleModule();
            return;
        }

        int footerY = this.height - 34;
        int resetX = frameX() + frameW() - 250;
        int hudX = resetX + 86;
        int closeX = hudX + 90;
        if (mouseButton == 0 && mouseY >= footerY && mouseY <= footerY + 20) {
            if (mouseX >= resetX && mouseX <= resetX + 78) {
                resetAllModules();
                return;
            }
            if (mouseX >= hudX && mouseX <= hudX + 82) {
                mc.displayGuiScreen(new HudEditorScreen());
                return;
            }
            if (mouseX >= closeX && mouseX <= closeX + 62) {
                closeGui();
                return;
            }
        }

        ArrayList<Module> modules = getVisibleModules(selectedCategory);
        int listX = listX();
        int listW = listW();
        int rowY = listTop();
        int rowIndex = moduleScroll + (mouseY - rowY) / ROW_HEIGHT;
        int availableRows = Math.max(0, (listBottom() - rowY) / ROW_HEIGHT);
        if (mouseButton == 0 && mouseX >= listX && mouseX <= listX + listW
                && mouseY >= rowY && mouseY < rowY + availableRows * ROW_HEIGHT
                && rowIndex >= 0 && rowIndex < modules.size()) {
            Module clicked = modules.get(rowIndex);
            selectedModule = clicked;
            settingScroll = 0;
            int toggleZoneX = listX + listW - 56;
            if (mouseX >= toggleZoneX && !isCatalog(clicked)) {
                clicked.toggle();
            }
            return;
        }

        if (selectedModule != null && mouseButton == 0 && !isCatalog(selectedModule)) {
            int detailsX = detailsX();
            int buttonY = contentTop() + 35 + 55;
            if (mouseX >= detailsX + 12 && mouseX <= detailsX + 116
                    && mouseY >= buttonY && mouseY <= buttonY + 25) {
                selectedModule.toggle();
                return;
            }
            int keyX = detailsX + 126;
            int keyW = Math.max(72, detailsW() - 138);
            if (mouseX >= keyX && mouseX <= keyX + keyW && mouseY >= buttonY && mouseY <= buttonY + 25) {
                listeningForKeybind = selectedModule;
                return;
            }

            ArrayList<Setting<?>> settings = selectedModule.getSettings();
            int startY = settingsTop();
            int settingIndex = settingScroll + (mouseY - startY) / SETTING_HEIGHT;
            int rows = Math.max(0, (settingsBottom() - startY) / SETTING_HEIGHT);
            if (mouseX >= detailsX + 9 && mouseX <= detailsX + detailsW() - 9
                    && mouseY >= startY && mouseY < startY + rows * SETTING_HEIGHT
                    && settingIndex >= 0 && settingIndex < settings.size()) {
                Setting<?> setting = settings.get(settingIndex);
                if (setting instanceof BooleanSetting) {
                    ((BooleanSetting) setting).toggle();
                } else if (setting instanceof ModeSetting) {
                    ((ModeSetting) setting).cycle();
                } else if (setting instanceof NumberSetting) {
                    draggingSetting = (NumberSetting) setting;
                    updateDraggingSetting(mouseX);
                }
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void resetAllModules() {
        if (Client.INSTANCE == null || Client.INSTANCE.manager == null) return;
        for (Module module : Client.INSTANCE.manager.getModules()) {
            if (!isCatalog(module) && module.isEnabled() && !"ClickGUI".equalsIgnoreCase(module.getName())) {
                module.toggle();
            }
        }
    }

    private void closeGui() {
        Module clickGui = Client.INSTANCE == null || Client.INSTANCE.manager == null
                ? null : Client.INSTANCE.manager.getModuleByName("ClickGUI");
        if (clickGui != null && clickGui.isEnabled()) {
            clickGui.toggle();
        } else {
            mc.displayGuiScreen(null);
        }
    }

    private void updateDraggingSetting(int mouseX) {
        if (draggingSetting == null) return;
        int x = detailsX() + 17;
        int w = detailsW() - 34;
        double percent = (mouseX - x) / (double) w;
        percent = Math.max(0.0, Math.min(1.0, percent));
        double range = draggingSetting.getMax() - draggingSetting.getMin();
        double value = draggingSetting.getMin() + percent * range;
        double increment = draggingSetting.getIncrement();
        if (increment > 0.0) {
            value = draggingSetting.getMin()
                    + Math.round((value - draggingSetting.getMin()) / increment) * increment;
        }
        value = Math.round(value * 10000.0) / 10000.0;
        value = Math.max(draggingSetting.getMin(), Math.min(draggingSetting.getMax(), value));
        draggingSetting.setValue(value);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingSetting != null && clickedMouseButton == 0) updateDraggingSetting(mouseX);
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getDWheel();
        if (wheel == 0) return;

        int mouseX = Mouse.getX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
        if (mouseX >= listX() && mouseX <= listX() + listW()
                && mouseY >= listTop() && mouseY <= listBottom()) {
            moduleScroll = Math.max(0, moduleScroll + (wheel > 0 ? -3 : 3));
        } else if (mouseX >= detailsX() && mouseX <= detailsX() + detailsW()
                && mouseY >= settingsTop() && mouseY <= settingsBottom()) {
            settingScroll = Math.max(0, settingScroll + (wheel > 0 ? -1 : 1));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (listeningForKeybind != null) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                listeningForKeybind = null;
            } else {
                listeningForKeybind.setKeybind(keyCode == 14 || keyCode == 211 ? 0 : keyCode);
                listeningForKeybind = null;
            }
            return;
        }

        if (searchFocused) {
            if (keyCode == KeyboardConstants.KEY_ESCAPE) {
                searchFocused = false;
                return;
            }
            if (keyCode == 14 || keyCode == 211) {
                if (!searchText.isEmpty()) searchText = searchText.substring(0, searchText.length() - 1);
                moduleScroll = 0;
                selectFirstVisibleModule();
                return;
            }
            if (typedChar >= 32 && typedChar <= 126 && searchText.length() < 40) {
                searchText += typedChar;
                moduleScroll = 0;
                selectFirstVisibleModule();
            }
            return;
        }

        if (keyCode == KeyboardConstants.KEY_ESCAPE || keyCode == KeyboardConstants.KEY_RSHIFT) {
            closeGui();
        }
    }

    @Override
    public void updateScreen() {
        if (!Mouse.isButtonDown(0)) draggingSetting = null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
