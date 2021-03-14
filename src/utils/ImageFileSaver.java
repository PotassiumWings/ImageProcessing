package utils;

import exceptions.FileChooserNotApproveException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFileSaver extends JFileChooser {
    public ImageFileSaver() {
        super();
        this.setFileFilter(new FileNameExtensionFilter("Image",".jpg",".png"));
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        this.setCurrentDirectory(fileSystemView.getHomeDirectory());
        this.setDialogTitle("Choose a destination to save");
    }

    public void save(BufferedImage image) throws FileChooserNotApproveException {
        int ret = this.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            String path = this.getSelectedFile().getPath();
            try {
                File f = new File(path + ".jpg");
                ImageIO.write(image, "jpg", f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new FileChooserNotApproveException(ret);
        }
    }
}
