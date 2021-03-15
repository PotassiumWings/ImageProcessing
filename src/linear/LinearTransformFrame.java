package linear;

import exceptions.AttributeOutOfBoundException;
import exceptions.FileChooserNotApproveException;
import exceptions.TypeNotSupportedException;
import transform.TransformFrame;
import utils.FrameSettings;
import utils.LineChart;

import javax.swing.*;
import java.awt.*;

public class LinearTransformFrame extends TransformFrame {
    private JTextField textFieldA;
    private JTextField textFieldB;
    private JTextField textFieldC;
    private JTextField textFieldD;

    private JLabel labelA;
    private JLabel labelB;
    private JLabel labelC;
    private JLabel labelD;

    private Button submitButton;
    private Button returnButton;
    private Button saveButton;

    private JLabel lineChart;

    private boolean isSeperate = false;

    private int a = 0;
    private int b = 255;
    private int c = 0;
    private int d = 255;

    public LinearTransformFrame(Frame fatherFrame, LinearTransform linearTransform, boolean isSeperate) {
        super(fatherFrame, linearTransform);
        this.isSeperate = isSeperate;
    }

    @Override
    public void init() {
        this.setTitle("Linear Transformation | Image processing demo");
        setUpBorder(100);
        textFieldA = new JTextField(4);
        textFieldB = new JTextField(4);
        textFieldC = new JTextField(4);
        textFieldD = new JTextField(4);
        labelA = new JLabel("a:");
        labelB = new JLabel("b:");
        labelC = new JLabel("c:");
        labelD = new JLabel("d:");
    }

    @Override
    public void setUpPanel() {
        lineChart = new LineChart(0, 255, 0, 255, !isSeperate, "");
        setTextField();
        setButtons();
        setLabels();
    }

    public void setTextField() {
        int width = getWidth();
        int upBorder = getUpBorder();
        int textSize = 20;
        labelA.setBounds(50, (upBorder / 2 - textSize) / 2, 50, textSize);
        textFieldA.setBounds(80, (upBorder / 2 - textSize) / 2, width / 8, textSize);
        labelB.setBounds(50, (3 * upBorder / 2 - textSize) / 2, 50, textSize);
        textFieldB.setBounds(80, (3 * upBorder / 2 - textSize) / 2, width / 8, textSize);
        labelC.setBounds(width / 4 + 50, (upBorder / 2 - textSize) / 2, 50, textSize);
        textFieldC.setBounds(width / 4 + 80, (upBorder / 2 - textSize) / 2, width / 8, textSize);
        labelD.setBounds(width / 4 + 50, (3 * upBorder / 2 - textSize) / 2, 50, textSize);
        textFieldD.setBounds(width / 4 + 80, (3 * upBorder / 2 - textSize) / 2, width / 8, textSize);

        lineChart.setBounds(width / 2 + 100, 0, width / 2 - 100, upBorder * 3 / 2);
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
                this.a = a;
                this.b = b;
                this.c = c;
                this.d = d;
                repaintComponents();
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            } catch (AttributeOutOfBoundException attributeOutOfBoundException) {
                attributeOutOfBoundException.print();
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        saveButton = new Button("Save image");
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

    public void labelsRepaint(){
        int width = getWidth();
        int upBorder = getUpBorder();
        if (lineChart != null) {
            lineChart.setVisible(false);
        }
        lineChart = new LineChart(a, b, c, d, !isSeperate, "");
        lineChart.setBounds(width / 2 + 100, 0, width / 2 - 100, upBorder * 3 / 2);
        lineChart.repaint();
        this.add(lineChart);
    }

    public void setLabels() {
        this.add(textFieldA);
        this.add(textFieldB);
        this.add(textFieldC);
        this.add(textFieldD);
        this.add(labelA);
        this.add(labelB);
        this.add(labelC);
        this.add(labelD);

        this.add(submitButton);
        submitButton.setBounds(getWidth() / 2, getUpBorder() / 3 - 25,
                80, 25);

        this.add(returnButton);
        returnButton.setBounds(getWidth() / 2, 2 * getUpBorder() / 3 - 25,
                80, 25);

        this.add(saveButton);
        saveButton.setBounds(getWidth() / 2 - 25, getUpBorder() - 25,
                80 + 25 * 2, 25);

        this.add(lineChart);
    }
}
