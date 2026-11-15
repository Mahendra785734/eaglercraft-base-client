package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.Minecraft;

public class AutoSprint extends Module {

    public AutoSprint() {
        super("AutoSprint", Category.Movement);
        this.description = "Automatically sprints while moving forward.";
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
}
