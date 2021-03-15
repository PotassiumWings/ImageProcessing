package linear;

import java.awt.image.BufferedImage;

public class SeperatedLinearTransform extends LinearTransform {
    public SeperatedLinearTransform(BufferedImage image, int width, int height) {
        super(image, width, height);
    }

    @Override
    public int mapping(int gray) {
        int res;
        int a = getA();
        int b = getB();
        int c = getC();
        int d = getD();
        if (gray < a) {
            res = gray * c / a;
        } else if(gray > b) {
            res = (gray - b) * (255 - d) / (255 - b) + d;
        } else {
            res = (gray - a) * (d - c) / (b - a) + c;
        }
        return res;
    }
}
