package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.settings.Setting;
import com.isacofff.clientbase.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.lax1dude.eaglercraft.opengl.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

/** Player outlines for local integrated singleplayer testing only. */
public class PlayerESP extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Setting.BooleanSetting box = new Setting.BooleanSetting("Box", true);
    private final Setting.BooleanSetting tracers = new Setting.BooleanSetting("Tracers", true);
    private final Setting.BooleanSetting invisibles = new Setting.BooleanSetting("Invisibles", false);
    private final Setting.BooleanSetting rainbow = new Setting.BooleanSetting("Rainbow", true);
    private final Setting.NumberSetting rainbowSpeed =
            new Setting.NumberSetting("Rainbow Speed", 4.0D, 1.0D, 10.0D, 0.5D);
    private final Setting.NumberSetting red = new Setting.NumberSetting("Red", 255.0D, 0.0D, 255.0D, 1.0D);
    private final Setting.NumberSetting green = new Setting.NumberSetting("Green", 0.0D, 0.0D, 255.0D, 1.0D);
    private final Setting.NumberSetting blue = new Setting.NumberSetting("Blue", 0.0D, 0.0D, 255.0D, 1.0D);

    public PlayerESP() {
        super("PlayerESP", "Player boxes and tracers in integrated singleplayer only.", Category.Render);
        settings.add(box);
        settings.add(tracers);
        settings.add(invisibles);
        settings.add(rainbow);
        settings.add(rainbowSpeed);
        settings.add(red);
        settings.add(green);
        settings.add(blue);
    }

    public void onRender3D(float partialTicks) {
        if (!isEnabled() || mc.world == null || mc.player == null || !mc.isSingleplayer()) return;

        long index = 0L;
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) || entity == mc.player || entity.isDead) continue;
            if (entity.isInvisible() && !invisibles.getValue()) continue;

            float[] color = getColor(index++ * 175L);
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks
                    - mc.getRenderManager().viewerPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks
                    - mc.getRenderManager().viewerPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks
                    - mc.getRenderManager().viewerPosZ;

            if (box.getValue()) {
                AxisAlignedBB bounds = entity.getEntityBoundingBox()
                        .offset(-entity.posX, -entity.posY, -entity.posZ)
                        .offset(x, y, z).expand(0.04D, 0.02D, 0.04D);
                drawBox(bounds, color[0], color[1], color[2]);
            }
            if (tracers.getValue()) {
                drawTracer(x, y + entity.height * 0.5D, z, color[0], color[1], color[2]);
            }
        }
    }

    private float[] getColor(long index) {
        int rgb = rainbow.getValue()
                ? ColorUtil.getRainbow(rainbowSpeed.getValue().floatValue(), 0.85F, 1.0F, index)
                : ColorUtil.hsbToRgb(0.0F, 0.0F, 1.0F);
        float r = rainbow.getValue() ? ((rgb >> 16) & 255) / 255.0F : red.getValue().floatValue() / 255.0F;
        float g = rainbow.getValue() ? ((rgb >> 8) & 255) / 255.0F : green.getValue().floatValue() / 255.0F;
        float b = rainbow.getValue() ? (rgb & 255) / 255.0F : blue.getValue().floatValue() / 255.0F;
        return new float[] { r, g, b };
    }

    private void beginRender() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
    }

    private void endRender() {
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void drawBox(AxisAlignedBB bb, float r, float g, float b) {
        beginRender();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        line(buffer, bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.minZ, r, g, b);
        line(buffer, bb.maxX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, r, g, b);
        line(buffer, bb.maxX, bb.minY, bb.maxZ, bb.minX, bb.minY, bb.maxZ, r, g, b);
        line(buffer, bb.minX, bb.minY, bb.maxZ, bb.minX, bb.minY, bb.minZ, r, g, b);
        line(buffer, bb.minX, bb.maxY, bb.minZ, bb.maxX, bb.maxY, bb.minZ, r, g, b);
        line(buffer, bb.maxX, bb.maxY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, r, g, b);
        line(buffer, bb.maxX, bb.maxY, bb.maxZ, bb.minX, bb.maxY, bb.maxZ, r, g, b);
        line(buffer, bb.minX, bb.maxY, bb.maxZ, bb.minX, bb.maxY, bb.minZ, r, g, b);
        line(buffer, bb.minX, bb.minY, bb.minZ, bb.minX, bb.maxY, bb.minZ, r, g, b);
        line(buffer, bb.maxX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.minZ, r, g, b);
        line(buffer, bb.maxX, bb.minY, bb.maxZ, bb.maxX, bb.maxY, bb.maxZ, r, g, b);
        line(buffer, bb.minX, bb.minY, bb.maxZ, bb.minX, bb.maxY, bb.maxZ, r, g, b);
        tessellator.draw();
        endRender();
    }

    private void drawTracer(double x, double y, double z, float r, float g, float b) {
        beginRender();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getBuffer();
        buffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        line(buffer, 0.0D, 0.0D, 0.0D, x, y, z, r, g, b);
        tessellator.draw();
        endRender();
    }

    private void line(WorldRenderer buffer, double x1, double y1, double z1,
            double x2, double y2, double z2, float r, float g, float b) {
        buffer.pos(x1, y1, z1).color(r, g, b, 1.0F).endVertex();
        buffer.pos(x2, y2, z2).color(r, g, b, 1.0F).endVertex();
    }
}
