package main.transform;

import main.Transform;

import java.awt.image.BufferedImage;

public abstract class TransformWithHistogram extends Transform {
    private BufferedImage image;
    private BufferedImage transformedImage;

    private int[] rawPixels;
    private int[] transformedPixels;

    public TransformWithHistogram(BufferedImage image) {
        super(image);
        this.image = image;
    }

    public void setTransformedImage(BufferedImage image) {
        super.setTransformedImage(image);
        this.transformedImage = image;
    }

    public void calculate() {
        getRawPixels();
        setRawExpression(getHistogram(rawPixels, image.getType(), "Before transformation"));

        calcTransformedImage(rawPixels, image.getType());
        setTransformedExpression(getHistogram(transformedPixels, transformedImage.getType(), "After transformation"));
    }

    public void setTransformedPixels(int[] pixels) {
        this.transformedPixels = pixels;
    }

    private void getRawPixels() {
        rawPixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                rawPixels, 0, image.getWidth());
    }
}
