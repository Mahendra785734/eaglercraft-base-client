package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

public class KyzxxHUD extends Module {
    public final Setting.BooleanSetting watermark = new Setting.BooleanSetting("Watermark", true);
    public final Setting.BooleanSetting keystrokes = new Setting.BooleanSetting("Keystrokes", true);
    public final Setting.BooleanSetting moduleList = new Setting.BooleanSetting("Module List", true);

    public KyzxxHUD() {
        super("HUD", "Shows the KYZXX watermark, keystrokes and enabled module list.", Category.Render);
        setKey(35);
        settings.add(watermark);
        settings.add(keystrokes);
        settings.add(moduleList);
    }
}
