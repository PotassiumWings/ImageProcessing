package main.fft;

public class FFT2D {
    private Complex[] destination;
    private Complex[] inverse;
    private final int width;
    private final int height;

    private final int originWidth;
    private final int originHeight;

    private final FFT fftW;
    private final FFT fftH;

    public FFT2D(int width, int height) {
        this.originWidth = width;
        this.originHeight = height;
        this.width = adjust(width);
        this.height = adjust(height);
        fftW = new FFT(width);
        fftH = new FFT(height);
    }

    public int getPaddingWidth() {
        return width;
    }

    public int getPaddingHeight() {
        return height;
    }

    private void constructFFT(double[] pixels) {
        // fft at width
        Complex[] agent = new Complex[width * height];
        for (int i = 0; i < height; i++) {
            Complex[] array = new Complex[width];
            for (int j = 0; j < width; j++) {
                array[j] = new Complex(pixels[i * width + j], 0);
            }
            Complex[] result = fftW.discreteFourierTransform(array, 1);
            for (int j = 0; j < width; j++) {
                agent[i * width + j] = result[j];
            }
        }

        // fft at height
        destination = new Complex[width * height];
        for (int i = 0; i < width; i++) {
            Complex[] array = new Complex[height];
            for (int j = 0; j < height; j++) {
                array[j] = agent[j * width + i];
            }
            Complex[] result = fftH.discreteFourierTransform(array, 1);
            for (int j = 0; j < height; j++) {
                destination[j * width + i] = result[j];
            }
        }
    }

    private void inverseFFT(Complex[] dft) {
        // fft at height
        Complex[] agent = new Complex[width * height];
        for (int i = 0; i < width; i++) {
            Complex[] array = new Complex[height];
            for (int j = 0; j < height; j++) {
                array[j] = dft[j * width + i];
            }
            Complex[] result = fftH.discreteFourierTransform(array, -1);
            for (int j = 0; j < height; j++) {
                agent[j * width + i] = result[j];
            }
        }

        // fft at width
        inverse = new Complex[width * height];
        for (int i = 0; i < height; i++) {
            Complex[] array = new Complex[width];
            if (width >= 0) System.arraycopy(agent, i * width, array, 0, width);
            Complex[] result = fftW.discreteFourierTransform(array, -1);
            for (int j = 0; j < width; j++) {
                inverse[i * width + j] = result[j];
            }
        }
    }

    public Complex[] getDFT(int[] pixels) {
        assert (pixels.length == width * height);
        constructFFT(getPaddingArray(pixels));
        return destination;
    }

    public Complex[] getInverse(Complex[] dft) {
        inverseFFT(dft);
        return inverse;
    }

    private int adjust(int input) {
        int limit = 1;
        while (limit < input) {
            limit <<= 1;
        }
        return limit;
    }

    private double[] getPaddingArray(int[] pixels) {
        double[] res = new double[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < originWidth && j < originHeight) {
                    res[i * width + j] = pixels[i * originWidth + j];
                } else {
                    res[i * width + j] = 0;
                }
            }
        }
        return res;
    }
}
