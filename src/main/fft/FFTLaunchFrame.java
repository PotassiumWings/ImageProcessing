package main.fft;

import main.TransformFrame;
import main.utils.FrameSettings;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Button;

public class FFTLaunchFrame extends TransformFrame {
    private Button actButton;
    private Button returnButton;

    public FFTLaunchFrame(JFrame fatherFrame, FourierTransform fourierTransform) {
        super(fatherFrame, fourierTransform);
        setUI();
    }

    public void init() {
        this.setTitle("FFT | " + FrameSettings.getName());
    }

    public void setUpPanel() {
        setButtons();
        setPanel();
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
        JLabel label = new JLabel();
        label.setBounds(0, 0, getWidth(), getUpBorder());
        this.add(label);

        label.add(actButton);
        actButton.setBounds(getWidth() / 2 - 330, 5, 220, getUpBorder() - 4);

        label.add(returnButton);
        returnButton.setBounds(getWidth() / 2 + 100, 5, 120, getUpBorder() - 4);
    }
}
