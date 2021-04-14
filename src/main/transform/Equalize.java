package main.transform;

import main.Transform;
import main.utils.ColorGetter;

import java.awt.image.BufferedImage;

public class Equalize extends TransformWithHistogram {
    private final int imageWidth;
    private final int imageHeight;

    public Equalize(BufferedImage image, int width, int height) {
        super(image);
        this.imageWidth = width;
        this.imageHeight = height;
    }

    @Override
    public void calcTransformedImage(int[] pixels, int imageType) {
        int[] transformedPixels = new int[imageWidth * imageHeight];
        for (int channel = 0; channel < 3; channel++) {
            int[] colorArray = new int[256];
            int[] prefixSum = new int[256];
            for (int pixel : pixels) {
                int color = ColorGetter.getColorValue(pixel, channel);
                colorArray[color]++;
            }
            prefixSum[0] = colorArray[0];
            for (int i = 1; i < 256; i++) {
                prefixSum[i] = prefixSum[i - 1] + colorArray[i];
            }

            int[] convert = new int[256];
            for (int i = 0; i < 256; i++) {
                convert[i] = (int) (prefixSum[i] * 255.0 / imageWidth / imageHeight);
            }

            for (int i = 0; i < pixels.length; i++) {
                int color = convert[ColorGetter.getColorValue(pixels[i], channel)];
                transformedPixels[i]  |= color << (8 * channel);
            }
        }
        BufferedImage result = new BufferedImage(imageWidth, imageHeight, imageType);
        result.setRGB(0, 0, imageWidth, imageHeight, transformedPixels, 0, imageWidth);

        setTransformedPixels(transformedPixels);
        setTransformedImage(result);
    }
}
