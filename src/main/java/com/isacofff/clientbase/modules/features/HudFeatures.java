package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

/**
 * Small, real HUD readouts. GuiIngame owns their rendering so all values are
 * read from the active 1.12.2 client/world rather than simulated placeholders.
 */
public final class HudFeatures {
    private HudFeatures() {}

    public static class DirectionHUD extends Module { public DirectionHUD(){super("DirectionHUD",Category.Render);description="Shows the direction you are facing.";}}
    public static class PotionHUD extends Module { public PotionHUD(){super("PotionHUD",Category.Render);description="Lists active potion effects and remaining time.";}}
    public static class ItemCounter extends Module { public ItemCounter(){super("ItemCounter",Category.Render);description="Shows the count of the selected hotbar stack.";}}
    public static class ClockHUD extends Module { public ClockHUD(){super("ClockHUD",Category.Render);description="Shows your local clock.";}}
    public static class BiomeHUD extends Module { public BiomeHUD(){super("BiomeHUD",Category.Render);description="Shows the biome at your current position.";}}
    public static class SpeedHUD extends Module { public SpeedHUD(){super("SpeedHUD",Category.Render);description="Shows horizontal movement speed.";}}
    public static class HeldDurability extends Module { public HeldDurability(){super("HeldDurability",Category.Render);description="Shows remaining durability of the held item.";}}
    public static class TotemCounter extends Module { public TotemCounter(){super("TotemCounter",Category.Render);description="Counts totems in your inventory.";}}
    public static class ArrowCounter extends Module { public ArrowCounter(){super("ArrowCounter",Category.Render);description="Counts arrows in your inventory.";}}
    public static class DayCounter extends Module { public DayCounter(){super("DayCounter",Category.Render);description="Shows the current world day.";}}
    public static class PingHUD extends Module { public PingHUD(){super("PingHUD",Category.Render);description="Shows your connection ping when available.";}}
    public static class FoodHUD extends Module { public FoodHUD(){super("FoodHUD",Category.Render);description="Shows your hunger level.";}}
    public static class XPDisplay extends Module { public XPDisplay(){super("XPDisplay",Category.Render);description="Shows your experience level and progress.";}}
    public static class HealthHUD extends Module { public HealthHUD(){super("HealthHUD",Category.Render);description="Shows current and maximum health.";}}
    public static class WorldTime extends Module { public WorldTime(){super("WorldTime",Category.Render);description="Shows the in-game time.";}}
    public static class RotationHUD extends Module { public RotationHUD(){super("RotationHUD",Category.Render);description="Shows yaw and pitch.";}}
    public static class ChunkHUD extends Module { public ChunkHUD(){super("ChunkHUD",Category.Render);description="Shows your current chunk coordinates.";}}
    public static class LightLevel extends Module { public LightLevel(){super("LightLevel",Category.Render);description="Shows the block light level at your position.";}}
    public static class InventoryCount extends Module { public InventoryCount(){super("InventoryCount",Category.Render);description="Counts occupied inventory slots.";}}
    public static class ArmorValue extends Module { public ArmorValue(){super("ArmorValue",Category.Render);description="Shows your total armor points.";}}
    public static class TargetHUD extends Module { public TargetHUD(){super("TargetHUD",Category.Render);description="Shows the name of the entity under your crosshair.";}}
    public static class EntityCount extends Module { public EntityCount(){super("EntityCount",Category.Render);description="Shows the number of loaded entities.";}}
    public static class DimensionHUD extends Module { public DimensionHUD(){super("DimensionHUD",Category.Render);description="Shows the current dimension ID.";}}
    public static class PlayerNameHUD extends Module { public PlayerNameHUD(){super("PlayerNameHUD",Category.Render);description="Shows your in-game player name.";}}
    public static class SaturationHUD extends Module { public SaturationHUD(){super("SaturationHUD",Category.Render);description="Shows your current food saturation.";}}
    public static class AirHUD extends Module { public AirHUD(){super("AirHUD",Category.Render);description="Shows your remaining air supply.";}}
    public static class ArmorCount extends Module { public ArmorCount(){super("ArmorCount",Category.Render);description="Counts equipped armor pieces.";}}
    public static class HeldItemHUD extends Module { public HeldItemHUD(){super("HeldItemHUD",Category.Render);description="Shows the selected item name.";}}
    public static class SelectedSlotHUD extends Module { public SelectedSlotHUD(){super("SelectedSlotHUD",Category.Render);description="Shows the selected hotbar slot.";}}
    public static class WorldNameHUD extends Module { public WorldNameHUD(){super("WorldNameHUD",Category.Render);description="Shows the current world name.";}}
    public static class LocalDifficultyHUD extends Module { public LocalDifficultyHUD(){super("LocalDifficultyHUD",Category.Render);description="Shows the local difficulty value.";}}
    
    public static class HideScoreboard extends Module { public HideScoreboard(){super("HideScoreboard",Category.Render);description="Hides the vanilla scoreboard.";}}
    public static class HideBossBar extends Module { public HideBossBar(){super("HideBossBar",Category.Render);description="Hides the vanilla boss bar.";}}
    public static class HidePotionEffects extends Module { public HidePotionEffects(){super("HidePotionEffects",Category.Render);description="Hides the vanilla potion effect list.";}}
    public static class HideHotbar extends Module { public HideHotbar(){super("HideHotbar",Category.Render);description="Hides the vanilla hotbar.";}}
    public static class HideCrosshair extends Module { public HideCrosshair(){super("HideCrosshair",Category.Render);description="Hides the vanilla crosshair and attack indicator.";}}
    public static class HidePlayerList extends Module { public HidePlayerList(){super("HidePlayerList",Category.Render);description="Hides the player list overlay.";}}
    public static class HideTitles extends Module { public HideTitles(){super("HideTitles",Category.Render);description="Hides titles and subtitles.";}}
    public static class HideSubtitles extends Module { public HideSubtitles(){super("HideSubtitles",Category.Render);description="Hides sound subtitles.";}}
    public static class HidePumpkin extends Module { public HidePumpkin(){super("HidePumpkin",Category.Render);description="Hides the pumpkin overlay.";}}
    public static class HidePortal extends Module { public HidePortal(){super("HidePortal",Category.Render);description="Hides the portal overlay.";}}
    public static class HideVignette extends Module { public HideVignette(){super("HideVignette",Category.Render);description="Hides the screen vignette.";}}
}
