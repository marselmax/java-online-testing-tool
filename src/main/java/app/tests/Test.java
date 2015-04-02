package app.tests;

import app.exception.ServiceException;
import app.invokers.AbstractInvoker;
import app.model.db.TestResult;

/**
 * @author marsel.maximov
 */

public interface Test<Invoker extends AbstractInvoker> {

    TestResult test(Invoker invoker) throws ReflectiveOperationException, ServiceException;

}
