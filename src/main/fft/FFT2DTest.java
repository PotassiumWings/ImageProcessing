package main.fft;

import org.junit.Test;

public class FFT2DTest {

    @Test
    public void testAll() {
        int w = 16;
        int h = 16;
        int[] pixels = new int[w * h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
//                pixels[i * w + j] = 255;
//                pixels[i * w + j] = ((i / 8) ^ (j / 8)) == 1 ? 255 : 0;
//                pixels[i * w + j] = 0;
                pixels[i * w + j] = (i < 8) ? 255 : 0;
            }
        }
        FFT2D FFT2D = new FFT2D(w, h);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(pixels[i * w + j] + "\t");
            }
            System.out.println();
        }
        Complex[] complex = FFT2D.getDFT(pixels);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Complex toPrint = complex[i * w + j];
                // System.out.print(String.format("%.2f\t", complex[i * w + j].x));
//                if (Math.abs(toPrint.x - 0) < 1e-10) {
//                    System.err.println(i + " " + j);
//                }
                int x = (int) (Math.log10(Math.abs(toPrint.x) + 1) / 4 * 255);
                System.out.print(x + "\t");
            }
            System.out.println();
        }
//        Complex[] complex = twoDimensionFFT.getInverse();
//        for (int i = 0; i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                int x = (int) complex[i * w + j].x;
//                System.out.print(x + "\t");
//            }
//            System.out.println();
//        }
    }
}