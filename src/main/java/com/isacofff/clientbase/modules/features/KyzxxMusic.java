package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

/** Original KYZXX melody played with the vanilla note-block harp instrument. */
public class KyzxxMusic extends Module {
    public final Setting.NumberSetting volume = new Setting.NumberSetting("Volume", 0.22D, 0.05D, 0.8D, 0.05D);
    private static final int[] NOTES = {12, 16, 19, 24, 19, 16, 14, 17, 21, 24, 22, 19, 17, 16, 12, 7};
    private int noteIndex;
    private long nextNoteAt;

    public KyzxxMusic() {
        super("KYZXX Music", Category.Client);
        this.description = "Plays an original looping KYZXX theme. Toggle it off to stop.";
        settings.add(volume);
    }

    @Override
    public void onEnable() {
        noteIndex = 0;
        nextNoteAt = 0L;
    }

    @Override
    public void onUpdate() {
        long now = Minecraft.getSystemTime();
        if (now < nextNoteAt) return;
        int note = NOTES[noteIndex];
        float pitch = (float) Math.pow(2.0D, (note - 12) / 12.0D);
        Minecraft.getMinecraft().getSoundHandler().playSound(
                new PositionedSoundRecord(SoundEvents.BLOCK_NOTE_HARP, SoundCategory.MASTER,
                        volume.getValue().floatValue(), pitch, 0.0F, 0.0F, 0.0F));
        noteIndex = (noteIndex + 1) % NOTES.length;
        nextNoteAt = now + 230L;
    }
}
