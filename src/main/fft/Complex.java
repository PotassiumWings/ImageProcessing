package main.fft;

public class Complex {
    public double x;
    public double y;

    public Complex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Complex add(Complex a) {
        return new Complex(this.x + a.x, this.y + a.y);
    }

    public Complex subtract(Complex a) {
        return new Complex(this.x - a.x, this.y - a.y);
    }

    public Complex multiply(Complex a) {
        return new Complex(this.x * a.x - this.y * a.y,
                this.x * a.y + this.y * a.x);
    }

    @Override
    public String toString() {
        return "(" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")";
    }

    @Override
    public Object clone() {
        return new Complex(x, y);
    }
}
