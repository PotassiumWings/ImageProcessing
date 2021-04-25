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
    private boolean isDFT;

    public TransformWithFFT(BufferedImage image, boolean isDFT) {
        super(image);
        this.isDFT = isDFT;
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
        this.imageType = image.getType();
        fft2D = new FFT2D(image.getWidth(), image.getHeight(), isDFT);
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
                FFTUtils.getDisplayFFTPixels(FFTShift.shift(rawComplex, paddingWidth, paddingHeight, isDFT), true),
                0, paddingWidth);
        setRawExpression(rawExpression);

        // transformed expression(shift)
        calcDFTTransformedImage(
                FFTShift.shift(rawComplex, paddingWidth, paddingHeight, isDFT),
                paddingWidth, paddingHeight, radius, getMiddle, isDFT
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
        if (isDFT) {
            calcDFTTransformedImage(
                    FFTShift.shift(rawComplex, paddingWidth, paddingHeight, isDFT),
                    paddingWidth, paddingHeight, radius, getMiddle, isDFT
            );
        } else {
            calculate();
        }
    }

    public void gaussianFilter() {
        offsetX = offsetY = gaussianLength;
        if (!isDFT) {
            System.err.println("DFT Gaussian Filter unsupported");
            return;
        }
        gaussianFilter(fft2D, FFTShift.shift(rawComplex, paddingWidth, paddingHeight, isDFT),
                paddingWidth, paddingHeight, radius, offsetX);
    }

    public void waveFilter() {
        offsetX = offsetY = 0;
        if (!isDFT) {
            System.err.println("DFT Wave Filter unsupported");
            return;
        }
        waveFilter(FFTShift.shift(rawComplex, paddingWidth, paddingHeight, isDFT),
                paddingWidth, paddingHeight, radius);
    }

    public void updateTransformedComplex(Complex[][] complex, boolean isDFT) {
        transformedComplex = complex;
        BufferedImage transformedExpression = new BufferedImage(paddingWidth, paddingHeight, imageType);
        transformedExpression.setRGB(0, 0, paddingWidth, paddingHeight,
                FFTUtils.getDisplayFFTPixels(transformedComplex, true), 0, paddingWidth);
        setTransformedExpression(transformedExpression);

        // transformed image
        Complex[][] shiftedComplex = FFTShift.shift(transformedComplex, paddingWidth, paddingHeight, isDFT);
        BufferedImage result = new BufferedImage(imageWidth, imageHeight, imageType);
        Complex[][] adjustComplex = new Complex[3][imageHeight * imageWidth];
        for (int i = 0; i < 3; i++) {
            Complex[] complexes = fft2D.getInverse(shiftedComplex[i]);
//            System.err.println(complexes.length);
            for (int j = 0; j < imageHeight; j++) {
                for (int k = 0; k < imageWidth; k++) {
//                    System.out.println(j * imageWidth + k + " / " + imageHeight * imageWidth);
//                    System.out.println(j * paddingWidth + k + " / " + paddingHeight * paddingWidth);
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

    public abstract void calcDFTTransformedImage(Complex[][] pixels,
                                                 int width, int height, double radius,
                                                 boolean getMiddle, boolean isDFT);

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
