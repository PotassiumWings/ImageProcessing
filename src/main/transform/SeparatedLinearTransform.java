package main.transform;

import java.awt.image.BufferedImage;

public class SeparatedLinearTransform extends LinearTransform {
    public SeparatedLinearTransform(BufferedImage image, int width, int height) {
        super(image, width, height);
    }

    @Override
    public int mapping(int gray) {
        return SeparatedLinearTransformMapping.linearTransformMapping(
                getA(), getB(), getC(), getD(), gray
        );
    }
}
