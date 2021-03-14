import equalize.Equalize;
import equalize.EqualizeFrame;
import exceptions.FileChooserNotApproveException;
import exceptions.TypeNotSupportedException;
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
    private int imageHeight;
    private int imageWidth;

    private ImageFileChooser fileChooser;
    private Button chooseFileButton;
    private Button equalizationButton;
    private Panel panel;

    private EqualizeFrame equalizeFrame;

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
            } catch (IOException ioException) {
                ioException.printStackTrace();
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
        panel = new Panel();
        this.add(panel, BorderLayout.NORTH);
        panel.add(chooseFileButton);
        panel.add(equalizationButton);
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
