package main;

import main.exceptions.FileChooserNotApproveException;
import main.utils.FrameSettings;
import main.utils.Histogram;
import main.utils.ImageFileSaver;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public abstract class TransformFrame extends JFrame {
//    UpPanel
//
//    |--------------------------------------------------------------------|
//    |                              |                                     |
//    |                              |      red            green           |
//    |                              |                                     |
//    |          rawImage            |                                     |
//    |                              |     blue          rawHistogram      |
//    |                              |                                     |
//    |                              |                                     |
//    |--------------------------------------------------------------------|
//    |                              |                                     |
//    |                              |                                     |
//    |          transformed         |             (gray)                  |
//    |            Image             |                                     |
//    |                              |         transformedHistogram        |
//    |                              |                                     |
//    |                              |                                     |
//    |--------------------------------------------------------------------|
    private final Frame fatherFrame;
    private final Transform transform;
    private ImageFileSaver fileSaver;

    private int upBorder = 40;

    private static final int BORDER = 40;  // distance between labels and frame border
    private static final int GAP = 10;  // distance between labels

    private static final int W_COUNT = 2;  // label count at width
    private static final int H_COUNT = 2;  // label count at height

    private final JLabel[] labels = new JLabel[4];

    public Transform getTransform() {
        return transform;
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
    }

    public void setUI() {
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
        labels[0] = transform.getOriginImagePanel(this);
        labels[1] = transform.getTransformedImagePanel(this);
        labels[2] = transform.getOriginExpressionPanel(this);
        labels[3] = transform.getTransformedExpressionPanel(this);
        System.err.println("frame width: " + getWidth() + ", height: " + getHeight());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int layerWidth = getWidth() - BORDER * 2;
                int gridWidth = layerWidth / W_COUNT;
                int layerHeight = getHeight() - BORDER - upBorder;
                int gridHeight = layerHeight / H_COUNT;

                int startX = BORDER + gridWidth * i + GAP;
                int startY = upBorder + gridHeight * j + GAP;
                int width = gridWidth - GAP * 2;
                int height = gridHeight - GAP * 2;
                labels[i * 2 + j].setBounds(startX, startY, width, height);
                this.add(labels[i * 2 + j]);
            }
        }
    }

    public void repaintComponents(boolean isHistogram) {
        if (isHistogram) {
            ((Histogram) (labels[3])).delete();
        }
        for (int i = 0; i < 4; i++) {
            labels[i].setVisible(false);
        }
        labelsRepaint();
        setLabels();
    }

    public abstract void labelsRepaint();

    public void closeWindow() {
        fatherFrame.setVisible(true);
        this.dispose();
    }
}
