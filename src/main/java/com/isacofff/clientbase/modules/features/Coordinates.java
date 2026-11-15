package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class Coordinates extends Module {

    public Coordinates() {
        super("Coordinates", Category.Render);
        this.description = "Displays your current XYZ coordinates on screen.";
    }
}
