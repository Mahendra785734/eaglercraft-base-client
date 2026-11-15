package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;

public class FullBright extends Module {

    public Setting.NumberSetting gamma = new Setting.NumberSetting("Gamma", 1000, 1, 1000, 1);
    private float previousGamma = 1.0F;

    public FullBright() {
        super("Fullbright", Category.Render);
        this.description = "Keeps the world bright while enabled.";
        settings.add(gamma);
    }

    private void applyGamma() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = gamma.getValue().floatValue();
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getMinecraft();
        previousGamma = mc.gameSettings.gammaSetting;
        applyGamma();
    }

    @Override
    public void onUpdate() {
        // Re-apply every tick so other settings updates do not silently turn it off.
        applyGamma();
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = previousGamma;
    }
}
