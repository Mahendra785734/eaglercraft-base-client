package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

public class ArmorStatus extends Module {
    public final Setting.BooleanSetting showDurability = new Setting.BooleanSetting("Durability", true);
    public final Setting.BooleanSetting showBar = new Setting.BooleanSetting("Durability Bar", true);
    public final Setting.BooleanSetting showPercent = new Setting.BooleanSetting("Percent", true);
    public final Setting.BooleanSetting showName = new Setting.BooleanSetting("Item Names", false);
    public final Setting.BooleanSetting showBackground = new Setting.BooleanSetting("Background", true);

    public ArmorStatus() {
        super("ArmorStatus", Category.Player);
        this.description = "Shows real armor icons, durability bars, and remaining durability.";
        settings.add(showDurability);
        settings.add(showBar);
        settings.add(showPercent);
        settings.add(showName);
        settings.add(showBackground);
    }
}
