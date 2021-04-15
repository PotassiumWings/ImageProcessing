package main.fft;

import org.junit.Assert;

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
        fftW = new FFT(this.width);
        fftH = new FFT(this.height);
    }

    public int getPaddingWidth() {
        return width;
    }

    public int getPaddingHeight() {
        return height;
    }

    private void constructFFT(Complex[] pixels) {
        // fft at width
        Complex[] agent = new Complex[width * height];
        for (int i = 0; i < height; i++) {
            Complex[] array = new Complex[width];
            for (int j = 0; j < width; j++) {
                array[j] = pixels[i * width + j];
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
        Assert.assertEquals (pixels.length, originWidth * originHeight);
        constructFFT(getPaddingArray(pixels));
        return destination;
    }

    public Complex[] getDFT(double[] pixels) {
        Assert.assertEquals (pixels.length, originWidth * originHeight);
        constructFFT(getPaddingArray(pixels));
        return destination;
    }

    public Complex[] getDFT(Complex[] pixels) {
        Assert.assertEquals (pixels.length, width * height);
        constructFFT(pixels);
        return destination;
    }

    public Complex[] getInverse(Complex[] dft) {
        inverseFFT(dft);
        return inverse;
    }

    private int adjust(int input) {
        int limit = 1;
        // limit set to >= input + 50 to support kernel convolution
        while (limit < input + 50) {
            limit <<= 1;
        }
        return limit;
    }

    private Complex[] getPaddingArray(int[] pixels) {
        double[] dbPixels = new double[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            dbPixels[i] = pixels[i];
        }
        return getPaddingArray(dbPixels);
    }

    private Complex[] getPaddingArray(double[] pixels) {
        Complex[] res = new Complex[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < originHeight && j < originWidth) {
                    res[i * width + j] = new Complex(pixels[i * originWidth + j], 0);
                } else {
                    res[i * width + j] = Complex.ZERO;
                }
            }
        }
        return res;
    }
}
