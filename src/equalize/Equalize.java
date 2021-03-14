package equalize;

import exceptions.TypeNotSupportedException;
import transform.Transform;
import utils.AutoAdjustIcon;
import utils.GrayImageConstructor;
import utils.Histogram;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Equalize extends Transform {
    private final int imageWidth;
    private final int imageHeight;

    public Equalize(BufferedImage image, int width, int height) {
        super(image);
        this.imageWidth = width;
        this.imageHeight = height;
    }

    @Override
    public void calcTransformedImage(int[] pixels) {
        int[] transformedPixels = new int[imageWidth * imageHeight];

        int[] grayArray = new int[256];
        int[] prefixSum = new int[256];
        for (int pixel : pixels) {
            int gray = pixel & 0xff;
            grayArray[gray]++;
        }
        prefixSum[0] = grayArray[0];
        for (int i = 1; i < 256; i++) {
            prefixSum[i] = prefixSum[i - 1] + grayArray[i];
        }

        int[] convert = new int[256];
        for (int i = 0; i < 256; i++) {
            convert[i] = (int) (prefixSum[i] * 255.0 / imageWidth / imageHeight);
        }

        for (int i = 0; i < pixels.length; i++) {
            int gray = convert[pixels[i] & 0xff];
            transformedPixels[i] = gray << 16 | gray << 8 | gray;
        }

        BufferedImage result = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        result.setRGB(0, 0, imageWidth, imageHeight, transformedPixels, 0, imageWidth);

        setTransformedPixels(transformedPixels);
        setTransformedImage(result);
    }
}
