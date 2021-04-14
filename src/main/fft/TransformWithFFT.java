package main.fft;

import main.Transform;
import main.utils.AutoAdjustIcon;

import javax.swing.JLabel;
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

    private FFT2D fft2D;

    public TransformWithFFT(BufferedImage image) {
        super(image);
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
        this.imageType = image.getType();
        rawPixels = new int[3][imageHeight * imageWidth];
        rawComplex = new Complex[3][];
    }

    public void calculate() {
        // raw image and expression
        getRawPixels();
        fft2D = new FFT2D(image.getWidth(), image.getHeight());
        paddingHeight = fft2D.getPaddingHeight();
        paddingWidth = fft2D.getPaddingWidth();
        for (int i = 0; i < 3; i++) {
            rawComplex[i] = fft2D.getDFT(rawPixels[i]);
        }

        BufferedImage rawExpression = new BufferedImage(paddingWidth, paddingHeight, imageType);
        rawExpression.setRGB(0, 0, paddingWidth, paddingHeight,
                FFTUtils.getDisplayFFTPixels(FFTShift.shift(rawComplex, paddingWidth, paddingHeight), true),
                0, paddingWidth);
        setRawExpression(rawExpression);

        // transformed image and expression
        calcTransformedImage(rawComplex);
        BufferedImage transformedExpression = new BufferedImage(paddingWidth, paddingHeight, imageType);
        transformedExpression.setRGB(0, 0, paddingWidth, paddingHeight,
                FFTUtils.getDisplayFFTPixels(FFTShift.shift(transformedComplex, paddingWidth, paddingHeight), true),
                0, paddingWidth);
        setTransformedExpression(transformedExpression);

        BufferedImage result = new BufferedImage(imageWidth, imageHeight, imageType);
        int[] inversePixels = new int[imageHeight * imageWidth];
        Complex[][] adjustComplex = new Complex[3][imageHeight * imageWidth];
        for (int i = 0; i < 3; i++) {
            Complex[] complex = fft2D.getInverse(transformedComplex[i]);
            for (int j = 0; j < imageHeight; j++) {
                for (int k = 0; k < imageWidth; k++) {
                    adjustComplex[i][j * imageWidth + k] = complex[j * paddingWidth + k];
                }
            }
        }
        result.setRGB(0, 0, imageWidth, imageHeight,
                FFTUtils.getDisplayFFTPixels(adjustComplex, false), 0, imageWidth);
        setTransformedImage(result);
    }

    public void setTransformedComplex(Complex[][] complex) {
        transformedComplex = complex;
    }

    public void setTransformedImage(BufferedImage image) {
        super.setTransformedImage(image);
        this.transformedImage = image;
    }

    public abstract void calcTransformedImage(Complex[][] pixels);

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
