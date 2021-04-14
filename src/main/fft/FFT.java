package main.fft;

public class FFT {
    private static final double PI = Math.acos(-1);
    private int limit;

    private final int[] rev;

    public FFT(int n) {
        limit = 1;
        while (limit < n) {
            limit <<= 1;
        }
        assert (limit == n);

        rev = new int[limit];
        for (int i = 0; i < limit; i++) {
            rev[i] = rev[i >> 1] >> 1 | ((i & 1) != 0 ? limit >> 1 : 0);
        }
    }

    public Complex[] discreteFourierTransform(Complex[] input, int sign) {
        int i, j, k;
        Complex[] array = new Complex[limit];
        for (i = 0; i < limit; i++) {
            if (i < input.length) {
                array[i] = input[i];
            } else {
                array[i] = new Complex(0, 0);
            }
        }
        for (i = 0; i < limit; i++) {
            if (i < rev[i]) {
                Complex temp = array[i];
                array[i] = array[rev[i]];
                array[rev[i]] = temp;
            }
        }
        for (k = 1; k < limit; k <<= 1) {
            Complex wn1 = new Complex(Math.cos(PI / k), sign * Math.sin(PI / k));
            for (j = 0; j < limit; j += (k << 1)) {
                Complex wnk = new Complex(1, 0);
                for (i = j; i < j + k; i++) {
                    Complex t = array[i + k].multiply(wnk);
                    array[i + k] = array[i].subtract(t);
                    array[i] = array[i].add(t);
                    wnk = wnk.multiply(wn1);
                }
            }
        }
        // IDFT
        if (sign == -1) {
            for (i = 0; i < limit; i++) {
                array[i].x = array[i].x / limit;
                array[i].y = array[i].y / limit;
            }
        }
        return array;
    }
}
