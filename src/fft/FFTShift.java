package fft;

public class FFTShift {
    private final Complex[] shift;

    public FFTShift(Complex[] complexes, int w, int h) {
        this.shift = new Complex[w * h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Complex toBeFilled = (Complex) complexes[i * w + j].clone();
                int x, y;
                if (i < h / 2 && j < w / 2) {
                    x = i; y = j;
                } else if (i < h / 2 && j >= w / 2) {
                    x = i + h / 2; y = j - w / 2;
                } else if (i >= h / 2 && j < w / 2) {
                    x = i - h / 2; y = j + w / 2;
                } else {
                    x = i - h / 2; y = j - w / 2;
                }
                shift[x * w + y] = toBeFilled;
            }
        }
    }

    public Complex[] getShift() {
        return shift;
    }
}
