package com.isacofff.clientbase.modules.features;
import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
public class CustomFOV extends Module {
    public final Setting.NumberSetting fov = new Setting.NumberSetting("FOV", 90.0, 30.0, 110.0, 1.0);
    private float previousFov = 70.0F;
    public CustomFOV() {
        super("CustomFOV", "Sets a custom field of view while enabled.", Category.Render);
        settings.add(fov);
    }
    @Override public void onEnable() {
        if (mc != null && mc.gameSettings != null) {
            previousFov = mc.gameSettings.fovSetting;
            mc.gameSettings.fovSetting = fov.getValue().floatValue();
        }
    }
    @Override public void onUpdate() {
        if (mc != null && mc.gameSettings != null) mc.gameSettings.fovSetting = fov.getValue().floatValue();
    }
    @Override public void onDisable() {
        if (mc != null && mc.gameSettings != null) mc.gameSettings.fovSetting = previousFov;
    }
}
