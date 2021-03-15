package utils;

import java.awt.*;

public class FrameSettings {
    private static final int FRAME_WIDTH = 960;
    private static final int FRAME_HEIGHT = 960;

    private static final Font BUTTON_FONT = new Font(null, Font.BOLD, 20);

    public static Font getButtonFont() {
        return BUTTON_FONT;
    }

    public static void setSize(Frame frame) {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public static void setCenter(Frame frame) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        frame.setLocation((kit.getScreenSize().width - frame.getWidth()) / 2,
                (kit.getScreenSize().height - frame.getHeight()) / 2 - 30);
    }
}
