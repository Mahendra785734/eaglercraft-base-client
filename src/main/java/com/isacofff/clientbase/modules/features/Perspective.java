package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class Perspective extends Module {
    private int previousPerspective = 0;

    public Perspective() {
        super("Perspective", "Keeps the camera in third-person view.", Category.Render);
        setKey(25);
    }

    @Override
    public void onEnable() {
        if (mc != null && mc.gameSettings != null) {
            previousPerspective = mc.gameSettings.thirdPersonView;
            mc.gameSettings.thirdPersonView = 1;
        }
    }

    @Override
    public void onUpdate() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.thirdPersonView = 1;
        }
    }

    @Override
    public void onDisable() {
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.thirdPersonView = previousPerspective;
        }
    }
}
