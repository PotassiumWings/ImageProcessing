package fft;

import org.junit.Test;

import java.util.Arrays;

public class FastFourierTransformTest {

    @Test
    public void test() {
        int i;
        Complex[] complexes = new Complex[8];
        for (i = 0; i < 8; i++) {
            complexes[i] = new Complex(255, 0);
        }
        FastFourierTransform fft = new FastFourierTransform(8);
        Complex[] dft = fft.discreteFourierTransform(complexes, 1);

        System.out.println(Arrays.toString(
                fft.discreteFourierTransform(dft, -1)
        ));
    }

    @Test
    public void test2() {
        int i;
        Complex[] a = new Complex[6];
        Complex[] b = new Complex[6];
        for (i = 0; i < 6; i++) {
            a[i] = new Complex(255, 0);
            b[i] = new Complex(i - 1, 0);
        }
        FastFourierTransform fft = new FastFourierTransform(16);
        Complex[] dfta = fft.discreteFourierTransform(a, 1);
        Complex[] dftb = fft.discreteFourierTransform(b, 1);
        Complex[] dftc = new Complex[dftb.length];
        for (i = 0; i < dfta.length; i++) {
            dftc[i] = dfta[i].multiply(dftb[i]);
        }

        for (i = 0; i < dfta.length; i++) {
            System.out.println(dfta[i]);
        }

        System.out.println(Arrays.toString(
                fft.discreteFourierTransform(dfta, -1)
        ));
        System.out.println(Arrays.toString(
                fft.discreteFourierTransform(dftb, -1)
        ));
        System.out.println(Arrays.toString(
                fft.discreteFourierTransform(dftc, -1)
        ));
    }
}