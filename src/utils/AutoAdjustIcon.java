package utils;

import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class AutoAdjustIcon {
    public static ImageIcon getAutoAdjustIcon(Image image, ImageObserver observer) {
        return new ImageIcon(image) {
            @Override
            public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
                Point startPoint = new Point(0, 0);
                Dimension componentSize = c.getSize();
                Dimension imgSize = new Dimension(getIconWidth(), getIconHeight());
                Dimension newSize = new Dimension();

                double ratio = 1.0 * imgSize.width / imgSize.height;
                if (componentSize.width / ratio < componentSize.height) {
                    newSize.width = componentSize.width;
                    newSize.height = (int) (newSize.width / ratio);
                } else {
                    newSize.height = componentSize.height;
                    newSize.width = (int) (ratio * newSize.height);
                }

                if (newSize.width < imgSize.width) {
                    imgSize = newSize;
                }

                startPoint.x = (int) ((componentSize.width - imgSize.width) / 2.0);
                startPoint.y = (int) ((componentSize.height - imgSize.height) / 2.0);

                g.drawImage(getImage(), startPoint.x, startPoint.y,
                        imgSize.width, imgSize.height, observer);
            }
        };
    }
}
