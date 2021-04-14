package main.fft;

public class FFTUtils {
    public static int[] getDisplayFFTPixels(Complex[][] complexes) {
        int[] result = new int[complexes[0].length];
        for (int channel = 0; channel < 3; channel++) {
            int max = -1;
            for (int i = 0; i < complexes[channel].length; i++) {
                if (Math.abs(complexes[channel][i].x) > max) {
                    max = (int) Math.abs(complexes[channel][i].x);
                }
            }
            for (int i = 0; i < complexes[channel].length; i++) {
                result[i] |= ((int) (Math.abs(complexes[channel][i].x) * 255 / max)) << (8 * channel);
            }
        }
        return result;
    }
}
