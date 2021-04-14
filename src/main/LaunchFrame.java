package main;

import main.exceptions.FileChooserNotApproveException;
import main.exceptions.TypeNotSupportedException;
import main.fft.FFTLaunchFrame;
import main.fft.FourierTransform;
import main.transform.TransformLaunchFrame;
import main.utils.AutoAdjustIcon;
import main.utils.FrameSettings;
import main.utils.ImageFileChooser;
import main.utils.ImageTypeFilter;

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

    private Button chooseFileButton;
    private Button transformButton;
    private Button fftButton;

    private int imageHeight;
    private int imageWidth;

    private ImageFileChooser fileChooser;

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

    private void setButtons() {
        chooseFileButton = new Button("Choose file");
        chooseFileButton.setFont(FrameSettings.getButtonFont());
        chooseFileButton.addActionListener(e -> {
            try {
                chooseFile();
                repaint();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FileChooserNotApproveException fileChooserNotApproveException) {
                fileChooserNotApproveException.print();
            } catch (TypeNotSupportedException typeNotSupportedException) {
                typeNotSupportedException.print();
            }
        });

        transformButton = new Button("main.Transform without FFT");
        transformButton.setFont(FrameSettings.getButtonFont());
        transformButton.addActionListener(e -> {
            new TransformLaunchFrame(this, image, imageWidth, imageHeight);
            this.setVisible(false);
        });

        fftButton = new Button("main.Transform without FFT");
        fftButton.setFont(FrameSettings.getButtonFont());
        fftButton.addActionListener(e -> {
            new FFTLaunchFrame(this, new FourierTransform(image), image, imageWidth, imageHeight);
            this.setVisible(false);
        });
    }

    private void setFileChooser() {
        fileChooser = new ImageFileChooser();
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
        panel.add(transformButton);
    }
}
