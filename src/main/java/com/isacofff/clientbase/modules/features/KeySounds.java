package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

/** Lightweight, configurable key feedback using the client's built-in UI sound. */
public class KeySounds extends Module {
    public final Setting.NumberSetting volume = new Setting.NumberSetting("Volume", 0.35D, 0.05D, 1.0D, 0.05D);
    public final Setting.BooleanSetting movementKeys = new Setting.BooleanSetting("Movement Keys", true);
    public final Setting.BooleanSetting otherKeys = new Setting.BooleanSetting("Other Keys", true);

    public KeySounds() {
        super("KeySounds", Category.Client);
        this.description = "Plays a soft sound when keys are pressed, including movement, jump, sneak and typing.";
        settings.add(volume);
        settings.add(movementKeys);
        settings.add(otherKeys);
    }

    public void play(int keyCode) {
        boolean movement = keyCode == 17 || keyCode == 30 || keyCode == 31 || keyCode == 32
                || keyCode == 57 || keyCode == 42 || keyCode == 54;
        if (movement ? !movementKeys.getValue() : !otherKeys.getValue()) return;
        float pitch = keyCode == 57 ? 1.18F : (keyCode == 42 || keyCode == 54 ? 0.72F
                : (keyCode == 17 || keyCode == 30 || keyCode == 31 || keyCode == 32 ? 0.92F : 1.04F));
        Minecraft.getMinecraft().getSoundHandler().playSound(
                new PositionedSoundRecord(SoundEvents.UI_BUTTON_CLICK, SoundCategory.MASTER,
                        volume.getValue().floatValue(), pitch, 0.0F, 0.0F, 0.0F));
    }
}
