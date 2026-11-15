package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

public class Zoom extends Module {
    public final Setting.NumberSetting zoomFov = new Setting.NumberSetting("Zoom FOV", 30.0, 20.0, 60.0, 1.0);
    private float previousFov = 70.0F;

    public Zoom() {
        super("Zoom", "Narrows the field of view while enabled.", Category.Render);
        setKey(46);
        settings.add(zoomFov);
    }

    @Override
    public void onEnable() {
        if (mc != null && mc.gameSettings != null) {
            previousFov = mc.gameSettings.fovSetting;
            mc.gameSettings.fovSetting = zoomFov.getValue().floatValue();
        }
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.fovSetting = zoomFov.getValue().floatValue();
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.fovSetting = previousFov;
        }
    }
}
