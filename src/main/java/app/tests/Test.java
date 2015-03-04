package app.tests;

import app.invokers.AbstractInvoker;

/**
 * @author marsel.maximov
 */

public interface Test<Invoker extends AbstractInvoker> {

    Boolean test(Invoker invoker) throws ReflectiveOperationException;

}
