package com.isacofff.clientbase.modules;

import com.isacofff.clientbase.modules.features.ClickGui;
import com.isacofff.clientbase.modules.features.FullBright;
import com.isacofff.clientbase.modules.features.ToggleSprint;
import com.isacofff.clientbase.Category;

import java.util.ArrayList;

public class Manager {

    public final ArrayList<Module> modules = new ArrayList<>();

    public void init() {
        modules.clear();
        modules.add(new ClickGui());
        modules.add(new FullBright());
        modules.add(new ToggleSprint());
    }

    public void onKey(int key) {
        if (key <= 0) return;
        for (Module module : modules) {
            if (module.getKey() == key) module.toggle();
        }
    }

    public void onTick() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> type) {
        for (Module module : modules) {
            if (type.isInstance(module)) return type.cast(module);
        }
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) return module;
        }
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> result = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) result.add(module);
        }
        return result;
    }
}
