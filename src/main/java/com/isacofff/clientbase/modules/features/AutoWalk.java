package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("AutoWalk", "Automatically holds the forward movement key while enabled.", Category.Movement);
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.gameSettings != null && mc.player != null) {
            mc.gameSettings.keyBindForward.pressed = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.keyBindForward.pressed = false;
        }
    }
}
