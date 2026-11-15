package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class ToggleSprint extends Module {

    public ToggleSprint() {
        super("Sprint", "Keeps sprint enabled while this module is active.", Category.Movement);
        setKey(19);
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.player != null) {
            mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.player != null) {
            mc.player.setSprinting(false);
        }
    }
}
