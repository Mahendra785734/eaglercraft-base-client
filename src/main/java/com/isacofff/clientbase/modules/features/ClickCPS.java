package com.isacofff.clientbase.modules.features;

import java.util.ArrayList;
import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.lax1dude.eaglercraft.Mouse;
import net.minecraft.client.Minecraft;

public class ClickCPS extends Module {
    private final ArrayList<Long> leftClicks = new ArrayList<>();
    private final ArrayList<Long> rightClicks = new ArrayList<>();
    private boolean leftWasDown;
    private boolean rightWasDown;

    public ClickCPS() {
        super("ClickCPS", Category.Combat);
        this.description = "Tracks your left and right clicks per second.";
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null || mc.player == null) {
            leftWasDown = Mouse.isButtonDown(0);
            rightWasDown = Mouse.isButtonDown(1);
            return;
        }

        long now = Minecraft.getSystemTime();
        boolean leftDown = Mouse.isButtonDown(0);
        boolean rightDown = Mouse.isButtonDown(1);

        if (leftDown && !leftWasDown) leftClicks.add(now);
        if (rightDown && !rightWasDown) rightClicks.add(now);
        leftWasDown = leftDown;
        rightWasDown = rightDown;

        trim(leftClicks, now);
        trim(rightClicks, now);
    }

    private void trim(ArrayList<Long> clicks, long now) {
        while (!clicks.isEmpty() && now - clicks.get(0) > 1000L) {
            clicks.remove(0);
        }
    }

    public int getLeftCps() {
        return leftClicks.size();
    }

    public int getRightCps() {
        return rightClicks.size();
    }
}
