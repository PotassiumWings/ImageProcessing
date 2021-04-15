package main.fft;

import org.junit.Assert;

import java.awt.image.BufferedImage;

public class FourierTransform extends TransformWithFFT {
    public static final double PI = Math.acos(-1);

    public FourierTransform(BufferedImage image) {
        super(image);
    }

    @Override
    public void calcTransformedImage(Complex[][] pixels,
                                     int width, int height, double radius, boolean getMiddle) {
        Complex[][] res = new Complex[pixels.length][pixels[0].length];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (inRadiusOf(j, width, height, radius, getMiddle)) {
                    res[i][j] = pixels[i][j];
                } else {
                    res[i][j] = new Complex(0, 0);
                }
            }
        }
        updateTransformedComplex(res);
    }

    private boolean inRadiusOf(int i, int width, int height, double radius, boolean getMiddle) {
        int x = i / width;
        int y = i % width;
        boolean isMiddle = Math.pow(y - width / 2, 2) + Math.pow(x - height / 2, 2)
                <= Math.pow(radius * Math.min(height, width), 2);
        return isMiddle ^ getMiddle;
    }

    @Override
    public void calcTransformedImage(int[] pixels, int imageType) {

    }

    public void gaussianFilter(FFT2D fft2D, Complex[][] pixels, int paddingWidth, int paddingHeight,
                               double sigma, int len) {
        Assert.assertTrue(len < 25); // FFT2D: limit >= w + 50
        Complex[][] res = new Complex[pixels.length][pixels[0].length];
        Complex[] gaussian = new Complex[paddingWidth * paddingHeight];
        for (int i = 0; i < paddingHeight; i++) {
            for (int j = 0; j < paddingWidth; j++) {
                if (!(i < 2 * len + 1 && j < 2 * len + 1)) {
                    gaussian[i * paddingWidth + j] = Complex.ZERO;
                } else {
                    int x = i - len;
                    int y = j - len;
                    double p = Math.exp(-(x * x + y * y) / 2.0 / sigma / sigma) / 2.0 / PI / sigma / sigma;
                    gaussian[i * paddingWidth + j] = new Complex(p, 0);
                }
            }
        }
        gaussian = fft2D.getDFT(gaussian);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                res[i][j] = gaussian[j].multiply(pixels[i][j]);
            }
        }
        res = FFTShift.shift(res, paddingWidth, paddingHeight);
        updateTransformedComplex(res);
    }

    public void waveFilter(Complex[][] pixels, int width, int height, double radius) {
        Complex[][] res = new Complex[pixels.length][pixels[0].length];
        int[] displayPixels = FFTUtils.getDisplayFFTPixels(pixels, true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                res[i][j] = (Complex) pixels[i][j].clone();
            }
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    if (Math.abs(j - height / 2) <= radius * Math.min(width, height) &&
                            Math.abs(k - width / 2) <= radius * Math.min(width, height)) {
                        continue;
                    }
                    int temp = 0;
                    for (int u = -5; u <= 5; u++) {
                        for (int v = -5; v <= 5; v++) {
                            if (j + u >= 0 && j + u < height && k + v >= 0 && k + v < width &&
                                    ((displayPixels[(j + u) * width + k + v] >> (8 * i)) & 0xff) >= 150) {
                                temp++;
                            }
                        }
                    }
                    if (temp >= 7) {
                        for (int u = -5; u <= 5; u++) {
                            for (int v = -5; v <= 5; v++) {
                                if (j + u >= 0 && j + u < height && k + v >= 0 && k + v < width) {
                                    res[i][(j + u) * width + k + v] = Complex.ZERO;
                                }
                            }
                        }
                    }
                }
            }
        }
        updateTransformedComplex(res);
    }
}
