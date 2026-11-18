package net.eaglercraft.client.module;

import net.eaglercraft.client.module.impl.hud.FPSDisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Explicit module registry and event dispatcher.
 * Registration is static and reflection-free for TeaVM.
 */
public class ModuleManager {

    private final List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        registerModules();
    }

    private void registerModules() {
        addModule(new FPSDisplay());
    }

    private void addModule(Module module) {
        this.modules.add(module);
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(this.modules);
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> result = new ArrayList<Module>();
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (module.getCategory() == category) {
                result.add(module);
            }
        }
        return result;
    }

    public Module getModuleByName(String name) {
        if (name == null) {
            return null;
        }
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public void onKeyPress(int keyCode) {
        if (keyCode <= 0) {
            return;
        }
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (module.getKeyCode() == keyCode) {
                module.toggle();
            }
        }
    }

    public void onTick() {
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (module.isEnabled()) {
                module.onTick();
            }
        }
    }

    public void onRender2D(float partialTicks) {
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (module.isEnabled()) {
                module.onRender2D(partialTicks);
            }
        }
    }
}
