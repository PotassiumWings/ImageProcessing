package main.exceptions;

public class TypeNotSupportedException extends Exception {
    private final int type;

    public TypeNotSupportedException(int type) {
        super("");
        this.type = type;
    }

    public void print() {
        System.err.println("TypeNotSupported, image type = " + type);
    }
}
