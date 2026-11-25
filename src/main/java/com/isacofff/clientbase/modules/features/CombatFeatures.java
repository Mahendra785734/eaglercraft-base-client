package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

/**
 * Combat helpers for controlled local testing. TriggerBot is deliberately
 * disabled by default and requires both the explicit private-test setting
 * and an Eaglercraft singleplayer world before it can act.
 */
public final class CombatFeatures {
    private CombatFeatures() {}

    public static class TriggerBot extends Module {
        public final Setting.NumberSetting speed =
                new Setting.NumberSetting("Speed", 7.0D, 1.0D, 12.0D, 1.0D);
        public final Setting.NumberSetting range =
                new Setting.NumberSetting("Range", 4.75D, 1.0D, 6.0D, 0.25D);
        public final Setting.BooleanSetting useCooldown =
                new Setting.BooleanSetting("UseCooldown", true);
        public final Setting.BooleanSetting attackPlayers =
                new Setting.BooleanSetting("AttackPlayers", false);
        public final Setting.BooleanSetting attackMobs =
                new Setting.BooleanSetting("AttackMobs", true);
        public final Setting.BooleanSetting attackAnimals =
                new Setting.BooleanSetting("AttackAnimals", false);
        public final Setting.BooleanSetting attackInvisibleEntities =
                new Setting.BooleanSetting("AttackInvisibleEntities", false);
        public final Setting.BooleanSetting attackWhileMainhandInUse =
                new Setting.BooleanSetting("AttackWhileMainhandInUse", false);
        public final Setting.BooleanSetting privateTestMode =
                new Setting.BooleanSetting("Private Test Mode", false);

        private long lastAttackTime;

        public TriggerBot() {
            super("TriggerBot", Category.Combat);
            description = "Crosshair-triggered attacks for explicitly enabled singleplayer testing only.";
            settings.add(speed);
            settings.add(range);
            settings.add(useCooldown);
            settings.add(attackPlayers);
            settings.add(attackMobs);
            settings.add(attackAnimals);
            settings.add(attackInvisibleEntities);
            settings.add(attackWhileMainhandInUse);
            settings.add(privateTestMode);
        }

        @Override
        public void onEnable() {
            lastAttackTime = Minecraft.getSystemTime();
        }

        @Override
        public void onUpdate() {
            Minecraft mc = Minecraft.getMinecraft();
            if (!privateTestMode.getValue() || !mc.isSingleplayer()
                    || mc.player == null || mc.world == null || mc.currentScreen != null) {
                return;
            }
            if (!attackWhileMainhandInUse.getValue() && mc.player.isHandActive()) return;
            if (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != RayTraceResult.Type.ENTITY) return;

            Entity target = mc.objectMouseOver.entityHit;
            if (!(target instanceof EntityLivingBase) || target == mc.player || target.isDead) return;
            EntityLivingBase livingTarget = (EntityLivingBase) target;
            if (livingTarget.getHealth() <= 0.0F || mc.player.getDistance(target.posX, target.posY, target.posZ) > range.getValue()) return;
            if (target.isInvisible() && !attackInvisibleEntities.getValue()) return;

            if (target instanceof EntityPlayer) {
                if (!attackPlayers.getValue()) return;
            } else if (target instanceof IMob) {
                if (!attackMobs.getValue()) return;
            } else if (target instanceof EntityAnimal) {
                if (!attackAnimals.getValue()) return;
            } else {
                return;
            }

            long now = Minecraft.getSystemTime();
            if (useCooldown.getValue()) {
                if (mc.player.getCooledAttackStrength(0.0F) < 1.0F) return;
            } else {
                long interval = (long) (1000.0D / Math.max(1.0D, speed.getValue()));
                if (now - lastAttackTime < interval) return;
            }

            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            lastAttackTime = now;
        }
    }
}
