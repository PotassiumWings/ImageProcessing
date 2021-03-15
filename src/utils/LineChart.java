package utils;

import javax.swing.*;
import java.awt.*;

public class LineChart extends JLabel {
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean isLinear;
    private String string;

    private static final int MAX_HEIGHT = 60;
    private static final int START_X = 20;
    private static final int START_Y = 20;
    private static final int GAP_X = 50;
    private static final int END_Y = START_Y + MAX_HEIGHT;

    public LineChart(int a, int b, int c, int d, boolean isLinear, String string) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.isLinear = isLinear;
        this.string = string;
    }

    public void paint(Graphics g) {
        g.drawLine(START_X, END_Y, START_X + 256 / 2, END_Y);
        g.drawLine(START_X, START_Y, START_X, END_Y);
        for (int i = 0; i < 256; i += GAP_X) {
            g.drawString("" + i, START_X + (i - 2) / 2, END_Y + 13);
        }
//        for (int i = 0; i < 256; i++) {
//            g.drawLine(START_X + i, END_Y, START_X + i, END_Y - histogram[i]);
//        }
        if (!isLinear) {
            g.drawLine(START_X, END_Y, START_X + a / 2, END_Y - c * MAX_HEIGHT / 255);
            g.drawLine(START_X + b / 2, END_Y - d * MAX_HEIGHT / 255, START_X + 255 / 2, END_Y - MAX_HEIGHT);
        } else {
            g.drawLine(START_X, END_Y - c * MAX_HEIGHT / 255, START_X + a / 2, END_Y - c * MAX_HEIGHT / 255);
            g.drawLine(START_X + b / 2, END_Y - d * MAX_HEIGHT / 255, START_X + 255 / 2, END_Y - d * MAX_HEIGHT  / 255);
        }
        g.drawLine(START_X + a / 2, END_Y - c * MAX_HEIGHT / 255, START_X + b / 2, END_Y - d * MAX_HEIGHT / 255);
        Font font = new Font(g.getFont().getName(), Font.BOLD, 30);
        g.setFont(font);
        g.drawString(string, START_X + 128 - string.length() * 7, END_Y + 60);
    }
}
