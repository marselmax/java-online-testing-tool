package app.invokers;

/**
 * @author marsel.maximov
 */

public abstract class AbstractInvoker {

    protected final Class<?> clazz;

    public AbstractInvoker(Class<?> clazz) {
        this.clazz = clazz;
    }
}
