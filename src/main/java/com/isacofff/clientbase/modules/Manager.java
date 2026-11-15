package com.isacofff.clientbase.modules;

import com.isacofff.clientbase.modules.features.ClickGui;
import com.isacofff.clientbase.modules.features.ExampleModule;
import com.isacofff.clientbase.modules.features.FullBright;
import com.isacofff.clientbase.modules.features.AutoSprint;
import com.isacofff.clientbase.modules.features.Zoom;
import com.isacofff.clientbase.modules.features.NoHurtCam;
import com.isacofff.clientbase.modules.features.Keystrokes;
import com.isacofff.clientbase.modules.features.Coordinates;
import com.isacofff.clientbase.Category;

import java.util.ArrayList;

public class Manager {

    public final ArrayList<Module> modules = new ArrayList<>();
    //Add the modules here for them to appear in the ClickGui
    public void init() {
    modules.add(new ClickGui());
    modules.add(new FullBright());
    modules.add(new ExampleModule());
    modules.add(new AutoSprint());
    modules.add(new Zoom());
    modules.add(new NoHurtCam());
    modules.add(new Keystrokes());
    modules.add(new Coordinates());
    }

    public void onTick() {
        for (Module m : modules) {
            if (m.isEnabled()) {
                m.onUpdate();
            }
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> classs) {

        for (Module m : modules) {
            if (classs.isInstance(m)) return classs.cast(m);
        }
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category c) {
        ArrayList<Module> list = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == c) list.add(m);
        }
        return list;
    }
}
