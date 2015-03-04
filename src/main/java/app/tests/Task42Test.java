package app.tests;

import app.invokers.Task42Invoker;

/**
 * @author marsel.maximov
 */

public class Task42Test implements Test<Task42Invoker> {

    @Override
    public Boolean test(Task42Invoker invoker) throws ReflectiveOperationException {
        return new Integer(42).equals(invoker.get42());
    }

}
