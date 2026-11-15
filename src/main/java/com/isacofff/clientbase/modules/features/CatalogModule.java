package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;

/**
 * Registry-backed module shell for the expanded KYZXX module catalog.
 * These entries expose toggles and editable settings in ClickGUI. Feature-specific
 * game hooks must be implemented separately; this class intentionally does not
 * spoof packets, bypass server protections, or automate destructive actions.
 */
public final class CatalogModule extends Module {
    public final Setting.ModeSetting displayMode =
            new Setting.ModeSetting("Mode", "Visual", "Visual", "Assist", "Diagnostics");
    public final Setting.NumberSetting refreshRate =
            new Setting.NumberSetting("Refresh Rate", 5.0D, 1.0D, 20.0D, 1.0D);
    public final Setting.BooleanSetting showIndicator =
            new Setting.BooleanSetting("Show Indicator", true);

    public CatalogModule(String name, String description, Category category) {
        super(name, description, category);
        settings.add(displayMode);
        settings.add(refreshRate);
        settings.add(showIndicator);
    }
}
