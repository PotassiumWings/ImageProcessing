package main.transform;

public class SeparatedLinearTransformLineChart extends LineChart {
    private final int a;
    private final int b;
    private final int c;
    private final int d;

    public SeparatedLinearTransformLineChart(int a, int b, int c, int d, String string) {
        super(string);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public int mapping(int gray) {
        return SeparatedLinearTransformMapping.linearTransformMapping(a, b, c, d, gray);
    }
}
