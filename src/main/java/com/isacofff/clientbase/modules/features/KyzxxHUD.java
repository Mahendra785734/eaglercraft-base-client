package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

public class KyzxxHUD extends Module {
    public final Setting.BooleanSetting watermark = new Setting.BooleanSetting("Watermark", true);
    public final Setting.BooleanSetting fps = new Setting.BooleanSetting("FPS", true);
    public final Setting.BooleanSetting coordinates = new Setting.BooleanSetting("Coordinates", true);
    public final Setting.BooleanSetting keystrokes = new Setting.BooleanSetting("Keystrokes", true);

    public KyzxxHUD() {
        super("HUD", "Shows a KYZXX watermark, FPS, coordinates and keystrokes.", Category.Render);
        setKey(35);
        settings.add(watermark);
        settings.add(fps);
        settings.add(coordinates);
        settings.add(keystrokes);
    }
}
