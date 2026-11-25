package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

/**
 * KillAura-style target practice module.
 * It is disabled by default and will only run in an integrated singleplayer
 * world after the user explicitly enables Private Test Mode.
 */
public class KillAura extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public final Setting.NumberSetting range =
            new Setting.NumberSetting("Range", 4.2D, 1.0D, 6.0D, 0.1D);
    public final Setting.BooleanSetting onlyPlayers =
            new Setting.BooleanSetting("Only Players", true);
    public final Setting.BooleanSetting useCooldown =
            new Setting.BooleanSetting("Use Cooldown", true);
    public final Setting.BooleanSetting privateTestMode =
            new Setting.BooleanSetting("Private Test Mode", false);

    public KillAura() {
        super("KillAura", Category.Combat);
        description = "Automatic melee target practice for integrated singleplayer worlds only.";
        settings.add(range);
        settings.add(onlyPlayers);
        settings.add(useCooldown);
        settings.add(privateTestMode);
    }

    @Override
    public void onUpdate() {
        if (!privateTestMode.getValue() || !mc.isSingleplayer()
                || mc.player == null || mc.world == null || mc.currentScreen != null) {
            return;
        }

        if (useCooldown.getValue()
                && mc.player.getCooledAttackStrength(0.0F) < 1.0F) {
            return;
        }

        EntityLivingBase target = getClosestTarget();
        if (target != null) {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    private EntityLivingBase getClosestTarget() {
        EntityLivingBase closest = null;
        double closestDistanceSq = range.getValue() * range.getValue();

        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityLivingBase)) continue;

            EntityLivingBase living = (EntityLivingBase) entity;
            if (living == mc.player || living.isDead || living.getHealth() <= 0.0F) continue;
            if (onlyPlayers.getValue() && !(living instanceof EntityPlayer)) continue;

            double distanceSq = mc.player.getDistanceSqToEntity(living);
            if (distanceSq <= closestDistanceSq) {
                closestDistanceSq = distanceSq;
                closest = living;
            }
        }
        return closest;
    }
}
