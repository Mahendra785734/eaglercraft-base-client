package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Local scaffold helper. Automatic placement is intentionally restricted
 * to integrated singleplayer worlds.
 */
public class Scaffold extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Setting.NumberSetting placeDelay =
            new Setting.NumberSetting("Place Delay", 2.0D, 1.0D, 6.0D, 1.0D);
    private int ticksUntilPlace;

    public Scaffold() {
        super("Scaffold", "Places blocks beneath you in integrated singleplayer only.", Category.Movement);
        settings.add(placeDelay);
    }

    @Override
    public void onEnable() {
        ticksUntilPlace = 0;
    }

    @Override
    public void onDisable() {
        ticksUntilPlace = 0;
    }

    @Override
    public void onUpdate() {
        if (!isEnabled() || mc.player == null || mc.world == null
                || mc.playerController == null || !mc.isSingleplayer()
                || mc.currentScreen != null) {
            return;
        }

        if (ticksUntilPlace > 0) {
            ticksUntilPlace--;
            return;
        }

        BlockPos target = new BlockPos(mc.player.posX, mc.player.posY - 1.0D, mc.player.posZ);
        if (!mc.world.getBlockState(target).getMaterial().isReplaceable()) {
            return;
        }

        int blockSlot = findBlockInHotbar();
        if (blockSlot < 0) return;

        PlacementData placement = findPlacement(target);
        if (placement == null) return;

        int previousSlot = mc.player.inventory.currentItem;
        if (previousSlot != blockSlot) {
            mc.player.inventory.currentItem = blockSlot;
            mc.playerController.updateController();
        }

        Vec3d hit = new Vec3d(placement.neighbor)
                .addVector(0.5D, 0.5D, 0.5D)
                .add(new Vec3d(placement.face.getDirectionVec()).scale(0.5D));

        EnumActionResult result = mc.playerController.processRightClickBlock(
                mc.player, mc.world, placement.neighbor, placement.face, hit, EnumHand.MAIN_HAND);

        if (result == EnumActionResult.SUCCESS) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            ticksUntilPlace = Math.max(1, placeDelay.getValue().intValue());
        }

        if (previousSlot != blockSlot) {
            mc.player.inventory.currentItem = previousSlot;
            mc.playerController.updateController();
        }
    }

    private int findBlockInHotbar() {
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(slot);
            if (stack.func_190926_b() || !(stack.getItem() instanceof ItemBlock)) continue;

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block == Blocks.AIR || block == Blocks.SAND || block == Blocks.GRAVEL
                    || block == Blocks.WEB) continue;
            if (block.getDefaultState().isFullBlock()
                    && block.getDefaultState().getMaterial().isSolid()) {
                return slot;
            }
        }
        return -1;
    }

    private PlacementData findPlacement(BlockPos target) {
        EnumFacing[] directions = {
            EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH,
            EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP
        };

        for (EnumFacing direction : directions) {
            BlockPos neighbor = target.offset(direction);
            if (!mc.world.getBlockState(neighbor).getMaterial().isReplaceable()) {
                return new PlacementData(neighbor, direction.getOpposite());
            }
        }
        return null;
    }

    private static final class PlacementData {
        private final BlockPos neighbor;
        private final EnumFacing face;

        private PlacementData(BlockPos neighbor, EnumFacing face) {
            this.neighbor = neighbor;
            this.face = face;
        }
    }
}
