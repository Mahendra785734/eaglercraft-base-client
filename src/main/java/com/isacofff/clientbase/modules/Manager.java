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

        // Client - client-side quality-of-life options
        register(new CatalogModule("Interface Theme", "Choose the client interface appearance.", Category.Client));
        register(new CatalogModule("Menu Background", "Choose a main menu background style.", Category.Client));
        register(new CatalogModule("Menu Music", "Control the main menu soundtrack.", Category.Client));
        register(new CatalogModule("Menu Particles", "Show subtle menu background particles.", Category.Client));
        register(new CatalogModule("Compact Click GUI", "Use a compact module browser layout.", Category.Client));
        register(new CatalogModule("GUI Animations", "Enable smooth interface transitions.", Category.Client));
        register(new CatalogModule("GUI Sounds", "Play interface interaction sounds.", Category.Client));
        register(new CatalogModule("Quick Settings", "Open frequently used game settings.", Category.Client));
        register(new CatalogModule("Session Summary", "Display a summary of the current play session.", Category.Client));
        register(new CatalogModule("Playtime Display", "Show time spent in the current session.", Category.Client));
        register(new CatalogModule("Server Bookmark", "Keep a local list of favorite servers.", Category.Client));
        register(new CatalogModule("World Bookmark", "Keep a local list of favorite worlds.", Category.Client));
        register(new CatalogModule("Profile Switcher", "Switch between saved client profiles.", Category.Client));
        register(new CatalogModule("Config Backup", "Create a local client configuration backup.", Category.Client));
        register(new CatalogModule("Config Restore", "Restore a saved client configuration.", Category.Client));
        register(new CatalogModule("Keybind Manager", "View and edit client keybinds.", Category.Client));
        register(new CatalogModule("Search History", "Remember recent module searches locally.", Category.Client));
        register(new CatalogModule("Notification Center", "Show client-side notifications.", Category.Client));
        register(new CatalogModule("Toast Notifications", "Show brief status messages.", Category.Client));
        register(new CatalogModule("Welcome Screen", "Show a client welcome panel.", Category.Client));
        register(new CatalogModule("Quick Restart", "Return to the title screen quickly.", Category.Client));
        register(new CatalogModule("Screenshot Folder", "Show screenshot location information.", Category.Client));
        register(new CatalogModule("Resource Pack Reminder", "Show resource pack status reminders.", Category.Client));
        register(new CatalogModule("Accessibility Options", "Open visual accessibility preferences.", Category.Client));

        // Render - client-side quality-of-life options
        register(new CatalogModule("Clean HUD", "Hide selected vanilla HUD elements.", Category.Render));
        register(new CatalogModule("Minimal HUD", "Use a reduced HUD layout.", Category.Render));
        register(new CatalogModule("HUD Editor", "Arrange supported HUD elements.", Category.Render));
        register(new CatalogModule("HUD Grid", "Show alignment guides in the HUD editor.", Category.Render));
        register(new CatalogModule("HUD Snap", "Snap HUD elements to alignment guides.", Category.Render));
        register(new CatalogModule("HUD Backgrounds", "Add backgrounds to supported HUD panels.", Category.Render));
        register(new CatalogModule("HUD Borders", "Add borders to supported HUD panels.", Category.Render));
        register(new CatalogModule("HUD Shadow", "Use shadows on supported HUD text.", Category.Render));
        register(new CatalogModule("HUD Scale", "Adjust the scale of supported HUD elements.", Category.Render));
        register(new CatalogModule("HUD Opacity", "Adjust supported HUD panel opacity.", Category.Render));
        register(new CatalogModule("Clock Display", "Display the local time.", Category.Render));
        register(new CatalogModule("World Clock", "Display the in-game time.", Category.Render));
        register(new CatalogModule("World Day", "Display the current world day.", Category.Render));
        register(new CatalogModule("Coordinates Display", "Display current coordinates.", Category.Render));
        register(new CatalogModule("Direction Display", "Display the facing direction.", Category.Render));
        register(new CatalogModule("Biome Display", "Display the current biome.", Category.Render));
        register(new CatalogModule("Chunk Display", "Display current chunk coordinates.", Category.Render));
        register(new CatalogModule("Light Display", "Display the local block light level.", Category.Render));
        register(new CatalogModule("Speed Display", "Display horizontal movement speed.", Category.Render));
        register(new CatalogModule("Ping Display", "Display connection latency when available.", Category.Render));
        register(new CatalogModule("FPS Display", "Display current frame rate.", Category.Render));
        register(new CatalogModule("CPS Display", "Display mouse click rate.", Category.Render));
        register(new CatalogModule("Keystrokes Display", "Display pressed movement keys.", Category.Render));
        register(new CatalogModule("Armor Display", "Display equipped armor durability.", Category.Render));
        register(new CatalogModule("Held Item Display", "Display the selected item.", Category.Render));
        register(new CatalogModule("Item Count Display", "Display the selected stack count.", Category.Render));
        register(new CatalogModule("Inventory Display", "Display occupied inventory slots.", Category.Render));
        register(new CatalogModule("Potion Display", "Display active potion effects.", Category.Render));
        register(new CatalogModule("Health Display", "Display current health.", Category.Render));
        register(new CatalogModule("Food Display", "Display hunger and saturation.", Category.Render));
        register(new CatalogModule("Experience Display", "Display experience level and progress.", Category.Render));
        register(new CatalogModule("Air Display", "Display remaining air underwater.", Category.Render));
        register(new CatalogModule("Arrow Display", "Display the arrow count.", Category.Render));
        register(new CatalogModule("Totem Display", "Display the totem count.", Category.Render));
        register(new CatalogModule("Entity Count Display", "Display loaded entity count.", Category.Render));
        register(new CatalogModule("Target Information", "Display information about the entity under the crosshair.", Category.Render));
        register(new CatalogModule("Rotation Display", "Display current yaw and pitch.", Category.Render));
        register(new CatalogModule("Dimension Display", "Display the current dimension.", Category.Render));
        register(new CatalogModule("World Name Display", "Display the current world name.", Category.Render));
        register(new CatalogModule("Player Name Display", "Display the local player name.", Category.Render));
        register(new CatalogModule("Armor Points Display", "Display total armor points.", Category.Render));
        register(new CatalogModule("Selected Slot Display", "Display the selected hotbar slot.", Category.Render));
        register(new CatalogModule("Durability Display", "Display held item durability.", Category.Render));
        register(new CatalogModule("Module List Display", "Display enabled client modules.", Category.Render));
        register(new CatalogModule("Watermark Display", "Display a small KYZXX watermark.", Category.Render));
        register(new CatalogModule("Direction Compass", "Display a compact compass.", Category.Render));
        register(new CatalogModule("Status Panel", "Display current client status.", Category.Render));
        register(new CatalogModule("Performance Panel", "Display frame and memory information when available.", Category.Render));
        register(new CatalogModule("Crosshair Style", "Choose a custom crosshair appearance.", Category.Render));
        register(new CatalogModule("Crosshair Color", "Choose a crosshair color.", Category.Render));
        register(new CatalogModule("Damage Tint", "Adjust the local damage screen tint.", Category.Render));
        register(new CatalogModule("Vignette Control", "Adjust the screen vignette.", Category.Render));
        register(new CatalogModule("Weather Particles", "Adjust local weather particle visibility.", Category.Render));
        register(new CatalogModule("Cloud Style", "Choose a cloud rendering preference.", Category.Render));
        register(new CatalogModule("Time of Day Preview", "Preview a preferred visual time setting.", Category.Render));
        register(new CatalogModule("Screenshot Overlay", "Show a clean screenshot mode indicator.", Category.Render));
        register(new CatalogModule("Hide Scoreboard", "Hide the vanilla scoreboard.", Category.Render));
        register(new CatalogModule("Hide Boss Bar", "Hide the vanilla boss bar.", Category.Render));
        register(new CatalogModule("Hide Potion Icons", "Hide vanilla potion icons.", Category.Render));
        register(new CatalogModule("Hide Hotbar", "Hide the vanilla hotbar.", Category.Render));
        register(new CatalogModule("Hide Crosshair", "Hide the vanilla crosshair.", Category.Render));
        register(new CatalogModule("Hide Player List", "Hide the vanilla player list.", Category.Render));
        register(new CatalogModule("Hide Titles", "Hide title text.", Category.Render));
        register(new CatalogModule("Hide Subtitles", "Hide sound subtitles.", Category.Render));
        register(new CatalogModule("Hide Pumpkin Overlay", "Hide the pumpkin overlay.", Category.Render));
        register(new CatalogModule("Hide Portal Overlay", "Hide the portal overlay.", Category.Render));
        register(new CatalogModule("Hide Vignette", "Hide the screen vignette.", Category.Render));

        // World - client-side quality-of-life options
        register(new CatalogModule("Waypoint Manager", "Save and manage personal waypoints.", Category.World));
        register(new CatalogModule("Waypoint Beacons", "Show visual markers for saved waypoints.", Category.World));
        register(new CatalogModule("Waypoint Distance", "Display distance to the selected waypoint.", Category.World));
        register(new CatalogModule("World Information", "Display world and dimension information.", Category.World));
        register(new CatalogModule("Block Information", "Inspect the block currently under the crosshair.", Category.World));
        register(new CatalogModule("Fluid Information", "Display information about nearby fluids.", Category.World));
        register(new CatalogModule("Biome Information", "Display information about the current biome.", Category.World));
        register(new CatalogModule("Time Information", "Display world time and day information.", Category.World));
        register(new CatalogModule("Weather Information", "Display current world weather.", Category.World));
        register(new CatalogModule("Light Level Warning", "Warn when the local light level is low.", Category.World));
        register(new CatalogModule("Spawn Point Marker", "Mark the current world spawn point when known.", Category.World));
        register(new CatalogModule("Death Location Marker", "Remember the most recent local death location.", Category.World));
        register(new CatalogModule("Map Notes", "Save text notes for the current world.", Category.World));
        register(new CatalogModule("World Notes", "Keep a local notebook for each world.", Category.World));
        register(new CatalogModule("Coordinate Clipboard", "Copy current coordinates with a keybind.", Category.World));
        register(new CatalogModule("Screenshot Map Marker", "Save coordinates with a screenshot note.", Category.World));
        register(new CatalogModule("Chunk Borders", "Display chunk boundary guides.", Category.World));
        register(new CatalogModule("Grid Overlay", "Display a configurable world grid.", Category.World));
        register(new CatalogModule("Structure Notes", "Save notes about discovered structures.", Category.World));
        register(new CatalogModule("Exploration Tracker", "Track explored distance locally.", Category.World));
        register(new CatalogModule("Biome Tracker", "Track visited biomes locally.", Category.World));
        register(new CatalogModule("World Session Timer", "Track time spent in the current world.", Category.World));
        register(new CatalogModule("Weather Reminder", "Show a reminder when weather changes.", Category.World));
        register(new CatalogModule("Local Sound Guide", "Show the direction of nearby sounds when supported.", Category.World));

        // Player - client-side quality-of-life options
        register(new CatalogModule("Cosmetics Menu", "Open the skin and cape editor.", Category.Player));
        register(new CatalogModule("Cape Preview", "Preview the selected cape.", Category.Player));
        register(new CatalogModule("Skin Preview", "Preview the active player skin.", Category.Player));
        register(new CatalogModule("Emote Wheel", "Open a local emote selection interface.", Category.Player));
        register(new CatalogModule("Player Profile", "Show the local player profile.", Category.Player));
        register(new CatalogModule("Friend List", "Manage a local friends list.", Category.Player));
        register(new CatalogModule("Friend Notes", "Save private notes for local friend entries.", Category.Player));
        register(new CatalogModule("Chat Timestamps", "Show timestamps in chat.", Category.Player));
        register(new CatalogModule("Chat Background", "Add a background behind chat lines.", Category.Player));
        register(new CatalogModule("Chat Font Scale", "Adjust chat text scale.", Category.Player));
        register(new CatalogModule("Chat History", "Browse recent chat messages.", Category.Player));
        register(new CatalogModule("Chat Search", "Search visible chat history.", Category.Player));
        register(new CatalogModule("Chat Copy", "Copy selected chat text.", Category.Player));
        register(new CatalogModule("Chat Notifications", "Show local chat notification indicators.", Category.Player));
        register(new CatalogModule("Item Favorites", "Mark frequently used items in a local list.", Category.Player));
        register(new CatalogModule("Inventory Notes", "Save local inventory organization notes.", Category.Player));
        register(new CatalogModule("Armor Reminder", "Show armor durability reminders.", Category.Player));
        register(new CatalogModule("Food Reminder", "Show hunger reminders.", Category.Player));
        register(new CatalogModule("Tool Durability Reminder", "Warn when the held tool is nearly worn out.", Category.Player));
        register(new CatalogModule("Session Notes", "Save notes about the current play session.", Category.Player));

        // Misc - client-side quality-of-life options
        register(new CatalogModule("Soundboard", "Play built-in client interface sounds.", Category.Misc));
        register(new CatalogModule("Key Sounds", "Play sounds for keyboard input.", Category.Misc));
        register(new CatalogModule("Music Player", "Play the original KYZXX client melody.", Category.Misc));
        register(new CatalogModule("Music Volume", "Adjust client music volume.", Category.Misc));
        register(new CatalogModule("Sound Volume", "Adjust client interface sound volume.", Category.Misc));
        register(new CatalogModule("Sound Presets", "Choose a client sound preset.", Category.Misc));
        register(new CatalogModule("Focus Mode", "Reduce optional interface distractions.", Category.Misc));
        register(new CatalogModule("Low Motion Mode", "Reduce nonessential interface animation.", Category.Misc));
        register(new CatalogModule("Low Effects Mode", "Reduce optional visual effects.", Category.Misc));
        register(new CatalogModule("Performance Monitor", "Show client performance statistics.", Category.Misc));
        register(new CatalogModule("Memory Monitor", "Show memory information when available.", Category.Misc));
        register(new CatalogModule("Frame Pacing Display", "Display frame pacing information when available.", Category.Misc));
        register(new CatalogModule("Network Status", "Display connection status information.", Category.Misc));
        register(new CatalogModule("Connection History", "Show recent connection status changes.", Category.Misc));
        register(new CatalogModule("Server Details", "Display available server information.", Category.Misc));
        register(new CatalogModule("Server Version", "Display the server version when provided.", Category.Misc));
        register(new CatalogModule("Copy Server Address", "Copy the current server address.", Category.Misc));
        register(new CatalogModule("Quick Disconnect", "Provide a quick disconnect control.", Category.Misc));
        register(new CatalogModule("Reconnect Reminder", "Show a reminder after a connection closes.", Category.Misc));
        register(new CatalogModule("Language Shortcut", "Open language settings quickly.", Category.Misc));
        register(new CatalogModule("Accessibility Shortcut", "Open accessibility settings quickly.", Category.Misc));
        register(new CatalogModule("Help Center", "Open the client help panel.", Category.Misc));
        register(new CatalogModule("About KYZXX", "Show client version and credits.", Category.Misc));
        register(new CatalogModule("Privacy Options", "Open local privacy preferences.", Category.Misc));
        register(new CatalogModule("Reset Client Layout", "Restore the default client HUD layout.", Category.Misc));
        register(new CatalogModule("Reset Client Sounds", "Restore default client sound settings.", Category.Misc));
        register(new CatalogModule("Reset Interface", "Restore the default client interface.", Category.Misc));
        register(new CatalogModule("Debug Information", "Show non-sensitive local client diagnostics.", Category.Misc));
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
