package main.transform;

import main.exceptions.TypeNotSupportedException;
import main.utils.AutoAdjustIcon;
import main.utils.FrameSettings;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class TransformLaunchFrame extends JFrame {
    private JFrame fatherFrame;

    private BufferedImage image;
    private JLabel imageLabel = null;

    private Equalize equalize;
    private LinearTransform linearTransform;
    private SeparatedLinearTransform separatedLinearTransform;

    private Button equalizationButton;
    private Button linearTransformButton;
    private Button separateLinearTransformButton;
    private Button returnButton;

    public TransformLaunchFrame(JFrame fatherFrame, BufferedImage image, int imageWidth, int imageHeight) {
        this.fatherFrame = fatherFrame;
        this.image = image;
        equalize = new Equalize(image, imageWidth, imageHeight);
        linearTransform = new LinearTransform(image, imageWidth, imageHeight);
        separatedLinearTransform =
                new SeparatedLinearTransform(image, imageWidth, imageHeight);

        FrameSettings.setSize(this);
        FrameSettings.setCenter(this);
        setButtons();
        setPanelAndImage(image);

        this.setTitle("Transform without FFT | " + FrameSettings.getName());
        this.setResizable(true);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeWindow();
            }
        });
    }

    private void closeWindow() {
        fatherFrame.setVisible(true);
        this.dispose();
    }

    private void setButtons() {
        equalizationButton = new Button("Equalize");
        equalizationButton.setFont(FrameSettings.getButtonFont());
        equalizationButton.addActionListener(e -> {
            equalize.calculate();
            new EqualizeFrame(this, equalize);
            this.setVisible(false);
        });

        linearTransformButton = new Button("Linear Transform");
        linearTransformButton.setFont(FrameSettings.getButtonFont());
        linearTransformButton.addActionListener(e -> {
            linearTransform.calculate();
            new LinearTransformFrame(this, linearTransform);
            this.setVisible(false);
        });

        separateLinearTransformButton = new Button("Sep.Linear Transform");
        separateLinearTransformButton.setFont(FrameSettings.getButtonFont());
        separateLinearTransformButton.addActionListener(e -> {
            separatedLinearTransform.calculate();
            new SeparatedLinearTransformFrame(this, separatedLinearTransform);
            this.setVisible(false);
        });

        returnButton = new Button("Return");
        returnButton.setFont(FrameSettings.getButtonFont());
        returnButton.addActionListener(e -> {
            closeWindow();
        });
    }

    private void setPanelAndImage(BufferedImage image) {
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.NORTH);
        panel.add(equalizationButton);
        panel.add(linearTransformButton);
        panel.add(separateLinearTransformButton);
        panel.add(returnButton);

        imageLabel = new JLabel();
        this.add(imageLabel);
        imageLabel.setIcon(AutoAdjustIcon.getAutoAdjustIcon(image, this));
        imageLabel.revalidate();
    }
}
