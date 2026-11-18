package com.isacofff.clientbase.modules;

import com.isacofff.clientbase.modules.features.ClickGui;
import com.isacofff.clientbase.modules.features.FullBright;
import com.isacofff.clientbase.modules.features.ToggleSprint;
import com.isacofff.clientbase.modules.features.KyzxxHUD;
import com.isacofff.clientbase.modules.features.FPSDisplay;
import com.isacofff.clientbase.modules.features.Zoom;
import com.isacofff.clientbase.modules.features.NoBob;
import com.isacofff.clientbase.modules.features.NoHurtCam;
import com.isacofff.clientbase.modules.features.Perspective;
import com.isacofff.clientbase.modules.features.SmoothCamera;
import com.isacofff.clientbase.modules.features.NoPumpkinOverlay;
import com.isacofff.clientbase.modules.features.NoPortalOverlay;
import com.isacofff.clientbase.modules.features.NoBossBar;
import com.isacofff.clientbase.modules.features.NoPotionHUD;
import com.isacofff.clientbase.modules.features.NoScoreboard;
import com.isacofff.clientbase.modules.features.NoVignette;
import com.isacofff.clientbase.modules.features.CustomFOV;
import com.isacofff.clientbase.Category;

import java.util.ArrayList;

public class Manager {

    public final ArrayList<Module> modules = new ArrayList<>();

    public void init() {
        modules.clear();
        modules.add(new ClickGui());
        modules.add(new FullBright());
        modules.add(new ToggleSprint());
        modules.add(new KyzxxHUD());
        modules.add(new FPSDisplay());
        modules.add(new Zoom());
        modules.add(new NoBob());
        modules.add(new NoHurtCam());
        modules.add(new Perspective());
        modules.add(new SmoothCamera());
        modules.add(new NoPumpkinOverlay());
        modules.add(new NoPortalOverlay());
        modules.add(new NoBossBar());
        modules.add(new NoPotionHUD());
        modules.add(new NoScoreboard());
        modules.add(new NoVignette());
        modules.add(new CustomFOV());
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
