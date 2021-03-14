package utils;

import exceptions.TypeNotSupportedException;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class GrayImageConstructor {
    private BufferedImage image;
    private final int imageWidth;
    private final int imageHeight;
    private BufferedImage grayImage;

    private int[] rawPixels;

    public GrayImageConstructor(BufferedImage image) throws TypeNotSupportedException {
        this.image = image;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        this.grayImage = calcGrayImage();
    }

    public BufferedImage getGrayImage() {
        return grayImage;
    }

    public int[] getRawPixels() {
        return rawPixels;
    }

    private BufferedImage calcGrayImage() throws TypeNotSupportedException {
        ArrayList<Integer> supportedType = new ArrayList<>(
                Arrays.asList(BufferedImage.TYPE_3BYTE_BGR, BufferedImage.TYPE_BYTE_GRAY)
        );
        boolean ok = false;
        for (Integer type: supportedType) {
            if (image.getType() == type) {
                ok = true;
            }
        }
        if (!ok) {
            throw new TypeNotSupportedException(image.getType());
        }

        int[] coloredPixels = new int[imageWidth * imageHeight];
        rawPixels = new int[imageWidth * imageHeight];

        coloredPixels = image.getRGB(0, 0, imageWidth, imageHeight,
                coloredPixels, 0, imageWidth);

        for (int i = 0; i < coloredPixels.length; i++) {
            int val = 0;
            if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
                int bgr = coloredPixels[i];
                int blue_part = bgr & 0xff0000 >> 16;
                int green_part = bgr & 0x00ff00 >> 8;
                int red_part = bgr & 0x0000ff;
                int gray = (int) (0.299 * red_part + 0.587 * green_part + 0.114 * blue_part);
                val = gray << 16 | gray << 8 | gray;
            } else if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
                int bgr = coloredPixels[i];
                int gray = bgr & 0xff0000 >> 16;
                assert((bgr & 0x00ff00 >> 8) == gray);
                assert((bgr & 0x0000ff) == gray);
                val = gray << 16 | gray << 8 | gray;
            }
            rawPixels[i] = val;
        }

        BufferedImage result = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        result.setRGB(0, 0, imageWidth, imageHeight, rawPixels, 0, imageWidth);

        return result;
    }
}
