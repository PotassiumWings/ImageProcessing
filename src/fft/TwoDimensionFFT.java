package fft;

public class TwoDimensionFFT {
    private Complex[] destination;
    private Complex[] inverse;
    private final int width;
    private final int height;

    private final int originWidth;
    private final int originHeight;

    private FastFourierTransform fftW;
    private FastFourierTransform fftH;

    private int adjust(int input) {
        int limit = 1;
        while (limit < input) {
            limit <<= 1;
        }
        return limit;
    }

    private double[] getPaddingArray(int[] pixels) {
        double[] res = new double[width * height];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < originWidth && j < originHeight) {
                    res[i * width + j] = pixels[i * width + j];
                } else {
                    res[i * width + j] = 0;
                }
            }
        }
        return res;
    }

    public TwoDimensionFFT(int[] pixels, int width, int height) {
        assert (pixels.length == width * height);
        this.originWidth = width;
        this.originHeight = height;
        this.width = adjust(width);
        this.height = adjust(height);

        constructFFT(getPaddingArray(pixels));
    }

    private void constructFFT(double[] pixels) {
        fftW = new FastFourierTransform(width);
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
        fftH = new FastFourierTransform(height);
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

    public Complex[] getDestination() {
        return destination;
    }

    public Complex[] getInverse(Complex[] dft) {
        inverseFFT(dft);
        return inverse;
    }
}
