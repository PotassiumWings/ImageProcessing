package exceptions;

public class AttributeOutOfBoundException extends Exception {
    private int a;
    private int b;
    private int c;
    private int d;
    public AttributeOutOfBoundException(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void print() {
        System.out.println("AttributeOutOfBound: got " + a + " " + b + " " + c + " " + d);
    }
}
