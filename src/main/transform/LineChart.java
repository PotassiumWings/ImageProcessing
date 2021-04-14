package main.transform;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

public abstract class LineChart extends JLabel {
    private final String string;

    private static final int MAX_HEIGHT = 60;
    private static final int START_X = 20;
    private static final int START_Y = 20;
    private static final int GAP_X = 50;
    private static final int END_Y = START_Y + MAX_HEIGHT;

    public LineChart(String string) {
        this.string = string;
    }

    public abstract int mapping(int x);

    public void paint(Graphics g) {
        g.drawLine(START_X, END_Y, START_X + 256 / 2, END_Y);
        g.drawLine(START_X, START_Y, START_X, END_Y);
        for (int i = 0; i < 256; i += GAP_X) {
            g.drawString("" + i, START_X + (i - 2) / 2, END_Y + 13);
        }
        for (int i = 0; i < 255; i++) {
            int x1 = i;
            int x2 = i + 1;
            int y1 = mapping(x1);
            int y2 = mapping(x2);
            g.drawLine(START_X + x1 / 2, END_Y - y1 * MAX_HEIGHT / 255,
                    START_X + x2 / 2, END_Y - y2 * MAX_HEIGHT / 255);
        }

        Font font = new Font(g.getFont().getName(), Font.BOLD, 20);
        g.setFont(font);
        g.drawString(string, START_X + 128 / 2 - string.length() * 3, END_Y + 40);
    }
}
