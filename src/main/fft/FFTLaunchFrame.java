package main.fft;

import main.TransformFrame;
import main.utils.FrameSettings;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class FFTLaunchFrame extends TransformFrame {
    private JFrame fatherFrame;
    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;
    private FourierTransform fourierTransform;

    private Button actButton;
    private Button returnButton;

    public FFTLaunchFrame(JFrame fatherFrame, FourierTransform fourierTransform,
                          BufferedImage image, int imageWidth, int imageHeight) {
        super(fatherFrame, fourierTransform);
        this.fourierTransform = fourierTransform;

        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        FrameSettings.setSize(this);
        FrameSettings.setCenter(this);
        setButtons();
        setPanel();

        this.setTitle(FrameSettings.getName());
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

    public void init() {
        this.setTitle("FFT | " + FrameSettings.getName());
    }

    public void setUpPanel() {

    }

    public void labelsRepaint() {

    }

    private void setButtons() {
        actButton = new Button("Calculate FFT");
        actButton.setFont(FrameSettings.getButtonFont());
        actButton.addActionListener(e -> {
            // TODO
        });

        returnButton = new Button("Return");
        returnButton.setFont(FrameSettings.getButtonFont());
        returnButton.addActionListener(e -> {
            closeWindow();
        });
    }

    private void setPanel() {
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.NORTH);
        panel.add(actButton);
        panel.add(returnButton);
    }
}
