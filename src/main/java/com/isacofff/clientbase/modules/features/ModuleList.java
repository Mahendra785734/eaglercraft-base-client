package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class ModuleList extends Module {
    public ModuleList() {
        super("ActiveModules", Category.Client);
        this.description = "Displays enabled modules with a draggable, customizable HUD.";
    }
}
