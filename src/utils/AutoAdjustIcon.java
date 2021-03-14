package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class AutoAdjustIcon {
    public static ImageIcon getAutoAdjustIcon(Image image, ImageObserver observer) {
        return new ImageIcon(image) {
            @Override
            public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
                Point startPoint = new Point(0, 0);
                Dimension cSize = c.getSize();
                Dimension imgSize = new Dimension(getIconWidth(), getIconHeight());

                double ratio = 1.0 * imgSize.width / imgSize.height;
                if (cSize.width / ratio < imgSize.height) {
                    imgSize.width = cSize.width;
                    imgSize.height = (int) (imgSize.width / ratio);
                } else {
                    imgSize.height = cSize.height;
                    imgSize.width = (int) (ratio * imgSize.height);
                }

                startPoint.x = (int) ((cSize.width - imgSize.width) / 2.0);
                startPoint.y = (int) ((cSize.height - imgSize.height) / 2.0);

                g.drawImage(getImage(), startPoint.x, startPoint.y, imgSize.width, imgSize.height, observer);
            }
        };
    }
}
