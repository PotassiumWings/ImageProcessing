package transform;

import utils.Histogram;
import exceptions.TypeNotSupportedException;
import utils.AutoAdjustIcon;
import utils.GrayImageConstructor;

import javax.swing.JLabel;
import java.awt.Frame;
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

    private void calculateRaw() {
        // get gray image and rawPixels
        // grayImageConstructor = new GrayImageConstructor(image);
        // grayImageConstructor.getGrayImage();
        // rawPixels = grayImageConstructor.getRawPixels();

        grayImage = image;
        getRawPixels();

        // get gray image histogram
        rawHistogram = getHistogram(rawPixels, image.getType());
        rawHistogram.setString("Before transformation");
    }

    private void getRawPixels() {
        rawPixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                rawPixels, 0, image.getWidth());
    }

    public void calculateTransformed() {
        // get transformed image from gray image and rawPixels
        calcTransformedImage(rawPixels, image.getType());

        // get transformed image histogram
        transformedHistogram = getHistogram(transformedPixels, transformedImage.getType());
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

    public abstract void calcTransformedImage(int[] pixels, int imageType);

    private Histogram getHistogram(int[] pixels, int imageType) {
        return new Histogram(pixels, imageType);
    }
}
