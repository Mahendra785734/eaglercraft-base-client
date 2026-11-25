package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.List;

/**
 * AutoCrystal for controlled local testing.
 *
 * The module deliberately requires the Private Test Mode setting and an
 * integrated singleplayer world. It uses the normal client controller rather
 * than raw packet injection and does not run on multiplayer servers.
 */
public class AutoCrystal extends Module {
    public final Setting.NumberSetting range =
            new Setting.NumberSetting("Target Range", 8.0D, 2.0D, 8.0D, 0.5D);
    public final Setting.NumberSetting breakRange =
            new Setting.NumberSetting("Break Range", 4.5D, 1.0D, 6.0D, 0.5D);
    public final Setting.NumberSetting delay =
            new Setting.NumberSetting("Action Delay", 2.0D, 1.0D, 10.0D, 1.0D);
    public final Setting.BooleanSetting privateTestMode =
            new Setting.BooleanSetting("Private Test Mode", false);

    private int tickDelay;

    public AutoCrystal() {
        super("AutoCrystal", Category.Combat);
        description = "Places and breaks End Crystals near a target in integrated singleplayer testing only.";
        settings.add(range);
        settings.add(breakRange);
        settings.add(delay);
        settings.add(privateTestMode);
    }

    @Override
    public void onEnable() {
        tickDelay = 0;
    }

    @Override
    public void onDisable() {
        tickDelay = 0;
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (!privateTestMode.getValue() || !mc.isSingleplayer()
                || mc.player == null || mc.world == null || mc.currentScreen != null) {
            return;
        }

        if (tickDelay > 0) {
            tickDelay--;
            return;
        }

        EntityPlayer target = findTarget(mc);
        if (target == null) return;

        EntityEnderCrystal crystal = findCrystalToBreak(mc, target);
        if (crystal != null) {
            mc.playerController.attackEntity(mc.player, crystal);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            tickDelay = (int) Math.round(delay.getValue());
            return;
        }

        int crystalSlot = findHotbarItem(mc, Items.END_CRYSTAL);
        if (crystalSlot == -1) return;

        BlockPos base = findBestCrystalPos(mc, target);
        if (base == null) return;

        int previousSlot = mc.player.inventory.currentItem;
        if (previousSlot != crystalSlot) {
            mc.player.inventory.currentItem = crystalSlot;
            mc.playerController.updateController();
        }

        Vec3d hit = new Vec3d(base).addVector(0.5D, 1.0D, 0.5D);
        mc.playerController.processRightClickBlock(
                mc.player, mc.world, base, EnumFacing.UP, hit, EnumHand.MAIN_HAND);
        mc.player.swingArm(EnumHand.MAIN_HAND);

        if (previousSlot != crystalSlot) {
            mc.player.inventory.currentItem = previousSlot;
            mc.playerController.updateController();
        }
        tickDelay = (int) Math.round(delay.getValue());
    }

    private EntityPlayer findTarget(Minecraft mc) {
        EntityPlayer closest = null;
        double maxDistanceSq = range.getValue() * range.getValue();
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player || !player.isEntityAlive() || player.isSpectator()) continue;
            double distanceSq = mc.player.getDistanceSqToEntity(player);
            if (distanceSq <= maxDistanceSq) {
                closest = player;
                maxDistanceSq = distanceSq;
            }
        }
        return closest;
    }

    private EntityEnderCrystal findCrystalToBreak(Minecraft mc, EntityPlayer target) {
        double maxDistanceSq = breakRange.getValue() * breakRange.getValue();
        AxisAlignedBB searchBox = target.getEntityBoundingBox().expandXyz(breakRange.getValue());
        List<EntityEnderCrystal> crystals =
                mc.world.getEntitiesWithinAABB(EntityEnderCrystal.class, searchBox);

        EntityEnderCrystal closest = null;
        double closestDistanceSq = maxDistanceSq;
        for (EntityEnderCrystal crystal : crystals) {
            double targetDistanceSq = target.getDistanceSqToEntity(crystal);
            double playerDistanceSq = mc.player.getDistanceSqToEntity(crystal);
            if (targetDistanceSq <= maxDistanceSq && playerDistanceSq <= 36.0D
                    && targetDistanceSq < closestDistanceSq) {
                closest = crystal;
                closestDistanceSq = targetDistanceSq;
            }
        }
        return closest;
    }

    private BlockPos findBestCrystalPos(Minecraft mc, EntityPlayer target) {
        BlockPos center = new BlockPos(target.posX, target.posY, target.posZ);
        BlockPos best = null;
        double bestDistanceSq = range.getValue() * range.getValue();

        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                for (int dy = -1; dy <= 0; dy++) {
                    BlockPos base = center.add(dx, dy, dz);
                    Block block = mc.world.getBlockState(base).getBlock();
                    if (block != Blocks.OBSIDIAN && block != Blocks.BEDROCK) continue;
                    if (!mc.world.isAirBlock(base.up()) || !mc.world.isAirBlock(base.up(2))) continue;

                    double distanceSq = target.getDistanceSq(
                            base.getX() + 0.5D, base.getY() + 1.0D, base.getZ() + 0.5D);
                    if (distanceSq < bestDistanceSq) {
                        best = base;
                        bestDistanceSq = distanceSq;
                    }
                }
            }
        }
        return best;
    }

    private int findHotbarItem(Minecraft mc, net.minecraft.item.Item item) {
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(slot);
            if (stack.getItem() == item && !stack.func_190926_b()) return slot;
        }
        return -1;
    }
}
