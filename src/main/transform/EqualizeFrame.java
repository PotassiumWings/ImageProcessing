package main.transform;

import main.TransformFrame;
import main.exceptions.FileChooserNotApproveException;
import main.utils.FrameSettings;

import javax.swing.JLabel;
import java.awt.Button;
import java.awt.Frame;

public class EqualizeFrame extends TransformFrame {
    private Button saveButton;
    private Button returnButton;

    public EqualizeFrame(Frame fatherFrame, Equalize equalize) {
        super(fatherFrame, equalize);
        setUI();
    }

    @Override
    public void init() {
        this.setTitle("Equalization | " + FrameSettings.getName());
    }

    @Override
    public void setUpPanel() {
        setButtons();
        setPanel();
    }

    @Override
    public void labelsRepaint() {

    }

    public void setButtons() {
        saveButton = new Button("Save equalized image");
        saveButton.setFont(FrameSettings.getButtonFont());
        saveButton.addActionListener(e -> {
            try {
                saveFile(getTransform().getTransformedImage());
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });

        returnButton = new Button("Return");
        returnButton.setFont(FrameSettings.getButtonFont());
        returnButton.addActionListener(e -> {
            closeWindow();
        });
    }

    public void setPanel() {
        JLabel label = new JLabel();
        label.setBounds(0, 0, getWidth(), getUpBorder());
        this.add(label);

//        label.add(saveButton1);
//        saveButton1.setBounds(getWidth() / 2 - 100, 5, 190, getUpBorder() - 4);
        label.add(saveButton);
        saveButton.setBounds(getWidth() / 2 - 330, 5, 220, getUpBorder() - 4);

        label.add(returnButton);
        returnButton.setBounds(getWidth() / 2 + 100, 5, 120, getUpBorder() - 4);
    }
}
