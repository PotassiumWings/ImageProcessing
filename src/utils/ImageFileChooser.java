package utils;

import exceptions.FileChooserNotApproveException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageFileChooser extends JFileChooser {
    public ImageFileChooser() {
        super();
        // set extensions filter
        ArrayList<String> fileExtensions = new ArrayList<>(Arrays.asList(".jpg", ".jpeg", ".webp",
                ".bmp", ".pcx", ".tif", ".tga", ".exif", ".fpx", ".svg", ".psd", ".cdr", ".dxf",
                ".ufo", ".eps", ".ai", ".png", ".hdri", ".raw", ".wmf", ".flic", ".emf", ".ico"));
        StringBuilder fileExtensionsString = new StringBuilder("images (");
        for (String fileExtension : fileExtensions) {
            fileExtensionsString.append("*").append(fileExtension).append(",");
        }
        fileExtensionsString = new StringBuilder(
                fileExtensionsString.substring(0, fileExtensionsString.length() - 1)
        );
        fileExtensionsString.append(")");
        String finalFileExtensionsString = fileExtensionsString.toString();

        this.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                for (String fileExtension : fileExtensions) {
                    if (name.endsWith(fileExtension)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
                return finalFileExtensionsString;
            }
        });
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        this.setCurrentDirectory(fileSystemView.getHomeDirectory());
        this.setDialogTitle("Please choose a picture");
        this.setApproveButtonText("Select");
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public String getImagePath() throws FileChooserNotApproveException {
        int result = this.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String imagePath = this.getSelectedFile().getPath();
            System.err.println("path: " + imagePath);
            return imagePath;
        } else {
            throw new FileChooserNotApproveException(result);
        }
    }
}
