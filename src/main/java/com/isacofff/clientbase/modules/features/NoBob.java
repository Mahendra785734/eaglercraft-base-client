package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class NoBob extends Module {
    private boolean previousValue = true;

    public NoBob() {
        super("NoBob", "Disables first-person camera bobbing.", Category.Render);
        setKey(48);
    }

    @Override
    public void onEnable() {
        if (mc != null && mc.gameSettings != null) {
            previousValue = mc.gameSettings.viewBobbing;
            mc.gameSettings.viewBobbing = false;
        }
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.viewBobbing = false;
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.viewBobbing = previousValue;
        }
    }
}
