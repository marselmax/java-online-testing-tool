package app.exception;

/**
 * @author marsel.maximov
 */
public class NoSuchConstructorException extends Exception {

    private final String constructorParameters;

    public NoSuchConstructorException(String constructorParameters) {
        this.constructorParameters = constructorParameters;
    }

    public String getConstructorParameters() {
        return constructorParameters;
    }
}
