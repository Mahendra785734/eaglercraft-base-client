package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import java.util.HashSet;
import java.util.Set;

/**
 * Browser-safe CreamyKeys-style feedback. Sounds are generated only for key
 * events delivered to the Minecraft client; this never installs a system-wide
 * keyboard hook or reads keystrokes from other browser tabs/applications.
 */
public class KeySounds extends Module {
    public final Setting.NumberSetting volume = new Setting.NumberSetting("Volume", 0.35D, 0.05D, 1.0D, 0.05D);
    public final Setting.BooleanSetting movementKeys = new Setting.BooleanSetting("Movement Keys", true);
    public final Setting.BooleanSetting otherKeys = new Setting.BooleanSetting("Other Keys", true);
    public final Setting.BooleanSetting playOnRepeat = new Setting.BooleanSetting("Play on Repeat", false);

    private final Set<Integer> pressedKeys = new HashSet<Integer>();

    public KeySounds() {
        super("CreamyKeys", Category.Client);
        this.description = "Plays soft, configurable sounds for keys pressed inside the game client.";
        settings.add(volume);
        settings.add(movementKeys);
        settings.add(otherKeys);
        settings.add(playOnRepeat);
    }

    @Override
    public void onEnable() {
        pressedKeys.clear();
    }

    @Override
    public void onDisable() {
        pressedKeys.clear();
    }

    public void release(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    public void play(int keyCode) {
        boolean fresh = pressedKeys.add(keyCode);
        if (!fresh && !playOnRepeat.getValue()) return;

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
