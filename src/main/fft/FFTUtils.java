package main.fft;

public class FFTUtils {
    public static int[] getDisplayFFTPixels(Complex[][] complexes, boolean useLog) {
        int[] result = new int[complexes[0].length];
        for (int channel = 0; channel < 3; channel++) {
            int max = -1;
            for (int i = 0; i < complexes[channel].length; i++) {
                int pixel;
                if (useLog) {
                    pixel = (int) Math.log10(Math.abs(complexes[channel][i].x) + 1);
                } else {
                    pixel = (int) Math.abs(complexes[channel][i].x);
                }
                if (pixel > max) {
                    max = pixel;
                }
            }
            if (max <= 1) {
                max = 255;
            }
            for (int i = 0; i < complexes[channel].length; i++) {
                int pixel;
                if (useLog) {
                    pixel = (int) (Math.log10(Math.abs(complexes[channel][i].x) + 1) * 255 / max);
                } else {
                    pixel = (int) (Math.abs(complexes[channel][i].x) * 255 / max);
                }
                result[i] |= pixel << (8 * channel);
            }
        }
        return result;
    }
}
