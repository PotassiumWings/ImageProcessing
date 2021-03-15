import equalize.Equalize;
import equalize.EqualizeFrame;
import exceptions.FileChooserNotApproveException;
import exceptions.TypeNotSupportedException;
import linear.LinearTransform;
import linear.LinearTransformFrame;
import linear.SeperatedLinearTransform;
import linear.SeperatedLinearTransformFrame;
import utils.FrameSettings;
import utils.ImageFileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LaunchFrame extends Frame {
    private String imagePath;
    private BufferedImage image;

    private Equalize equalize;
    private LinearTransform linearTransform;
    private SeperatedLinearTransform seperatedLinearTransform;

    private int imageHeight;
    private int imageWidth;

    private ImageFileChooser fileChooser;
    private Button chooseFileButton;
    private Button equalizationButton;
    private Button linearTransformButton;
    private Button separateLinearTransformButton;

    private EqualizeFrame equalizeFrame;
    private LinearTransformFrame linearTransformFrame;
    private SeperatedLinearTransformFrame seperatedLinearTransformFrame;

    public LaunchFrame() {
        FrameSettings.setSize(this);
        FrameSettings.setCenter(this);
        setFileChooser();
        setButtons();
        setPanel();

        this.setTitle("Image processing demo");
        this.setResizable(false);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeWindow();
            }
        });
    }

    private void closeWindow() {
        this.dispose();
    }

    private void setFileChooser() {
        fileChooser = new ImageFileChooser();
    }

    private void setButtons() {
        chooseFileButton = new Button("Choose file");
        chooseFileButton.setFont(FrameSettings.getButtonFont());
        chooseFileButton.addActionListener(e -> {
            try {
                chooseFile();
                equalize = new Equalize(image, imageWidth, imageHeight);
                linearTransform = new LinearTransform(image, imageWidth, imageHeight);
                seperatedLinearTransform = new SeperatedLinearTransform(image, imageWidth, imageHeight);
                repaint();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            }
        });

        equalizationButton = new Button("Equalize");
        equalizationButton.setFont(FrameSettings.getButtonFont());
        equalizationButton.addActionListener(e -> {
            try {
                equalize.calculate();
                this.equalizeFrame = new EqualizeFrame(this, equalize);
                this.setVisible(false);
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        linearTransformButton = new Button("Linear Transform");
        linearTransformButton.setFont(FrameSettings.getButtonFont());
        linearTransformButton.addActionListener(e -> {
            try {
                linearTransform.calculate();
                this.linearTransformFrame = new LinearTransformFrame(this, linearTransform, false);
                this.setVisible(false);
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        separateLinearTransformButton = new Button("Linear Transform");
        separateLinearTransformButton.setFont(FrameSettings.getButtonFont());
        separateLinearTransformButton.addActionListener(e -> {
            try {
                seperatedLinearTransform.calculate();
                this.seperatedLinearTransformFrame = new SeperatedLinearTransformFrame(this, seperatedLinearTransform, true);
                this.setVisible(false);
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });
    }

    private void chooseFile() throws IOException, FileChooserNotApproveException {
        imagePath = fileChooser.getImagePath();
        image = ImageIO.read(new File(imagePath));
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();
    }

    private void setPanel() {
        Panel panel = new Panel();
        this.add(panel, BorderLayout.NORTH);
        panel.add(chooseFileButton);
        panel.add(equalizationButton);
        panel.add(linearTransformButton);
        panel.add(separateLinearTransformButton);
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        if (image == null) {
            return;
        }
        graphics.drawImage(image, (this.getWidth() - image.getWidth()) / 2,
                (this.getHeight() - image.getHeight()) / 2, null);
    }
}
