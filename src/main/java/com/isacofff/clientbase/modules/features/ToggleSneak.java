package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class ToggleSneak extends Module {
    public ToggleSneak() {
        super("ToggleSneak", "Keeps your player sneaking while enabled.", Category.Movement);
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.player != null) {
            mc.player.setSneaking(true);
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.player != null) {
            mc.player.setSneaking(false);
        }
    }
}
