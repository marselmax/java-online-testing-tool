package app.exception;

/**
 * @author marsel.maximov
 */

public class NoSuchMethodException extends Exception {

    private final String methodName;

    public NoSuchMethodException(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
