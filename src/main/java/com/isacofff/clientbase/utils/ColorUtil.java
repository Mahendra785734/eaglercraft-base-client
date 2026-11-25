package com.isacofff.clientbase.utils;

/** Small AWT-free color helpers that also work in the browser build. */
public final class ColorUtil {
    private ColorUtil() {}

    public static int getRainbow(float seconds, float saturation, float brightness, long index) {
        float period = Math.max(0.1F, seconds) * 1000.0F;
        float hue = ((System.currentTimeMillis() + index) % (long) period) / period;
        return hsbToRgb(hue, saturation, brightness);
    }

    public static int hsbToRgb(float hue, float saturation, float brightness) {
        hue = hue - (float) Math.floor(hue);
        saturation = clamp(saturation);
        brightness = clamp(brightness);
        float scaled = hue * 6.0F;
        int sector = (int) scaled;
        float fraction = scaled - sector;
        float p = brightness * (1.0F - saturation);
        float q = brightness * (1.0F - saturation * fraction);
        float t = brightness * (1.0F - saturation * (1.0F - fraction));
        float r, g, b;
        switch (sector % 6) {
            case 0: r = brightness; g = t; b = p; break;
            case 1: r = q; g = brightness; b = p; break;
            case 2: r = p; g = brightness; b = t; break;
            case 3: r = p; g = q; b = brightness; break;
            case 4: r = t; g = p; b = brightness; break;
            default: r = brightness; g = p; b = q; break;
        }
        return (Math.round(r * 255.0F) << 16) | (Math.round(g * 255.0F) << 8)
                | Math.round(b * 255.0F) | 0xFF000000;
    }

    private static float clamp(float value) {
        return value < 0.0F ? 0.0F : (value > 1.0F ? 1.0F : value);
    }
}
