import transform.Equalize;
import transform.EqualizeFrame;
import exceptions.FileChooserNotApproveException;
import exceptions.TypeNotSupportedException;
import transform.LinearTransform;
import transform.LinearTransformFrame;
import transform.SeparatedLinearTransform;
import transform.SeparatedLinearTransformFrame;
import utils.AutoAdjustIcon;
import utils.FrameSettings;
import utils.ImageFileChooser;
import utils.ImageTypeFilter;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LaunchFrame extends JFrame {
    private String imagePath;
    private BufferedImage image;
    private JLabel imageLabel = null;

    private Equalize equalize;
    private LinearTransform linearTransform;
    private SeparatedLinearTransform separatedLinearTransform;

    private int imageHeight;
    private int imageWidth;

    private ImageFileChooser fileChooser;
    private Button chooseFileButton;
    private Button equalizationButton;
    private Button linearTransformButton;
    private Button separateLinearTransformButton;

    public LaunchFrame() {
        FrameSettings.setSize(this);
        FrameSettings.setCenter(this);
        setFileChooser();
        setButtons();
        setPanel();

        this.setTitle(FrameSettings.getName());
        this.setResizable(true);
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
                separatedLinearTransform =
                        new SeparatedLinearTransform(image, imageWidth, imageHeight);
                repaint();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        equalizationButton = new Button("Equalize");
        equalizationButton.setFont(FrameSettings.getButtonFont());
        equalizationButton.addActionListener(e -> {
            try {
                equalize.calculate();
                new EqualizeFrame(this, equalize);
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
                new LinearTransformFrame(this, linearTransform);
                this.setVisible(false);
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        separateLinearTransformButton = new Button("Sep.Linear Transform");
        separateLinearTransformButton.setFont(FrameSettings.getButtonFont());
        separateLinearTransformButton.addActionListener(e -> {
            try {
                separatedLinearTransform.calculate();
                new SeparatedLinearTransformFrame(this, separatedLinearTransform);
                this.setVisible(false);
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });
    }

    private void chooseFile() throws IOException, FileChooserNotApproveException, TypeNotSupportedException {
        imagePath = fileChooser.getImagePath();
        image = ImageIO.read(new File(imagePath));
        ImageTypeFilter.checkImageType(image.getType());
        imageHeight = image.getHeight();
        imageWidth = image.getWidth();

        if (imageLabel != null) {
            imageLabel.setVisible(false);
            this.remove(imageLabel);
        }
        imageLabel = new JLabel();
        this.add(imageLabel);
        imageLabel.setIcon(AutoAdjustIcon.getAutoAdjustIcon(image, this));
        imageLabel.revalidate();
    }

    private void setPanel() {
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.NORTH);
        panel.add(chooseFileButton);
        panel.add(equalizationButton);
        panel.add(linearTransformButton);
        panel.add(separateLinearTransformButton);
    }
}
