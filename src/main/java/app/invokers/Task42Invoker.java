package app.invokers;

/**
 * @author marsel.maximov
 */

public class Task42Invoker extends AbstractInvoker {

    public Task42Invoker(Class<?> clazz) {
        super(clazz);
    }

    public Integer get42() throws ReflectiveOperationException {
        Object instance = clazz.newInstance();
        Object get42 = clazz.getDeclaredMethod("get42").invoke(instance);

        return (Integer) get42;
    }
}
