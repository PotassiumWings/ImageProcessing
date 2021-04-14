package main.fft;

import java.awt.image.BufferedImage;

public class FourierTransform extends TransformWithFFT {
    public FourierTransform(BufferedImage image) {
        super(image);
    }

    @Override
    public void calcTransformedImage(Complex[][] pixels) {
        setTransformedComplex(pixels);
    }

    @Override
    public void calcTransformedImage(int[] pixels, int imageType) {

    }

    // TODO: override expression
}
