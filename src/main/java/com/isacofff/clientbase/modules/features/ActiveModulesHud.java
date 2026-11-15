package com.isacofff.clientbase.modules.features;

/** Runtime layout and style settings for the ActiveModules overlay. */
public final class ActiveModulesHud {
    public static int x = 6;
    public static int y = 70;
    public static int colorIndex = 0;
    public static boolean rainbow = false;
    public static boolean background = true;
    public static boolean leftAccent = true;
    public static boolean editing = false;
    private static String colorMode = "GOLD";

    private static final int[] COLORS = new int[] {
        0xFFFFC45C, 0xFF55D6BE, 0xFF69A7FF, 0xFFFF6B81,
        0xFFB78CFF, 0xFFFFFFFF, 0xFF72D572, 0xFFFFA45B
    };

    private ActiveModulesHud() {}

    public static void setColorMode(String mode) {
        colorMode = mode == null ? "GOLD" : mode;
        rainbow = "RAINBOW".equalsIgnoreCase(colorMode);
        for (int i = 0; i < 8; ++i) {
            if (new String[] {"GOLD","TEAL","BLUE","PINK","PURPLE","WHITE","GREEN","ORANGE"}[i].equalsIgnoreCase(colorMode)) {
                colorIndex = i;
                break;
            }
        }
    }

    public static int getColor(int index) {
        if (rainbow) {
            float hue = (System.currentTimeMillis() % 5000L) / 5000.0F + index * 0.045F;
            hue = hue - (float) Math.floor(hue);
            return 0xFF000000 | (net.minecraft.util.math.MathHelper.hsvToRGB(hue, 0.82F, 1.0F) & 0xFFFFFF);
        }
        return COLORS[colorIndex % COLORS.length];
    }

    public static void nextColor() {
        colorIndex = (colorIndex + 1) % COLORS.length;
        rainbow = false;
    }
}