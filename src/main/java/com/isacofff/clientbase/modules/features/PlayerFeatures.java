package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

/**
 * Inventory-focused player utilities. These modules only inspect local client
 * state; they never move items or send gameplay packets.
 */
public final class PlayerFeatures {
    private PlayerFeatures() {}

    public static class ArmorCompare extends Module {
        public final Setting.ModeSetting format =
                new Setting.ModeSetting("Display", "Upgrades Only", "Upgrades Only", "All Slots");
        public final Setting.BooleanSetting showDurability =
                new Setting.BooleanSetting("Show Durability", true);

        private static final EntityEquipmentSlot[] SLOTS = new EntityEquipmentSlot[] {
                EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST,
                EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET
        };
        private static final String[] LABELS = new String[] {
                "Helmet", "Chestplate", "Leggings", "Boots"
        };

        public ArmorCompare() {
            super("ArmorCompare", Category.Player);
            description = "Compares equipped armor with the best matching pieces in your inventory. Never equips items.";
            settings.add(format);
            settings.add(showDurability);
        }

        public java.util.List<String> getLines(EntityPlayer player) {
            java.util.ArrayList<String> lines = new java.util.ArrayList<String>();
            boolean allSlots = "All Slots".equals(format.getValue());
            for (int slotIndex = 0; slotIndex < SLOTS.length; ++slotIndex) {
                EntityEquipmentSlot slot = SLOTS[slotIndex];
                ItemStack equipped = player.getItemStackFromSlot(slot);
                ItemStack best = ItemStack.field_190927_a;
                for (int i = 0; i < player.inventory.mainInventory.size(); ++i) {
                    ItemStack candidate = player.inventory.mainInventory.get(i);
                    if (candidate.func_190926_b() || !(candidate.getItem() instanceof ItemArmor)) continue;
                    ItemArmor armor = (ItemArmor) candidate.getItem();
                    if (armor.getEquipmentSlot() != slot) continue;
                    if (best.func_190926_b() || isBetter(candidate, best)) best = candidate;
                }

                boolean upgrade = !best.func_190926_b()
                        && (equipped.func_190926_b() || isBetter(best, equipped));
                if (upgrade) {
                    ItemArmor candidateArmor = (ItemArmor) best.getItem();
                    int currentProtection = equipped.func_190926_b()
                            ? 0 : ((ItemArmor) equipped.getItem()).damageReduceAmount;
                    int bonus = candidateArmor.damageReduceAmount - currentProtection;
                    String line = "Upgrade " + LABELS[slotIndex] + ": " + best.getDisplayName()
                            + " (+" + bonus + " armor)";
                    if (showDurability.getValue()) {
                        line += " [" + remainingPercent(best) + "%]";
                    }
                    lines.add(line);
                } else if (allSlots) {
                    String currentName = equipped.func_190926_b() ? "Empty" : equipped.getDisplayName();
                    String spareName = best.func_190926_b() ? "none" : best.getDisplayName();
                    String line = LABELS[slotIndex] + ": " + currentName + " | Spare: " + spareName;
                    if (showDurability.getValue() && !best.func_190926_b()) {
                        line += " [" + remainingPercent(best) + "%]";
                    }
                    lines.add(line);
                }
            }
            if (lines.isEmpty() && !allSlots) lines.add("Armor Compare: no upgrades found");
            return lines;
        }

        private static boolean isBetter(ItemStack candidate, ItemStack current) {
            if (current.func_190926_b()) return !candidate.func_190926_b();
            if (candidate.func_190926_b()) return false;
            if (!(candidate.getItem() instanceof ItemArmor) || !(current.getItem() instanceof ItemArmor)) return false;
            ItemArmor candidateArmor = (ItemArmor) candidate.getItem();
            ItemArmor currentArmor = (ItemArmor) current.getItem();
            if (candidateArmor.damageReduceAmount != currentArmor.damageReduceAmount) {
                return candidateArmor.damageReduceAmount > currentArmor.damageReduceAmount;
            }
            if (candidateArmor.toughness != currentArmor.toughness) {
                return candidateArmor.toughness > currentArmor.toughness;
            }
            return remainingPercent(candidate) > remainingPercent(current);
        }

        private static int remainingPercent(ItemStack stack) {
            int maximum = stack.getMaxDamage();
            if (maximum <= 0) return 100;
            return Math.max(0, Math.min(100, (maximum - stack.getItemDamage()) * 100 / maximum));
        }
    }

    public static class InventoryAlerts extends Module {
        public final Setting.NumberSetting threshold =
                new Setting.NumberSetting("Durability Threshold", 20.0D, 5.0D, 50.0D, 5.0D);
        public final Setting.BooleanSetting checkArmor =
                new Setting.BooleanSetting("Check Armor", true);
        public final Setting.BooleanSetting checkInventory =
                new Setting.BooleanSetting("Check Inventory", true);

        private String lastAlertKey = "";

        public InventoryAlerts() {
            super("InventoryAlerts", Category.Player);
            description = "Sends one local chat warning when a damageable item falls below the selected durability percentage.";
            settings.add(threshold);
            settings.add(checkArmor);
            settings.add(checkInventory);
        }

        @Override
        public void onEnable() {
            lastAlertKey = "";
        }

        @Override
        public void onUpdate() {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player == null || mc.world == null) return;

            AlertCandidate lowItem = findLowDurabilityItem(mc.player);
            if (lowItem == null) {
                lastAlertKey = "";
                return;
            }
            if (!lowItem.key.equals(lastAlertKey)) {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(
                        "§e[KYZXX] §fLow durability: " + lowItem.message));
                lastAlertKey = lowItem.key;
            }
        }

        private AlertCandidate findLowDurabilityItem(EntityPlayer player) {
            int limit = (int) Math.round(threshold.getValue());
            if (checkArmor.getValue()) {
                for (int i = 0; i < player.inventory.armorInventory.size(); ++i) {
                    ItemStack stack = player.inventory.armorInventory.get(i);
                    AlertCandidate result = describeIfLow(stack, "armor slot " + (i + 1), limit);
                    if (result != null) return result;
                }
            }
            if (checkInventory.getValue()) {
                for (int i = 0; i < player.inventory.mainInventory.size(); ++i) {
                    ItemStack stack = player.inventory.mainInventory.get(i);
                    AlertCandidate result = describeIfLow(stack, "inventory slot " + (i + 1), limit);
                    if (result != null) return result;
                }
                for (int i = 0; i < player.inventory.offHandInventory.size(); ++i) {
                    ItemStack stack = player.inventory.offHandInventory.get(i);
                    AlertCandidate result = describeIfLow(stack, "offhand", limit);
                    if (result != null) return result;
                }
            }
            return null;
        }

        private AlertCandidate describeIfLow(ItemStack stack, String location, int limit) {
            if (stack == null || stack.func_190926_b() || !stack.isItemStackDamageable()) return null;
            int maximum = stack.getMaxDamage();
            if (maximum <= 0) return null;
            int remaining = Math.max(0, (maximum - stack.getItemDamage()) * 100 / maximum);
            if (remaining > limit) return null;
            String name = stack.getDisplayName();
            return new AlertCandidate(location + ":" + name, name + " (" + remaining + "%, " + location + ")");
        }

        private static class AlertCandidate {
            private final String key;
            private final String message;

            private AlertCandidate(String key, String message) {
                this.key = key;
                this.message = message;
            }
        }
    }
}
