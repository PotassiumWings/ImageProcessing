package equalize;

import exceptions.FileChooserNotApproveException;
import transform.TransformFrame;
import utils.FrameSettings;

import javax.swing.*;
import java.awt.*;

public class EqualizeFrame extends TransformFrame {
    private Button saveButton1;
    private Button saveButton2;
    private Button returnButton;

    public EqualizeFrame(Frame fatherFrame, Equalize equalize) {
        super(fatherFrame, equalize);
    }

    @Override
    public void init() {
        this.setTitle("Equalization | Image processing demo");
    }

    @Override
    public void setUpPanel() {
        setButtons();
        setPanel();
    }

    public void setButtons() {
        saveButton1 = new Button("Save gray image");
        saveButton1.setFont(FrameSettings.getButtonFont());
        saveButton1.addActionListener(e -> {
            try {
                saveFile(getTransform().getTransformedImage());
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });

        saveButton2 = new Button("Save equalized image");
        saveButton2.setFont(FrameSettings.getButtonFont());
        saveButton2.addActionListener(e -> {
            try {
                saveFile(getTransform().getGrayImage());
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
        label.add(saveButton1);
        saveButton1.setBounds(getWidth() / 2 - 100, 5, 190, getUpBorder() - 4);
        label.add(saveButton2);
        saveButton2.setBounds(getWidth() / 2 - 330, 5, 220, getUpBorder() - 4);

        label.add(returnButton);
        returnButton.setBounds(getWidth() / 2 + 100, 5, 120, getUpBorder() - 4);
    }
}
