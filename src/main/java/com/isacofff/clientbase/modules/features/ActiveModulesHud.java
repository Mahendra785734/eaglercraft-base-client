package com.isacofff.clientbase.modules.features;

/** Runtime layout and style settings for the ActiveModules overlay. */
public final class ActiveModulesHud {
    public static int x = 6;
    public static int y = 70;
    public static int colorIndex = 0;
    public static boolean rainbow = false;
    public static boolean background = true;
    public static boolean leftAccent = true;
    public static String backgroundStyle = "SOLID";
    public static String accentMode = "LEFT";
    public static String accentColorMode = "MATCH";
    public static boolean editing = false;
    private static String colorMode = "GOLD";

    private static final String[] COLOR_NAMES = new String[] {
        "GOLD", "TEAL", "BLUE", "PINK", "PURPLE", "WHITE", "GREEN", "ORANGE"
    };
    private static final int[] COLORS = new int[] {
        0xFFFFC45C, 0xFF55D6BE, 0xFF69A7FF, 0xFFFF6B81,
        0xFFB78CFF, 0xFFFFFFFF, 0xFF72D572, 0xFFFFA45B
    };

    private ActiveModulesHud() {}

    public static void setColorMode(String mode) {
        colorMode = mode == null ? "GOLD" : mode.toUpperCase();
        rainbow = "RAINBOW".equals(colorMode);
        if (rainbow) return;

        for (int i = 0; i < COLOR_NAMES.length; ++i) {
            if (COLOR_NAMES[i].equals(colorMode)) {
                colorIndex = i;
                return;
            }
        }

        colorMode = "GOLD";
        colorIndex = 0;
    }

    private static int getRainbowColor(int index) {
        float hue = (System.currentTimeMillis() % 5000L) / 5000.0F + index * 0.045F;
        hue = hue - (float) Math.floor(hue);
        return 0xFF000000 | (net.minecraft.util.math.MathHelper.hsvToRGB(hue, 0.82F, 1.0F) & 0xFFFFFF);
    }

    public static int getColor(int index) {
        return rainbow ? getRainbowColor(index) : COLORS[colorIndex];
    }

    public static int getAccentColor(int index) {
        if ("MATCH".equalsIgnoreCase(accentColorMode)) return getColor(index);
        if ("RAINBOW".equalsIgnoreCase(accentColorMode)) return getRainbowColor(index + 3);

        for (int i = 0; i < COLOR_NAMES.length; ++i) {
            if (COLOR_NAMES[i].equalsIgnoreCase(accentColorMode)) return COLORS[i];
        }
        return COLORS[0];
    }

    public static void nextColor() {
        colorIndex = (colorIndex + 1) % COLORS.length;
        colorMode = COLOR_NAMES[colorIndex];
        rainbow = false;
    }
}
