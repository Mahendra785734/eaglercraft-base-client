package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class SmoothCamera extends Module {
    private boolean previousValue = false;

    public SmoothCamera() {
        super("SmoothCamera", "Enables the game's smooth camera setting.", Category.Render);
        setKey(50);
    }

    @Override
    public void onEnable() {
        if (mc != null && mc.gameSettings != null) {
            previousValue = mc.gameSettings.smoothCamera;
            mc.gameSettings.smoothCamera = true;
        }
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.smoothCamera = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.smoothCamera = previousValue;
        }
    }
}
