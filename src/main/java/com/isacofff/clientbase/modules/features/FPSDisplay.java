package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

/**
 * Displays the local client's current FPS and the local player's coordinates.
 *
 * The actual drawing is performed by GuiIngame so the module uses the same
 * font renderer and HUD coordinate system as the rest of the client.
 */
public class FPSDisplay extends Module {

    public FPSDisplay() {
        super("FPS Display", "Displays FPS and your current coordinates.", Category.Render);
    }
}
