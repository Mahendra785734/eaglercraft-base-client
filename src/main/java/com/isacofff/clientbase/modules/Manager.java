package com.isacofff.clientbase.modules;

import com.isacofff.clientbase.modules.features.ClickGui;
import com.isacofff.clientbase.modules.features.FullBright;
import com.isacofff.clientbase.modules.features.AutoSprint;
import com.isacofff.clientbase.modules.features.Zoom;
import com.isacofff.clientbase.modules.features.NoHurtCam;
import com.isacofff.clientbase.modules.features.Keystrokes;
import com.isacofff.clientbase.modules.features.Coordinates;
import com.isacofff.clientbase.modules.features.ClickCPS;
import com.isacofff.clientbase.modules.features.ArmorStatus;
import com.isacofff.clientbase.modules.features.ModuleList;
import com.isacofff.clientbase.modules.features.FPS;
import com.isacofff.clientbase.modules.features.HudFeatures;
import com.isacofff.clientbase.Category;

import java.util.ArrayList;

public class Manager {

    public final ArrayList<Module> modules = new ArrayList<>();
    //Add the modules here for them to appear in the ClickGui
    public void init() {
    modules.add(new ClickGui());
    modules.add(new FullBright());
    modules.add(new AutoSprint());
    modules.add(new Zoom());
    modules.add(new NoHurtCam());
    modules.add(new Keystrokes());
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
