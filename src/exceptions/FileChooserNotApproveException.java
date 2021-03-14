package exceptions;

public class FileChooserNotApproveException extends Exception {
    private int FileChooserResult;

    public FileChooserNotApproveException(int result) {
        super("");
        FileChooserResult = result;
    }

    public void print() {
        System.err.println("FileChooserNotApprove, get result = " + FileChooserResult);
    }
}
