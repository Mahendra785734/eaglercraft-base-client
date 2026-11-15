package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ClickGuiScreen;

public class ClickGui extends Module {

        public ClickGui() {
            super("ClickGUI", "Opens the KYZXX client menu.", Category.Client);
            setKey(54);

            this.description = "Controls the client's gui.";
        }

        @Override
    public void onEnable() {
            super.onEnable();
            Minecraft.getMinecraft().displayGuiScreen(new ClickGuiScreen());
    }

    @Override
    public void onDisable() {
            super.onDisable();
            if (Minecraft.getMinecraft().currentScreen instanceof ClickGuiScreen) {
                Minecraft.getMinecraft().displayGuiScreen(null);
            }
    }
}
