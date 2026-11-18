package com.isacofff.clientbase.modules;

import com.isacofff.clientbase.settings.Setting;
import com.isacofff.clientbase.Category;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public abstract class Module {

    private String name;
    private Category category;
    private boolean enabled;
    private int key = 0;
    protected Minecraft mc = Minecraft.getMinecraft();

    public boolean open = false;

    public ArrayList<Setting<?>> settings = new ArrayList<>();

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {return this.name;
    }

    public Category getCategory() { return category; }

    public boolean isEnabled() { return enabled; }

    public int getKey() { return key; }

    public void setKey(int key) { this.key = key; }

    public void toggle() {
        setEnabled(!this.enabled);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }



    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    public String description = "- - -";



    public String getDescription() {
        return this.description;
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onUpdate() {}
    public void onRender2D(float partialTicks) {}

}
