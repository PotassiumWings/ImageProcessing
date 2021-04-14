package main.fft;

import java.awt.image.BufferedImage;

public class FourierTransform extends TransformWithFFT {
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

    // TODO: override expression
}
