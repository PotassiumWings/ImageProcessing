package main.fft;

import org.junit.Test;

import static org.junit.Assert.*;

public class FFTShiftTest {
    @Test
    public void testFFTShift() {
        Complex[][] complexes = new Complex[3][8];
        for (int i = 0; i < 8; i++) {
            complexes[0][i] = new Complex(i, 0);
            complexes[1][i] = new Complex(i, 0);
            complexes[2][i] = new Complex(i, 0);
        }
        Complex[][] res = FFTShift.shift(complexes, 2, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(res[0][i * 2 + j].x + " ");
            }
            System.out.println();
        }
        System.out.println();
        Complex[][] res2 = FFTShift.shift(complexes, 4, 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(res2[0][i * 4 + j].x + " ");
            }
            System.out.println();
        }
    }
}