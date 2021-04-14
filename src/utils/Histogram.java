package utils;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Histogram extends JLabel {
    private final int[][] histogram = new int[3][256];

    private static final int MAX_HEIGHT = 100;

    private static final int START_X = 50;
    private static final int START_Y = 20;
    private static final int GAP_X = 50;
    
    private static final int END_Y = START_Y + MAX_HEIGHT;

    private String string = "Histogram";

    private boolean deleted = false;

    private final int imageType;

    public Histogram(int[] pixels, int imageType) {
        for (int channel = 0; channel < 3; channel++) {
            for (int datum : pixels) {
                histogram[channel][ColorGetter.getColorValue(datum, channel)]++;
            }
            int temp = histogram[channel][0];
            for (int i = 1; i < 256; i++) {
                if (temp <= histogram[channel][i]) {
                    temp = histogram[channel][i];
                }
            }

            for (int i = 0; i < 256; i++) {
                histogram[channel][i] = histogram[channel][i] * MAX_HEIGHT / temp;
            }
        }
        this.imageType = imageType;
    }

    public void delete() {
        deleted = true;
    }

    public void paint(Graphics g) {
        if (deleted) {
            return;
        }
        if (imageType == BufferedImage.TYPE_BYTE_GRAY) {
            int startX = START_X;
            int startY = START_Y + MAX_HEIGHT;
            int endY = startY + MAX_HEIGHT;
            g.drawLine(startX, endY, startX + 256, endY);
            g.drawLine(startX, startY, startX, endY);
            for (int i = 0; i < 256; i += GAP_X) {
                g.drawString("" + i, startX + (i - 2), endY + 13);
            }
            for (int i = 0; i < 256; i++) {
                g.drawLine(startX + i, endY, startX + i, endY - histogram[0][i]);
            }

            Font font = new Font(g.getFont().getName(), Font.BOLD, 30);
            g.setFont(font);
            g.drawString(string, START_X + 128 - string.length() * 7, END_Y * 2 + 90);
        } else {
            for (int channel = 0; channel < 3; channel++) {
                int I = channel / 2;
                int J = channel % 2;
                int lenX = 200;
                int lenY = 180;
                int startX = START_X + lenX * I;
                int startY = START_Y + lenY * J;
                int endY = startY + MAX_HEIGHT;

                g.drawLine(startX, endY, startX + 256 / 2, endY);
                g.drawLine(startX, startY, startX, endY);
                for (int i = 0; i < 256; i += GAP_X) {
                    g.drawString("" + i, startX + (i - 2) / 2, endY + 13);
                }
                for (int i = 0; i < 256; i++) {
                    g.drawLine(startX + i / 2, endY, startX + i / 2, endY - histogram[channel][i]);
                }
            }

            Font font = new Font(g.getFont().getName(), Font.BOLD, 30);
            g.setFont(font);
            g.drawString(string, START_X + 128 - string.length() * 5, END_Y * 2 + 120);
        }
    }

    public void setString(String string) {
        this.string = string;
    }
}