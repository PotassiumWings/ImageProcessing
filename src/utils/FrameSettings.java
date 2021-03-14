package utils;

import java.awt.*;

public class FrameSettings {
    private static final int frameWidth = 960;
    private static final int frameHeight = 960;

    private static final Font buttonFont = new Font(null, Font.BOLD, 20);

    public static Font getButtonFont() {
        return buttonFont;
    }

    public static void setSize(Frame frame) {
        frame.setSize(frameWidth, frameHeight);
    }

    public static void setCenter(Frame frame) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        frame.setLocation((kit.getScreenSize().width - frame.getWidth()) / 2,
                (kit.getScreenSize().height - frame.getHeight()) / 2 - 30);
    }
}
