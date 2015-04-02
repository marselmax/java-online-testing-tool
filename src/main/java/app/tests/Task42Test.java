package app.tests;

import app.exception.InvocationException;
import app.exception.NoSuchConstructorException;
import app.exception.ServiceException;
import app.invokers.Task42Invoker;
import app.model.db.TestResult;

/**
 * @author marsel.maximov
 */

public class Task42Test implements Test<Task42Invoker> {

    @Override
    public TestResult test(Task42Invoker invoker) throws ServiceException {
        try {
            Object instance = invoker.getInstance();

            Integer invokeResult = invoker.get42(instance);
            Boolean result = Integer.valueOf(42).equals(invokeResult);

            String cause;
            if (result) {
                cause = "Success";
            } else {
                cause = "Test error";
            }

            return new TestResult(result, cause);

        } catch (app.exception.NoSuchMethodException e) {
            return new TestResult(Boolean.FALSE, "No such method: " + e.getMethodName());
        } catch (NoSuchConstructorException e) {
            return new TestResult(Boolean.FALSE, "No such constructor with parameter types: " + e.getConstructorParameters());
        } catch (InvocationException e) {
            return new TestResult(Boolean.FALSE, "Exception was thrown: " + e.getTargetException().toString());
        }
    }

}
