package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import net.minecraft.client.Minecraft;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/** Local container locator for integrated singleplayer worlds only. */
public class ChestESP extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Setting.BooleanSetting normalChests = new Setting.BooleanSetting("Normal Chests", true);
    private final Setting.BooleanSetting trappedChests = new Setting.BooleanSetting("Trapped Chests", true);
    private final Setting.BooleanSetting enderChests = new Setting.BooleanSetting("Ender Chests", true);
    private final Setting.BooleanSetting shulkerBoxes = new Setting.BooleanSetting("Shulker Boxes", true);
    private final Setting.ModeSetting colorMode = new Setting.ModeSetting("Color", "Classic", "Classic", "Rainbow");
    private final Setting.BooleanSetting tracers = new Setting.BooleanSetting("Tracers", false);

    public ChestESP() {
        super("ChestESP", "Highlights containers in integrated singleplayer", Category.Render);
        settings.add(normalChests);
        settings.add(trappedChests);
        settings.add(enderChests);
        settings.add(shulkerBoxes);
        settings.add(colorMode);
        settings.add(tracers);
    }

    public void onRender3D(float partialTicks) {
        if (!isEnabled() || mc.world == null || mc.player == null || !mc.isSingleplayer()) return;
        for (TileEntity tile : mc.world.loadedTileEntityList) {
            if (tile == null || tile.isInvalid()) continue;
            BlockPos pos = tile.getPos();
            float[] rgb = null;
            if (tile instanceof TileEntityEnderChest && enderChests.getValue()) {
                rgb = getColor(pos, 0.68F, 0.24F, 1.0F);
            } else if (tile instanceof TileEntityShulkerBox && shulkerBoxes.getValue()) {
                rgb = getColor(pos, 0.15F, 0.85F, 0.95F);
            } else if (tile instanceof TileEntityChest) {
                boolean trapped = mc.world.getBlockState(pos).getBlock() == Blocks.TRAPPED_CHEST;
                if (trapped && trappedChests.getValue()) rgb = getColor(pos, 1.0F, 0.25F, 0.22F);
                else if (!trapped && normalChests.getValue()) rgb = getColor(pos, 1.0F, 0.65F, 0.0F);
            }
            if (rgb != null) {
                drawContainer(pos, rgb[0], rgb[1], rgb[2], 0.92F);
                if (tracers.getValue()) drawTracer(pos, rgb[0], rgb[1], rgb[2]);
            }
        }
    }

    private float[] getColor(BlockPos pos, float red, float green, float blue) {
        if (!"Rainbow".equalsIgnoreCase(colorMode.getValue())) return new float[] { red, green, blue };
        float hue = ((System.currentTimeMillis() % 4200L) / 4200.0F
                + (float) (pos.hashCode() & 255) / 255.0F) % 1.0F;
        float h = hue * 6.0F;
        int sector = (int) h;
        float f = h - sector;
        float p = 0.18F, q = 1.0F - 0.82F * f, t = 0.18F + 0.82F * f;
        switch (sector % 6) {
        case 0: return new float[] { 1.0F, t, p };
        case 1: return new float[] { q, 1.0F, p };
        case 2: return new float[] { p, 1.0F, t };
        case 3: return new float[] { p, q, 1.0F };
        case 4: return new float[] { t, p, 1.0F };
        default: return new float[] { 1.0F, p, q };
        }
    }

    private void drawContainer(BlockPos pos, float red, float green, float blue, float alpha) {
        double x = pos.getX() - mc.getRenderManager().viewerPosX;
        double y = pos.getY() - mc.getRenderManager().viewerPosY;
        double z = pos.getZ() - mc.getRenderManager().viewerPosZ;
        AxisAlignedBB box = new AxisAlignedBB(x + 0.0625D, y, z + 0.0625D,
                x + 0.9375D, y + 0.875D, z + 0.9375D);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderGlobal.drawSelectionBoundingBox(box, red, green, blue, alpha);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void drawTracer(BlockPos pos, float red, float green, float blue) {
        double vx = mc.getRenderManager().viewerPosX;
        double vy = mc.getRenderManager().viewerPosY;
        double vz = mc.getRenderManager().viewerPosZ;
        double sx = mc.player.posX - vx;
        double sy = mc.player.posY + mc.player.getEyeHeight() - vy;
        double sz = mc.player.posZ - vz;
        double ex = pos.getX() + 0.5D - vx;
        double ey = pos.getY() + 0.5D - vy;
        double ez = pos.getZ() + 0.5D - vz;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(sx, sy, sz).color(red, green, blue, 0.95F).endVertex();
        buffer.pos(ex, ey, ez).color(red, green, blue, 0.95F).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
