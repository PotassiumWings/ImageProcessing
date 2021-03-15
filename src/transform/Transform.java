package transform;

import utils.Histogram;
import exceptions.TypeNotSupportedException;
import utils.AutoAdjustIcon;
import utils.GrayImageConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Transform {
    private GrayImageConstructor grayImageConstructor;
    private BufferedImage grayImage;
    private BufferedImage transformedImage;

    private final BufferedImage image;

    private int[] rawPixels;
    private int[] transformedPixels;
    private Histogram rawHistogram;
    private Histogram transformedHistogram;

    public Transform(BufferedImage image) {
        this.image = image;
    }

    public void setTransformedPixels(int[] pixels) {
        this.transformedPixels = pixels;
    }

    public void setTransformedImage(BufferedImage image) {
        this.transformedImage = image;
    }

    public void calculate() throws TypeNotSupportedException {
        calculateRaw();
        calculateTransformed();
    }

    private void calculateRaw() throws TypeNotSupportedException {
        // get gray image and rawPixels
        grayImageConstructor = new GrayImageConstructor(image);
        grayImage = grayImageConstructor.getGrayImage();
        rawPixels = grayImageConstructor.getRawPixels();

        // get gray image histogram
        rawHistogram = getHistogram(rawPixels);
        rawHistogram.setString("Before transformation");
    }

    public void calculateTransformed() {
        // get transformed image from gray image and rawPixels
        calcTransformedImage(rawPixels);

        // get transformed image histogram
        transformedHistogram = getHistogram(transformedPixels);
        transformedHistogram.setString("After transformation");
    }

    public BufferedImage getGrayImage() {
        return grayImage;
    }

    public BufferedImage getTransformedImage() {
        return transformedImage;
    }

    public JLabel getGrayImagePanel(Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(grayImage, frame));
        return label;
    }

    public JLabel getTransformedImagePanel(Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(transformedImage, frame));
        return label;
    }

    public JLabel getGrayHistogramPanel() {
        return rawHistogram;
    }

    public JLabel getTransformedHistogramPanel() {
        return transformedHistogram;
    }

    public abstract void calcTransformedImage(int[] pixels);

    private Histogram getHistogram(int[] pixels) {
        return new Histogram(pixels);
    }
}
