package linear;

import exceptions.AttributeOutOfBoundException;
import exceptions.FileChooserNotApproveException;
import exceptions.TypeNotSupportedException;
import transform.TransformFrame;
import utils.FrameSettings;

import javax.swing.*;
import java.awt.*;

public class LinearTransformFrame extends TransformFrame {
    private JTextField textFieldA;
    private JTextField textFieldB;
    private JTextField textFieldC;
    private JTextField textFieldD;
    private JLabel label;

    private Button submitButton;
    private Button returnButton;

    public LinearTransformFrame(Frame fatherFrame, LinearTransform linearTransform) {
        super(fatherFrame, linearTransform);
    }

    @Override
    public void init() {
        this.setTitle("Linear Transformation | Image processing demo");
        setUpBorder(100);
        textFieldA = new JTextField(4);
        textFieldB = new JTextField(4);
        textFieldC = new JTextField(4);
        textFieldD = new JTextField(4);
    }

    @Override
    public void setUpPanel() {
        setTextField();
        setButtons();
        setPanel();
    }

    public void setTextField() {
        int width = getWidth();
        int upBorder = getUpBorder();
        int textSize = 20;
        textFieldA.setBounds(30, (upBorder / 2 - textSize) / 2, width / 8, textSize);
        textFieldB.setBounds(30, (3 * upBorder / 2 - textSize) / 2, width / 8, textSize);
        textFieldC.setBounds(width / 4 + 30, (upBorder / 2 - textSize) / 2, width / 8, textSize);
        textFieldD.setBounds(width / 4 + 30, (3 * upBorder / 2 - textSize) / 2, width / 8, textSize);
    }

    private int getNum(JTextField textField) throws NumberFormatException {
        String str = textField.getText();
        return Integer.parseInt(str);
    }

    private void setButtons() {
        submitButton = new Button("Submit");
        submitButton.setFont(FrameSettings.getButtonFont());
        submitButton.addActionListener(e -> {
            try {
                int a = getNum(textFieldA);
                int b = getNum(textFieldB);
                int c = getNum(textFieldC);
                int d = getNum(textFieldD);
                ((LinearTransform) (getTransform())).updateAttributes(a, b, c, d);
                repaintComponents();
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            } catch (AttributeOutOfBoundException attributeOutOfBoundException) {
                attributeOutOfBoundException.print();
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        returnButton = new Button("Return");
        returnButton.setFont(FrameSettings.getButtonFont());
        returnButton.addActionListener(e -> {
            closeWindow();
        });
    }

    public void setPanel() {
        label = new JLabel();
        label.setBounds(0, 0, getWidth(), getUpBorder());
        this.add(label);
        label.add(submitButton);
        submitButton.setBounds(getWidth() / 2, (getUpBorder() / 2 - 25) / 2,
                80, 25);

        label.add(returnButton);
        returnButton.setBounds(getWidth() / 2, getUpBorder() - (getUpBorder() / 2 - 25) / 2 - 25,
                80, 25);

        label.add(textFieldA);
        label.add(textFieldB);
        label.add(textFieldC);
        label.add(textFieldD);
    }
}
