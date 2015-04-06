package app.service;

import app.invokers.AbstractInvoker;
import app.model.db.TestResult;
import app.tests.Test;

import java.util.concurrent.Callable;

/**
 * @author marsel.maximov
 */
public class TestTask implements Callable<TestResult> {

    private final Class<? extends Test> testClass;
    private final Class<? extends AbstractInvoker> invokerClass;
    private final Class<?> clazz;


    public TestTask(Class<? extends Test> testClass, Class<? extends AbstractInvoker> invokerClass, Class<?> clazz) {
        this.testClass = testClass;
        this.invokerClass = invokerClass;
        this.clazz = clazz;
    }

    @Override
    public TestResult call() throws Exception {
        return testClass.newInstance().test(invokerClass.getConstructor(Class.class).newInstance(clazz));
    }
}
