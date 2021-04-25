package main.fft;

import main.TransformFrame;
import main.exceptions.FileChooserNotApproveException;
import main.utils.FrameSettings;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Button;
import java.util.ArrayList;
import java.util.Arrays;

public class FFTLaunchFrame extends TransformFrame {
    private Button actButton;
    private Button returnButton;
    private Button reverseButton;
    private Button gaussianButton;
    private Button waveButton;

    private Button saveFFT1Button;
    private Button saveFFT2Button;
    private Button saveButton;

    private JLabel labelRadius;

    private JTextField textField;

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
            double p = -1;
            try {
                p = Double.parseDouble(textField.getText());
            } catch (Exception ignored){
                ;
            }
            if (p >= 0 && p <= 1) {
                ((TransformWithFFT) getTransform()).setRadius(p);
            }
            ((TransformWithFFT) getTransform()).recalculate();
            repaintComponents(false);
        });

        waveButton = new Button("Wave!");
        waveButton.setFont(FrameSettings.getButtonFont());
        waveButton.addActionListener(e -> {
            double p = -1;
            try {
                p = Double.parseDouble(textField.getText());
            } catch (Exception ignored) {
                ;
            }
            if (p >= 0 && p <= 1) {
                ((TransformWithFFT) getTransform()).setRadius(p);
            }
            ((TransformWithFFT) getTransform()).waveFilter();
            repaintComponents(false);
        });

        gaussianButton = new Button("Gau Fil");
        gaussianButton.setFont(FrameSettings.getButtonFont());
        gaussianButton.addActionListener(e -> {
            double p = -1;
            int len = 3;
            try {
                ArrayList<String> params = new ArrayList<String>(Arrays.asList(textField.getText().split("[ \t,/|]")));
                params.removeIf(e2 -> e2.equals(""));
                String[] strings = params.toArray(new String[0]);
                p = Double.parseDouble(strings[0]);
                len = Integer.parseInt(strings[1]);
                System.err.println("p: " + p + ", len: " + len);
            } catch (Exception ignored) {
                ;
            }
            if (p >= 0) {
                ((TransformWithFFT) getTransform()).setRadius(p);
                ((TransformWithFFT) getTransform()).setGaussianLength(len);
            }
            ((TransformWithFFT) getTransform()).gaussianFilter();
            repaintComponents(false);
        });

        reverseButton = new Button("R");
        reverseButton.setFont(FrameSettings.getButtonFont());
        reverseButton.addActionListener(e -> {
            double p = -1;
            try {
                p = Double.parseDouble(textField.getText());
            } catch (Exception ignored) {
                ;
            }
            if (p >= 0 && p <= 1) {
                ((TransformWithFFT) getTransform()).setRadius(p);
            } else {
                ((TransformWithFFT) getTransform()).setRadius(0.2);
            }
            ((TransformWithFFT) getTransform()).flip();
            ((TransformWithFFT) getTransform()).recalculate();
            repaintComponents(false);
        });

        returnButton = new Button("Return");
        returnButton.setFont(FrameSettings.getButtonFont());
        returnButton.addActionListener(e -> {
            closeWindow();
        });

        saveFFT1Button = new Button("FFT1");
        saveFFT1Button.setFont(FrameSettings.getButtonFont());
        saveFFT1Button.addActionListener(e -> {
            try {
                saveFile(getTransform().getRawExpressionImage());
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });

        saveFFT2Button = new Button("FFT2");
        saveFFT2Button.setFont(FrameSettings.getButtonFont());
        saveFFT2Button.addActionListener(e -> {
            try {
                saveFile(getTransform().getTransformedExpressionImage());
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });

        saveButton = new Button("Save");
        saveButton.setFont(FrameSettings.getButtonFont());
        saveButton.addActionListener(e -> {
            try {
                saveFile(getTransform().getTransformedImage());
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });
    }

    private void setPanel() {
        JLabel label = new JLabel();
        label.setBounds(0, 0, getWidth(), getUpBorder());
        this.add(label);

        labelRadius = new JLabel("Paras:");
        textField = new JTextField(4);

        label.add(labelRadius);
        labelRadius.setBounds(50, 5, 60, getUpBorder() - 4);

        label.add(textField);
        textField.setBounds(100, 7, 50, getUpBorder() - 8);

        label.add(reverseButton);
        reverseButton.setBounds(215, 5, 40, getUpBorder() - 4);

        label.add(gaussianButton);
        gaussianButton.setBounds(260, 5, 70, getUpBorder() - 4);

        label.add(waveButton);
        waveButton.setBounds(335, 5, 70, getUpBorder() - 4);

        label.add(actButton);
        actButton.setBounds(410, 5, 140, getUpBorder() - 4);

        label.add(saveFFT1Button);
        saveFFT1Button.setBounds(555, 5, 60, getUpBorder() - 4);

        label.add(saveFFT2Button);
        saveFFT2Button.setBounds(620, 5, 60, getUpBorder() - 4);

        label.add(saveButton);
        saveButton.setBounds(685, 5, 70, getUpBorder() - 4);

        label.add(returnButton);
        returnButton.setBounds(760, 5, 100, getUpBorder() - 4);
    }
}
