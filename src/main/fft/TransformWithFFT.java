package main.fft;

import main.Transform;

import java.awt.image.BufferedImage;

public abstract class TransformWithFFT extends Transform {
    public TransformWithFFT(BufferedImage image) {
        super(image);
    }

    public abstract void calculate();

    public abstract void calcTransformedImage(int[] pixels, int imageType);
}
