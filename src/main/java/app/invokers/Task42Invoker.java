package app.invokers;

import app.exception.InvocationException;
import app.exception.NoSuchMethodException;
import app.exception.ServiceException;

/**
 * @author marsel.maximov
 */

public class Task42Invoker extends AbstractInvoker {

    public Task42Invoker(Class<?> clazz) {
        super(clazz);
    }

    public Integer get42(Object instance) throws NoSuchMethodException, InvocationException, ServiceException {
        return (Integer) invokeMethod(instance, "get42");
    }
}
