package transform;

import equalize.Equalize;
import exceptions.FileChooserNotApproveException;
import utils.FrameSettings;
import utils.Histogram;
import utils.ImageFileSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public abstract class TransformFrame extends JFrame {
    private final Frame fatherFrame;
    private final Transform transform;
    private ImageFileSaver fileSaver;

    private int upBorder = 40;

    private static final int BORDER = 40;  // distance between labels and frame border
    private static final int GAP = 10;  // distance between labels

    private static final int W_COUNT = 2;  // label count at width
    private static final int H_COUNT = 2;  // label count at height

    private JLabel[] labels = new JLabel[4];

    public Transform getTransform() {
        return transform;
    }

    public int getBorder() {
        return BORDER;
    }

    public int getUpBorder() {
        return upBorder;
    }

    public void setUpBorder(int x) {
        upBorder = x;
    }

    public TransformFrame(Frame fatherFrame, Transform transform) {
        FrameSettings.setSize(this);
        FrameSettings.setCenter(this);
        this.setLayout(null);

        this.fatherFrame = fatherFrame;
        this.transform = transform;

        init();

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

        setUpPanel();

        this.setVisible(true);
    }

    public abstract void init();

    public void setFileSaver() {
        fileSaver = new ImageFileSaver();
    }

    public void saveFile(BufferedImage image) throws FileChooserNotApproveException {
        fileSaver.save(image);
    }

    public abstract void setUpPanel();

    private void setLabels() {
        labels[0] = transform.getGrayImagePanel(this);
        labels[1] = transform.getTransformedImagePanel(this);
        labels[2] = transform.getGrayHistogramPanel();
        labels[3] = transform.getTransformedHistogramPanel();
        System.err.println("frame width: " + getWidth() + ", height: " + getHeight());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int layerWidth = getWidth() - BORDER * 2;
                int gridWidth = layerWidth / W_COUNT;
                int layerHeight = getHeight() - BORDER - upBorder;
                int gridHeight = layerHeight / H_COUNT;

                int start_x = BORDER + gridWidth * i + GAP;
                int start_y = upBorder + gridHeight * j + GAP;
                int width = gridWidth - GAP * 2;
                int height = gridHeight - GAP * 2;
                labels[i * 2 + j].setBounds(start_x, start_y, width, height);
                System.err.println("! " + (i * 2 + j) + ", " + start_x + ", " + start_y + ", " + width + ", " + height);
                this.add(labels[i * 2 + j]);
            }
        }
    }

    public void repaintComponents() {
        ((Histogram) (labels[3])).delete();
        for (int i = 0; i < 4; i++) {
            labels[i].setVisible(false);
        }
        setLabels();
    }

    public void closeWindow() {
        fatherFrame.setVisible(true);
        this.dispose();
    }
}