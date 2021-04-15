package main.fft;

import main.Transform;
import java.awt.image.BufferedImage;

public abstract class TransformWithFFT extends Transform {
    private BufferedImage image;
    private BufferedImage transformedImage;
    private int imageWidth;
    private int imageHeight;
    private int paddingWidth;
    private int paddingHeight;
    private int imageType;

    private int[][] rawPixels;
    private int[][] transformedPixels;

    private Complex[][] rawComplex;
    private Complex[][] transformedComplex;

    private double radius = 0.2;

    private int offsetX = 0;
    private int offsetY = 0;
    private int gaussianLength = 3;

    private FFT2D fft2D;

    private boolean getMiddle = false;

    public TransformWithFFT(BufferedImage image) {
        super(image);
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
        this.imageType = image.getType();
        fft2D = new FFT2D(image.getWidth(), image.getHeight());
        rawPixels = new int[3][imageHeight * imageWidth];
        rawComplex = new Complex[3][];
    }

    public void calculate() {
        // raw image
        getRawPixels();
        paddingHeight = fft2D.getPaddingHeight();
        paddingWidth = fft2D.getPaddingWidth();
        for (int i = 0; i < 3; i++) {
            rawComplex[i] = fft2D.getDFT(rawPixels[i]);
        }

        // raw expression
        BufferedImage rawExpression = new BufferedImage(paddingWidth, paddingHeight, imageType);
        rawExpression.setRGB(0, 0, paddingWidth, paddingHeight,
                FFTUtils.getDisplayFFTPixels(FFTShift.shift(rawComplex, paddingWidth, paddingHeight), true),
                0, paddingWidth);
        setRawExpression(rawExpression);

        // transformed expression(shift)
        calcTransformedImage(
                FFTShift.shift(rawComplex, paddingWidth, paddingHeight),
                paddingWidth, paddingHeight, radius, getMiddle
        );
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setGaussianLength(int length) {
        this.gaussianLength = length;
    }

    public void recalculate() {
        offsetX = offsetY = 0;
        calcTransformedImage(
                FFTShift.shift(rawComplex, paddingWidth, paddingHeight),
                paddingWidth, paddingHeight, radius, getMiddle
        );
    }

    public void gaussianFilter() {
        offsetX = offsetY = gaussianLength;
        gaussianFilter(fft2D, FFTShift.shift(rawComplex, paddingWidth, paddingHeight),
                paddingWidth, paddingHeight, radius, offsetX);
    }

    public void waveFilter() {
        offsetX = offsetY = 0;
        waveFilter(FFTShift.shift(rawComplex, paddingWidth, paddingHeight),
                paddingWidth, paddingHeight, radius);
    }

    public void updateTransformedComplex(Complex[][] complex) {
        transformedComplex = complex;
        BufferedImage transformedExpression = new BufferedImage(paddingWidth, paddingHeight, imageType);
        transformedExpression.setRGB(0, 0, paddingWidth, paddingHeight,
                FFTUtils.getDisplayFFTPixels(transformedComplex, true), 0, paddingWidth);
        setTransformedExpression(transformedExpression);

        // transformed image
        Complex[][] shiftedComplex = FFTShift.shift(transformedComplex, paddingWidth, paddingHeight);
        BufferedImage result = new BufferedImage(imageWidth, imageHeight, imageType);
        Complex[][] adjustComplex = new Complex[3][imageHeight * imageWidth];
        for (int i = 0; i < 3; i++) {
            Complex[] complexes = fft2D.getInverse(shiftedComplex[i]);
            for (int j = 0; j < imageHeight; j++) {
                for (int k = 0; k < imageWidth; k++) {
                    adjustComplex[i][j * imageWidth + k] =
                            complexes[(j + offsetX) * paddingWidth + k + offsetY];
                }
            }
        }
        result.setRGB(0, 0, imageWidth, imageHeight,
                FFTUtils.getDisplayFFTPixels(adjustComplex, false), 0, imageWidth);
        setTransformedImage(result);
    }

    public void setTransformedImage(BufferedImage image) {
        super.setTransformedImage(image);
        this.transformedImage = image;
    }

    public void flip() {
        this.getMiddle ^= true;
    }

    public abstract void calcTransformedImage(Complex[][] pixels,
                                              int width, int height, double radius, boolean getMiddle);

    public abstract void gaussianFilter(FFT2D fft2D, Complex[][] pixels, int width, int height, double radius, int len);

    public abstract void waveFilter(Complex[][] pixels, int width, int height, double radius);

    private void getRawPixels() {
        int[] pixels = new int[imageWidth * imageHeight];
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                pixels, 0, image.getWidth());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < pixels.length; j++) {
                rawPixels[i][j] = pixels[j] >> (8 * i) & 255;
            }
        }
    }
}
