package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.lax1dude.eaglercraft.Keyboard;
import net.minecraft.client.Minecraft;

public class Zoom extends Module {

    private static final int ZOOM_KEY = 46; // C
    private static final float ZOOM_FOV = 30.0F;
    private float previousFov = 70.0F;
    private boolean zooming = false;

    public Zoom() {
        super("Zoom", Category.Render);
        this.description = "Hold C while enabled to zoom in.";
    }

    @Override
    public void onEnable() {
        previousFov = Minecraft.getMinecraft().gameSettings.fovSetting;
        zooming = false;
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) {
            return;
        }

        if (Keyboard.isKeyDown(ZOOM_KEY)) {
            if (!zooming) {
                previousFov = mc.gameSettings.fovSetting;
                zooming = true;
            }
            mc.gameSettings.fovSetting = ZOOM_FOV;
        } else if (zooming) {
            mc.gameSettings.fovSetting = previousFov;
            zooming = false;
        }
    }

    @Override
    public void onDisable() {
        if (zooming) {
            Minecraft.getMinecraft().gameSettings.fovSetting = previousFov;
            zooming = false;
        }
    }
}
