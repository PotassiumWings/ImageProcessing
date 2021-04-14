package main;

import main.utils.Histogram;
import main.exceptions.TypeNotSupportedException;
import main.utils.AutoAdjustIcon;
import main.utils.GrayImageConstructor;

import javax.swing.JLabel;
import java.awt.Frame;
import java.awt.image.BufferedImage;

public abstract class Transform {
    private BufferedImage transformedImage;

    private final BufferedImage image;
    private JLabel rawExpression;
    private JLabel transformedExpression;

    public Transform(BufferedImage image) {
        this.image = image;
    }

    public abstract void calculate();

    public BufferedImage getTransformedImage() {
        return transformedImage;
    }

    public JLabel getOriginImagePanel(Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(image, frame));
        return label;
    }

    public JLabel getTransformedImagePanel(Frame frame) {
        JLabel label = new JLabel();
        label.setIcon(AutoAdjustIcon.getAutoAdjustIcon(transformedImage, frame));
        return label;
    }

    public JLabel getOriginExpressionPanel() {
        return rawExpression;
    }

    public JLabel getTransformedExpressionPanel() {
        return transformedExpression;
    }

    public void setRawExpression(JLabel label) {
        rawExpression = label;
    }

    public void setTransformedExpression(JLabel label) {
        transformedExpression = label;
    }

    public void setTransformedImage(BufferedImage image) {
        this.transformedImage = image;
    }

    public abstract void calcTransformedImage(int[] pixels, int imageType);

    public JLabel getHistogram(int[] pixels, int imageType, String name) {
        return new Histogram(pixels, imageType, name);
    }
}
