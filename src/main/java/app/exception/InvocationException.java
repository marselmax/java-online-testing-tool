package app.exception;

/**
 * @author marsel.maximov
 */
public class InvocationException extends Exception {

    private final Throwable targetException;

    public InvocationException(Throwable targetException) {
        this.targetException = targetException;
    }

    public Throwable getTargetException() {
        return targetException;
    }
}
