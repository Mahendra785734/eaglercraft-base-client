package com.isacofff.clientbase.modules;

import com.isacofff.clientbase.modules.features.*;
import com.isacofff.clientbase.Category;
import java.util.ArrayList;

public class Manager {
    public final ArrayList<Module> modules = new ArrayList<>();

    public void init() {
        modules.add(new ClickGui());
        modules.add(new KeySounds());
        modules.add(new KyzxxMusic());
        modules.add(new FullBright());
        modules.add(new AutoSprint());
        modules.add(new Zoom());
        modules.add(new NoHurtCam());
        modules.add(new Coordinates());
        modules.add(new ClickCPS());
        modules.add(new ArmorStatus());
        modules.add(new ModuleList());
        modules.add(new FPS());
        modules.add(new HudFeatures.DirectionHUD());
        modules.add(new HudFeatures.PotionHUD());
        modules.add(new HudFeatures.ItemCounter());
        modules.add(new HudFeatures.ClockHUD());
        modules.add(new HudFeatures.BiomeHUD());
        modules.add(new HudFeatures.SpeedHUD());
        modules.add(new HudFeatures.HeldDurability());
        modules.add(new HudFeatures.TotemCounter());
        modules.add(new HudFeatures.ArrowCounter());
        modules.add(new HudFeatures.DayCounter());
        modules.add(new HudFeatures.PingHUD());
        modules.add(new HudFeatures.FoodHUD());
        modules.add(new HudFeatures.XPDisplay());
        modules.add(new HudFeatures.HealthHUD());
        modules.add(new HudFeatures.WorldTime());
        modules.add(new HudFeatures.RotationHUD());
        modules.add(new HudFeatures.ChunkHUD());
        modules.add(new HudFeatures.LightLevel());
        modules.add(new HudFeatures.InventoryCount());
        modules.add(new HudFeatures.ArmorValue());
        modules.add(new HudFeatures.TargetHUD());
        modules.add(new HudFeatures.EntityCount());
        modules.add(new HudFeatures.DimensionHUD());
        modules.add(new HudFeatures.PlayerNameHUD());
        modules.add(new HudFeatures.SaturationHUD());
        modules.add(new HudFeatures.AirHUD());
        modules.add(new HudFeatures.ArmorCount());
        modules.add(new HudFeatures.HeldItemHUD());
        modules.add(new HudFeatures.SelectedSlotHUD());
        modules.add(new HudFeatures.WorldNameHUD());
        modules.add(new HudFeatures.LocalDifficultyHUD());
        modules.add(new HudFeatures.HideScoreboard());
        modules.add(new HudFeatures.HideBossBar());
        modules.add(new HudFeatures.HidePotionEffects());
        modules.add(new HudFeatures.HideHotbar());
        modules.add(new HudFeatures.HideCrosshair());
        modules.add(new HudFeatures.HidePlayerList());
        modules.add(new HudFeatures.HideTitles());
        modules.add(new HudFeatures.HideSubtitles());
        modules.add(new HudFeatures.HidePumpkin());
        modules.add(new HudFeatures.HidePortal());
        modules.add(new HudFeatures.HideVignette());
        // Defaults for the two most-used toggles.
        Module clickGui = getModuleByName("ClickGUI");
        if (clickGui != null) clickGui.setKeybind(54); // Right Shift
        Module fullBright = getModuleByName("FullBright");
        if (fullBright != null) fullBright.setKeybind(net.lax1dude.eaglercraft.KeyboardConstants.KEY_F);
        Module sprint = getModuleByName("Sprint");
        if (sprint != null) sprint.setKeybind(net.lax1dude.eaglercraft.KeyboardConstants.KEY_R);
    }

    private void register(Module module) {
        if (getModuleByName(module.getName()) == null) modules.add(module);
    }

    public void onKeyPressed(int keyCode) {
        if (keyCode == 0) return;
        for (Module module : modules) {
            if (module.getKeybind() == keyCode) module.toggle();
        }
    }

    public void onTick() {
        for (Module m : modules) if (m.isEnabled()) m.onUpdate();
    }

    public ArrayList<Module> getModules() { return modules; }

    public <T extends Module> T getModule(Class<T> classs) {
        for (Module m : modules) if (classs.isInstance(m)) return classs.cast(m);
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module m : modules) if (m.getName().equalsIgnoreCase(name)) return m;
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category c) {
        ArrayList<Module> list = new ArrayList<>();
        for (Module m : modules) if (m.getCategory() == c) list.add(m);
        return list;
    }
}
