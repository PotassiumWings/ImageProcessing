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

    private boolean isDFT;

    public FFT2D(int width, int height, boolean isDFT) {
        this.originWidth = width;
        this.originHeight = height;
        this.isDFT = isDFT;
        this.width = adjust(width);
        this.height = adjust(height);
        this.inverse = new Complex[this.width * this.height];
        if (isDFT) {
            fftW = new FFT(this.width);
            fftH = new FFT(this.height);
        } else {
            fftW = new FFT(this.width << 1);
            fftH = new FFT(this.height << 1);
        }
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
            Complex[] array = new Complex[isDFT ? width : width * 2];
            for (int j = 0; j < width; j++) {
                array[j] = pixels[i * width + j];
            }
            for(int j = width; j < (isDFT ? width : width * 2); j++) {
                array[j] = new Complex(0, 0);
            }
            Complex[] result = fftW.discreteFourierTransform(array, 1);
            for (int j = 0; j < width; j++) {
                Complex res = getRes(result[j], j, height, isDFT);
                agent[i * width + j] = res;
            }
        }

        // fft at height
        destination = new Complex[width * height];
        for (int i = 0; i < width; i++) {
            Complex[] array = new Complex[isDFT ? height : height * 2];
            for (int j = 0; j < height; j++) {
                array[j] = agent[j * width + i];
            }
            for (int j = height; j < (isDFT ? height : height * 2); j++) {
                array[j] = new Complex(0, 0);
            }
            Complex[] result = fftH.discreteFourierTransform(array, 1);
            for (int j = 0; j < height; j++) {
                Complex res = getRes(result[j], j, width, isDFT);
                destination[j * width + i] = res;
            }
        }
    }

    private Complex getRes(Complex result, int j, int n, boolean isDFT) {
        Complex res;
        if (isDFT) {
            res = result;
        } else {
            double con = Math.sqrt(2.0 / n);
            if (j == 0) {
                con /= Math.sqrt(2);
            }
            res = new Complex(result.x * con, 0);
        }
        return res;
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
        if (isDFT) {
            inverseFFT(dft);
        } else {
            for (int i = 0; i < inverse.length; i++) {
                inverse[i] = new Complex(1, 0);
            }
        }
        return inverse;
    }

    private int adjust(int input) {
        int limit = 1;
        int upp = input + 50;
        // limit set to >= input + 50 to support kernel convolution
        while (limit < upp) {
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
