package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class AutoSprint extends Module {

    private boolean wasSprinting;

    public AutoSprint() {
        // Use the same name as the old catalog entry so the real module
        // replaces the non-functional Sprint placeholder during registration.
        super("Sprint", Category.Movement);
        this.description = "Automatically sprints while moving forward.";
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getMinecraft();
        wasSprinting = mc.player != null && mc.player.isSprinting();
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null && mc.currentScreen == null
                && mc.gameSettings.keyBindForward.isKeyDown()
                && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null && !wasSprinting) mc.player.setSprinting(false);
    }
}
