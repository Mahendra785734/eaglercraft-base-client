package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

public class ModuleList extends Module {
    public final Setting.ModeSetting color = new Setting.ModeSetting("Color", "GOLD",
            "GOLD", "TEAL", "BLUE", "PINK", "PURPLE", "WHITE", "GREEN", "ORANGE", "RAINBOW");
    public final Setting.BooleanSetting background = new Setting.BooleanSetting("Background", true);
    public final Setting.ModeSetting backgroundStyle = new Setting.ModeSetting("BG Style", "SOLID",
            "SOLID", "GLASS", "OUTLINE");
    public final Setting.ModeSetting accent = new Setting.ModeSetting("Accent", "LEFT",
            "LEFT", "TOP", "RIGHT", "BOTTOM", "NONE");
    public final Setting.ModeSetting accentColor = new Setting.ModeSetting("Accent Color", "MATCH",
            "MATCH", "GOLD", "TEAL", "BLUE", "PINK", "PURPLE", "WHITE", "GREEN", "ORANGE", "RAINBOW");

    public ModuleList() {
        super("ActiveModules", Category.Client);
        this.description = "Displays enabled modules with editable colors, backgrounds, and accents.";
        settings.add(color);
        settings.add(background);
        settings.add(backgroundStyle);
        settings.add(accent);
        settings.add(accentColor);
    }

    private void applySettings() {
        ActiveModulesHud.setColorMode(color.getValue());
        ActiveModulesHud.background = background.getValue();
        ActiveModulesHud.backgroundStyle = backgroundStyle.getValue();
        ActiveModulesHud.accentMode = accent.getValue();
        ActiveModulesHud.accentColorMode = accentColor.getValue();
        ActiveModulesHud.leftAccent = "LEFT".equalsIgnoreCase(accent.getValue());
    }

    @Override
    public void onEnable() {
        applySettings();
    }

    @Override
    public void onUpdate() {
        applySettings();
    }
}
