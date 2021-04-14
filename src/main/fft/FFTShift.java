package main.fft;

public class FFTShift {

    public static Complex[][] shift(Complex[][] complexes, int w, int h) {
        Complex[][] shift = new Complex[3][w * h];
        for (int c = 0; c < 3; c++) {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    Complex toBeFilled = (Complex) complexes[c][i * w + j].clone();
                    int x, y;
                    if (i < h / 2 && j < w / 2) {
                        x = i + h / 2; y = j + w / 2;
                    } else if (i < h / 2 && j >= w / 2) {
                        x = i + h / 2; y = j - w / 2;
                    } else if (i >= h / 2 && j < w / 2) {
                        x = i - h / 2; y = j + w / 2;
                    } else {
                        x = i - h / 2; y = j - w / 2;
                    }
                    shift[c][x * w + y] = toBeFilled;
                }
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (shift[c][i * w + j] == null) {
                        System.err.println(i + " " + j);
                    }
                }
            }
        }
        return shift;
    }
}
