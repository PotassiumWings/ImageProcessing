package equalize;

import exceptions.FileChooserNotApproveException;
import utils.FrameSettings;
import utils.ImageFileSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class EqualizeFrame extends JFrame {
    private final Frame fatherFrame;
    private final Equalize equalize;

    private static final int BORDER = 40;  // distance between labels and frame border
    private static final int GAP = 10;  // distance between labels

    private static final int W_COUNT = 2;  // label count at width
    private static final int H_COUNT = 2;  // label count at height

    private Button saveButton1;
    private Button saveButton2;
    private Button returnButton;
    private ImageFileSaver fileSaver;

    public EqualizeFrame(Frame fatherFrame, Equalize equalize) {
        FrameSettings.setSize(this);
        FrameSettings.setCenter(this);
        this.setLayout(null);

        this.fatherFrame = fatherFrame;
        this.equalize = equalize;

        this.setTitle("Equalization | Image processing demo");
        this.setResizable(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                closeWindow();
            }
        });
        setFileSaver();

        setLabels();

        setButtons();
        setPanel();

        this.setVisible(true);
    }

    private void setFileSaver() {
        fileSaver = new ImageFileSaver();
    }

    private void setButtons() {
        saveButton1 = new Button("Save gray image");
        saveButton1.setFont(FrameSettings.getButtonFont());
        saveButton1.addActionListener(e -> {
            try {
                saveFile(equalize.getEqualizedImage());
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });

        saveButton2 = new Button("Save equalized image");
        saveButton2.setFont(FrameSettings.getButtonFont());
        saveButton2.addActionListener(e -> {
            try {
                saveFile(equalize.getGrayImage());
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

    private void saveFile(BufferedImage image) throws FileChooserNotApproveException {
        fileSaver.save(image);
    }

    private void setPanel() {
        JLabel label = new JLabel();
        label.setBounds(0, 0, getWidth(), BORDER);
        this.add(label);
        label.add(saveButton1);
        saveButton1.setBounds(getWidth() / 2 - 100, 5, 190, BORDER - 4);
        label.add(saveButton2);
        saveButton2.setBounds(getWidth() / 2 - 330, 5, 220, BORDER - 4);

        label.add(returnButton);
        returnButton.setBounds(getWidth() / 2 + 100, 5, 120, BORDER - 4);
    }

    private void setLabels() {
        JLabel[] labels = new JLabel[4];
        labels[0] = equalize.getGrayImagePanel(this);
        labels[1] = equalize.getEqualizedImagePanel(this);
        labels[2] = equalize.getGrayHistogramPanel(this);
        labels[3] = equalize.getEqualizedHistogramPanel(this);
        System.err.println("frame width: " + getWidth() + ", height: " + getHeight());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int layerWidth = getWidth() - BORDER * 2;
                int gridWidth = layerWidth / W_COUNT;
                int layerHeight = getHeight() - BORDER * 2;
                int gridHeight = layerHeight / H_COUNT;

                int start_x = BORDER + gridWidth * i + GAP;
                int start_y = BORDER + gridHeight * j + GAP;
                int width = gridWidth - GAP * 2;
                int height = gridHeight - GAP * 2;
                labels[i * 2 + j].setBounds(start_x, start_y, width, height);
                System.err.println("! " + (i * 2 + j) + ", " + start_x + ", " + start_y + ", " + width + ", " + height);
                this.add(labels[i * 2 + j]);
            }
        }
    }

    private void closeWindow() {
        fatherFrame.setVisible(true);
        this.dispose();
    }
}
