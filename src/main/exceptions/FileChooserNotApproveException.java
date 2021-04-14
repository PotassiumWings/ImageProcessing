package main.exceptions;

public class FileChooserNotApproveException extends Exception {
    private final int fileChooserResult;

    public FileChooserNotApproveException(int result) {
        super("");
        fileChooserResult = result;
    }

    public void print() {
        System.err.println("FileChooserNotApprove, get result = " + fileChooserResult);
    }
}
