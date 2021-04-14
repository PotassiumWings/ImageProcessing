package main;

import main.utils.AutoAdjustIcon;
import main.utils.Histogram;

import javax.swing.JLabel;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public abstract class Transform {
    private BufferedImage transformedImage;

    private final BufferedImage image;
    private JLabel rawExpression;
    private JLabel transformedExpression;

    private BufferedImage rawExpressionImage;
    private BufferedImage transformedExpressionImage;

    public Transform(BufferedImage image) {
        this.image = image;
    }

    public abstract void calculate();

    public BufferedImage getTransformedImage() {
        return transformedImage;
    }

//    four essential functions to show the images/histograms
//   |------------------------|--------------------------------------|
//   | originImagePanel       |        originExpressionPanel         |
//   |------------------------|--------------------------------------|
//   | transformedImagePanel  |        transformedExpressionPanel    |
//   |------------------------|--------------------------------------|

    public JLabel getOriginImagePanel(Frame frame) {
        return getLabelFromImageAndFrame(image, frame);
    }

    public JLabel getTransformedImagePanel(Frame frame) {
        return getLabelFromImageAndFrame(transformedImage, frame);
    }

    public JLabel getOriginExpressionPanel(Frame frame) {
        if (rawExpression != null) {
            return rawExpression;
        }
        return getLabelFromImageAndFrame(rawExpressionImage, frame);
    }

    public JLabel getTransformedExpressionPanel(Frame frame) {
        if (transformedExpression != null) {
            return transformedExpression;
        }
        return getLabelFromImageAndFrame(transformedExpressionImage, frame);
    }

    private JLabel getLabelFromImageAndFrame(BufferedImage image, Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(image, frame));
        return label;
    }

    public void setRawExpression(JLabel label) {
        rawExpression = label;
    }

    public void setTransformedExpression(JLabel label) {
        transformedExpression = label;
    }

    public void setRawExpression(BufferedImage image) {
        rawExpressionImage = image;
    }

    public void setTransformedExpression(BufferedImage image) {
        transformedExpressionImage = image;
    }

    public BufferedImage getRawExpressionImage() {
        return rawExpressionImage;
    }

    public BufferedImage getTransformedExpressionImage() {
        return transformedExpressionImage;
    }

    public void setTransformedImage(BufferedImage image) {
        this.transformedImage = image;
    }

    public abstract void calcTransformedImage(int[] pixels, int imageType);

    public JLabel getHistogram(int[] pixels, int imageType, String name) {
        return new Histogram(pixels, imageType, name);
    }
}
