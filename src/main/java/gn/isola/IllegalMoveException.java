package gn.isola;

public class IllegalMoveException extends Exception {
    public IllegalMoveException() {
    }

    public IllegalMoveException(String s) {
        super(s);
    }

    public IllegalMoveException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalMoveException(Throwable throwable) {
        super(throwable);
    }
}
