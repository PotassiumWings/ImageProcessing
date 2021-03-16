package utils;

public class ColorGetter {
    public static int getColorValue(int pixel, int channel) {
        return pixel >> (8 * channel) & 0x00ff;
    }
}
