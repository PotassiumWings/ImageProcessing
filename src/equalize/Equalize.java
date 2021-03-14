package equalize;

import exceptions.TypeNotSupportedException;
import utils.AutoAdjustIcon;
import utils.GrayImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Equalize {
    GrayImage grayImageConstructor;
    private BufferedImage grayImage;
    private BufferedImage equalizedImage;

    private final BufferedImage image;
    private final int imageHeight;
    private final int imageWidth;

    private int[] equalizedPixels;
    private Histogram rawHistogram;
    private Histogram equalizedHistogram;

    public Equalize(BufferedImage image, int width, int height) {
        this.image = image;
        this.imageWidth = width;
        this.imageHeight = height;
    }

    public void calculate() throws IOException, TypeNotSupportedException {
        // get gray image and rawPixels
        grayImageConstructor = new GrayImage(image);
        grayImage = grayImageConstructor.getGrayImage();
        int[] rawPixels = grayImageConstructor.getRawPixels();

        // get gray image histogram
        rawHistogram = getHistogram(rawPixels);
        rawHistogram.setString("Before equalization");

        // get equalized image from gray image and rawPixels
        equalizedImage = getEqualizedImage(rawPixels);

        // get equalized image histogram
        equalizedHistogram = getHistogram(equalizedPixels);
        equalizedHistogram.setString("After equalization");
    }

    public BufferedImage getGrayImage() {
        return grayImage;
    }

    public BufferedImage getEqualizedImage() {
        return equalizedImage;
    }

    public JLabel getGrayImagePanel(Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(grayImage, frame));
        return label;
    }

    public JLabel getEqualizedImagePanel(Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(equalizedImage, frame));
        return label;
    }

    public JLabel getGrayHistogramPanel(Frame frame) {
        return rawHistogram;
    }

    public JLabel getEqualizedHistogramPanel(Frame frame) {
        return equalizedHistogram;
    }

    private BufferedImage getEqualizedImage(int[] pixels) {
        equalizedPixels = new int[imageWidth * imageHeight];

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
            equalizedPixels[i] = gray << 16 | gray << 8 | gray;
        }

        BufferedImage result = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        result.setRGB(0, 0, imageWidth, imageHeight, equalizedPixels, 0, imageWidth);

        return result;
    }

    private Histogram getHistogram(int[] pixels) {
        return new Histogram(pixels);
    }
}
