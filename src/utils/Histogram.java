package utils;

import javax.swing.*;
import java.awt.*;

public class Histogram extends JLabel {
    private final int[] histogram = new int[256];

    private static final int MAX_HEIGHT = 200;

    private static final int START_X = 100;
    private static final int START_Y = 115;
    private static final int GAP_X = 50;
    
    private static final int END_Y = START_Y + MAX_HEIGHT;

    private String string = "Histogram";

    private boolean deleted = false;

    public Histogram(int[] pixels) {
        for (int datum : pixels) {
            histogram[datum & 0xff]++;
        }
        int temp = histogram[0];
        for (int i = 1; i < 256; i++) {
            if (temp <= histogram[i]) {
                temp = histogram[i];
            }
        }

        for (int i = 0; i < 256; i++) {
            histogram[i] = histogram[i] * MAX_HEIGHT / temp;
        }
    }

    public void delete() {
        deleted = true;
    }

    public void paint(Graphics g) {
        if (deleted) {
            return;
        }
        g.drawLine(START_X, END_Y, START_X + 256, END_Y);
        g.drawLine(START_X, START_Y, START_X, END_Y);
        for (int i = 0; i < 256; i += GAP_X) {
            g.drawString("" + i, START_X + i - 2, END_Y + 13);
        }
        for (int i = 0; i < 256; i++) {
            g.drawLine(START_X + i, END_Y, START_X + i, END_Y - histogram[i]);
        }
        Font font = new Font(g.getFont().getName(), Font.BOLD, 30);
        g.setFont(font);
        g.drawString(string, START_X + 128 - string.length() * 7, END_Y + 60);
    }

    public void setString(String string) {
        this.string = string;
    }
}