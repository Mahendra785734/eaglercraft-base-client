package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

public class ModuleList extends Module {
    public final Setting.ModeSetting color = new Setting.ModeSetting("Color", "GOLD",
            "GOLD", "TEAL", "BLUE", "PINK", "PURPLE", "WHITE", "GREEN", "ORANGE", "RAINBOW");
    public final Setting.BooleanSetting background = new Setting.BooleanSetting("Background", true);
    public final Setting.BooleanSetting leftAccent = new Setting.BooleanSetting("Left Accent", true);

    public ModuleList() {
        super("ActiveModules", Category.Client);
        this.description = "Displays enabled modules with right-click settings for color and style.";
        settings.add(color);
        settings.add(background);
        settings.add(leftAccent);
    }

    @Override
    public void onUpdate() {
        ActiveModulesHud.setColorMode(color.getValue());
        ActiveModulesHud.background = background.getValue();
        ActiveModulesHud.leftAccent = leftAccent.getValue();
    }
}