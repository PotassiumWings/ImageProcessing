package transform;

import exceptions.AttributeOutOfBoundException;
import exceptions.TypeNotSupportedException;
import utils.ColorGetter;

import java.awt.image.BufferedImage;

public class LinearTransform extends Transform {
    private final int imageWidth;
    private final int imageHeight;

    private int a = 0;
    private int b = 255;
    private int c = 0;
    private int d = 255;

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public LinearTransform(BufferedImage image, int width, int height) {
        super(image);
        imageWidth = width;
        imageHeight = height;
    }

    public void updateAttributes(int a, int b, int c, int d)
            throws AttributeOutOfBoundException, TypeNotSupportedException {
        if (!(0 <= a && a < b && b <= 255 &&
                0 <= c && c < d && d <= 255)) {
            throw new AttributeOutOfBoundException(a, b, c, d);
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        // TO-DO: bug?!
        calculate();
        //calculateTransformed();
    }

    public int mapping(int gray) {
        return LinearTransformMapping.linearTransformMapping(a, b, c, d, gray);
    }

    @Override
    public void calcTransformedImage(int[] pixels, int imageType) {
        int[] transformedPixels = new int[imageWidth * imageHeight];
        for (int channel = 0; channel < 3; channel++) {
            for (int i = 0; i < pixels.length; i++) {
                int color = mapping(ColorGetter.getColorValue(pixels[i], channel));
                transformedPixels[i]  |= color << (8 * channel);
            }
        }
        BufferedImage result = new BufferedImage(imageWidth, imageHeight, imageType);
        result.setRGB(0, 0, imageWidth, imageHeight, transformedPixels, 0, imageWidth);

        setTransformedPixels(transformedPixels);
        setTransformedImage(result);
    }
}
