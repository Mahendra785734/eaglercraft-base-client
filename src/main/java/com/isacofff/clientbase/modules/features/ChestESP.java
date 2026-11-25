package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import net.minecraft.client.Minecraft;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Local chest locator for integrated singleplayer worlds.
 * Intentionally disabled on multiplayer servers.
 */
public class ChestESP extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public ChestESP() {
        super("ChestESP", "Highlights nearby chests in singleplayer", Category.Render);
    }

    public void onRender3D(float partialTicks) {
        if (!isEnabled() || mc.world == null || mc.player == null || !mc.isSingleplayer()) {
            return;
        }

        for (TileEntity tile : mc.world.loadedTileEntityList) {
            if (tile == null || tile.isInvalid()) {
                continue;
            }

            if (tile instanceof TileEntityChest) {
                drawChest(tile.getPos(), 1.0F, 0.65F, 0.0F, 0.9F);
            } else if (tile instanceof TileEntityEnderChest) {
                drawChest(tile.getPos(), 0.65F, 0.2F, 1.0F, 0.9F);
            }
        }
    }

    private void drawChest(BlockPos pos, float red, float green, float blue, float alpha) {
        double x = pos.getX() - mc.getRenderManager().viewerPosX;
        double y = pos.getY() - mc.getRenderManager().viewerPosY;
        double z = pos.getZ() - mc.getRenderManager().viewerPosZ;

        AxisAlignedBB box = new AxisAlignedBB(
            x + 0.0625D, y, z + 0.0625D,
            x + 0.9375D, y + 0.875D, z + 0.9375D
        );

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
}
