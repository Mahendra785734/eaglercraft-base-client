package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class FPS extends Module {
    public FPS() {
        super("FPS", Category.Render);
        this.description = "Displays your current client FPS.";
    }
}
