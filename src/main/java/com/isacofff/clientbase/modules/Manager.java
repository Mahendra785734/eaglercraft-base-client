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
        registerCatalogModules();
        // Defaults for the two most-used toggles.
        Module clickGui = getModuleByName("ClickGUI");
        if (clickGui != null) clickGui.setKeybind(54); // Right Shift
        Module fullBright = getModuleByName("FullBright");
        if (fullBright != null) fullBright.setKeybind(net.lax1dude.eaglercraft.KeyboardConstants.KEY_F);
        Module sprint = getModuleByName("Sprint");
        if (sprint != null) sprint.setKeybind(net.lax1dude.eaglercraft.KeyboardConstants.KEY_R);
    }

    private void registerCatalogModules() {

        // Combat
        register(new CatalogModule("Killaura", "Combat targeting module shell; automated attack behavior is not implemented in this catalog entry.", Category.Combat));
        register(new CatalogModule("Triggerbot", "Crosshair target and attack-delay module shell; automatic attacks are not wired.", Category.Combat));
        register(new CatalogModule("AimAssist", "Aim assistance module shell; game aiming hook is pending.", Category.Combat));
        register(new CatalogModule("AutoClicker", "Click timing module shell; automated input is not wired.", Category.Combat));
        register(new CatalogModule("Velocity", "Velocity diagnostics module; does not modify movement or network traffic.", Category.Combat));
        register(new CatalogModule("Criticals", "Critical-hit status diagnostics; no packet manipulation.", Category.Combat));
        register(new CatalogModule("Reach", "Interaction distance display; does not extend reach.", Category.Combat));
        register(new CatalogModule("Hitboxes", "Entity hitbox visualization module shell.", Category.Combat));
        register(new CatalogModule("AutoArmor", "Armor evaluation and equipment recommendation module shell.", Category.Combat));
        register(new CatalogModule("AutoPot", "Potion monitoring and reminder module shell.", Category.Combat));
        register(new CatalogModule("AutoSoup", "Soup monitoring and reminder module shell.", Category.Combat));
        register(new CatalogModule("WTap", "Sprint-reset practice indicator.", Category.Combat));
        register(new CatalogModule("STap", "Sprint timing practice indicator.", Category.Combat));
        register(new CatalogModule("KeepSprint", "Sprint-state display module.", Category.Combat));
        register(new CatalogModule("TargetStrafe", "Target-relative movement visualization.", Category.Combat));

        // Movement
        register(new CatalogModule("Fly", "Movement diagnostics only; no server-rule bypass.", Category.Movement));
        register(new CatalogModule("Speed", "Movement speed display and diagnostics.", Category.Movement));
        register(new CatalogModule("LongJump", "Jump distance and height tracking.", Category.Movement));
        register(new CatalogModule("Step", "Block-height diagnostics.", Category.Movement));
        register(new CatalogModule("Scaffold", "Block placement preview and counter.", Category.Movement));
        register(new CatalogModule("Tower", "Vertical placement statistics.", Category.Movement));
        register(new CatalogModule("Spider", "Climb-contact visualization.", Category.Movement));
        register(new CatalogModule("NoFall", "Fall-distance warning; does not cancel fall damage.", Category.Movement));
        register(new CatalogModule("Jesus", "Liquid-contact visualization.", Category.Movement));
        register(new CatalogModule("Sprint", "Sprint status and toggle module shell.", Category.Movement));
        register(new CatalogModule("InventoryMove", "Inventory movement input integration pending.", Category.Movement));
        register(new CatalogModule("Phase", "Collision-boundary diagnostics; no collision bypass.", Category.Movement));
        register(new CatalogModule("Clip", "Position and collision diagnostics.", Category.Movement));
        register(new CatalogModule("HighJump", "Jump-height statistics.", Category.Movement));
        register(new CatalogModule("Glide", "Falling-speed diagnostics.", Category.Movement));

        // Player
        register(new CatalogModule("ChestStealer", "Chest inspection and sorting shell; automatic looting is not wired.", Category.Player));
        register(new CatalogModule("InventoryManager", "Inventory organization shell; automatic item movement is not wired.", Category.Player));
        register(new CatalogModule("AutoTool", "Best-tool recommendation shell.", Category.Player));
        register(new CatalogModule("AutoEat", "Hunger monitor and food reminders.", Category.Player));
        register(new CatalogModule("FastPlace", "Placement timing statistics.", Category.Player));
        register(new CatalogModule("FastBreak", "Block breaking progress display.", Category.Player));
        register(new CatalogModule("NoRotate", "Rotation change diagnostics; does not override server corrections.", Category.Player));
        register(new CatalogModule("AntiAFK", "Idle timer and activity reminder; no simulated input.", Category.Player));
        register(new CatalogModule("Derp", "Local character rotation preview.", Category.Player));
        register(new CatalogModule("Spammer", "Message template manager; automatic chat sending is disabled.", Category.Player));
        register(new CatalogModule("NameProtect", "Local HUD name alias display.", Category.Player));
        register(new CatalogModule("Freecam", "Camera preview module shell.", Category.Player));
        register(new CatalogModule("Blink", "Connection event diagnostics; does not suppress packets.", Category.Player));
        register(new CatalogModule("Timer", "Session and local timer.", Category.Player));
        register(new CatalogModule("NoSlow", "Movement slowdown diagnostics; no movement modification.", Category.Player));

        // Render
        register(new CatalogModule("ESP", "Entity outline renderer integration pending.", Category.Render));
        register(new CatalogModule("Tracers", "Entity direction-line renderer integration pending.", Category.Render));
        register(new CatalogModule("Nametags", "Custom entity name display shell.", Category.Render));
        register(new CatalogModule("ChinaHat", "Decorative player model hat renderer shell.", Category.Render));
        register(new CatalogModule("XRay", "Block visibility preview renderer shell.", Category.Render));
        register(new CatalogModule("StorageESP", "Storage block highlight renderer shell.", Category.Render));
        register(new CatalogModule("ArrayList", "Enabled module list overlay shell.", Category.Render));
        register(new CatalogModule("Watermark", "Client name and version overlay shell.", Category.Render));
        register(new CatalogModule("Notifications", "Client notification display shell.", Category.Render));
        register(new CatalogModule("ViewClip", "Third-person camera clipping diagnostics.", Category.Render));
        register(new CatalogModule("CameraClip", "Third-person camera collision visualization.", Category.Render));
        register(new CatalogModule("ItemESP", "Dropped item visualization shell.", Category.Render));

        // World
        register(new CatalogModule("Nuker", "Block selection and mining preview only; no mass breaking.", Category.World));
        register(new CatalogModule("Scaffold", "Block placement preview and counter.", Category.World));
        register(new CatalogModule("BedFucker", "Bed detection and highlight.", Category.World));
        register(new CatalogModule("AutoMine", "Mining progress and block information.", Category.World));
        register(new CatalogModule("AntiBot", "Entity validation diagnostics.", Category.World));
        register(new CatalogModule("Teams", "Scoreboard team information display.", Category.World));
        register(new CatalogModule("MiddleClickFriend", "Friend management shortcut.", Category.World));
        register(new CatalogModule("ClickTP", "Destination preview only; no teleport packet.", Category.World));
        register(new CatalogModule("Waypoints", "Saved location manager.", Category.World));
        register(new CatalogModule("Radar", "Nearby entity radar display.", Category.World));
        register(new CatalogModule("AutoGG", "Post-game message reminder; no automatic chat sending.", Category.World));
        register(new CatalogModule("AutoHypixel", "Game and server information display.", Category.World));
        register(new CatalogModule("Disabler", "Protocol compatibility diagnostics only; no protection bypass.", Category.World));
        register(new CatalogModule("PingSpoof", "Simulated latency HUD only; no network traffic alteration.", Category.World));
        register(new CatalogModule("AntiVoid", "Void proximity warning.", Category.World));

        // Misc
        register(new CatalogModule("FakeLag", "Local simulated latency indicator only; no actual network delay.", Category.Misc));
        register(new CatalogModule("LagRange", "Latency and distance comparison display.", Category.Misc));
        register(new CatalogModule("Backtrack", "Entity position history visualization; no hit-registration manipulation.", Category.Misc));
        register(new CatalogModule("PacketMine", "Mining event diagnostics; no packet modification.", Category.Misc));
        register(new CatalogModule("AutoBlock", "Blocking-state display; automatic blocking is not wired.", Category.Misc));
        register(new CatalogModule("NoWeb", "Web contact diagnostics; no movement bypass.", Category.Misc));
        register(new CatalogModule("AntiFireball", "Fireball detection and direction indicator.", Category.Misc));
        register(new CatalogModule("StaffDetector", "Staff-name alerts using only locally available information.", Category.Misc));
        register(new CatalogModule("AutoReport", "Report preparation interface; submission remains user-confirmed.", Category.Misc));
        register(new CatalogModule("ConfigSystem", "Client configuration profile manager shell.", Category.Misc));
        register(new CatalogModule("FriendsManager", "Local friend list manager.", Category.Misc));
        register(new CatalogModule("Macro", "Local shortcut manager; macro execution is not enabled.", Category.Misc));
        register(new CatalogModule("PluginList", "Client extension and compatibility list.", Category.Misc));
        register(new CatalogModule("ServerCrasher", "Local rendering stress-test placeholder only; sends no traffic and cannot disrupt servers.", Category.Misc));
        register(new CatalogModule("SelfDestruct", "Safe reset placeholder; destructive file deletion is not implemented.", Category.Misc));
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
