package transform;

import java.awt.Frame;

public class SeparatedLinearTransformFrame extends LinearTransformFrame {
    public SeparatedLinearTransformFrame(Frame fatherFrame, LinearTransform linearTransform) {
        super(fatherFrame, linearTransform);
    }

    @Override
    public void setLineChart(int a, int b, int c, int d) {
        setLineChart(new SeparatedLinearTransformLineChart(a, b, c, d, "Separated"));
    }
}
