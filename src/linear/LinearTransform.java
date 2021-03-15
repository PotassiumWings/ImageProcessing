package linear;

import exceptions.AttributeOutOfBoundException;
import exceptions.TypeNotSupportedException;
import transform.Transform;

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

    public void updateAttributes(int a, int b, int c, int d) throws AttributeOutOfBoundException, TypeNotSupportedException {
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
//        calculateTransformed();
    }

    public int mapping(int gray) {
        int res;
        if (gray < a) {
            res = c;
        } else if(gray > b) {
            res = d;
        } else {
            res = (gray - a) * (d - c) / (b - a) + c;
        }
        return res;
    }

    @Override
    public void calcTransformedImage(int[] pixels) {
        int[] transformedPixels = new int[imageWidth * imageHeight];

        for (int i = 0; i < pixels.length; i++) {
            int gray = pixels[i] & 0xff;
            int newGray = mapping(gray);
            transformedPixels[i] = newGray << 16 | newGray << 8 | newGray;
        }

        BufferedImage result = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        result.setRGB(0, 0, imageWidth, imageHeight, transformedPixels, 0, imageWidth);

        setTransformedPixels(transformedPixels);
        setTransformedImage(result);
    }
}
