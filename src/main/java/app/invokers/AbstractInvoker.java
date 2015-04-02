package app.invokers;

import app.exception.InvocationException;
import app.exception.NoSuchConstructorException;
import app.exception.ServiceException;
import app.service.TestSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author marsel.maximov
 */

public abstract class AbstractInvoker {

    protected final Logger logger = LoggerFactory.getLogger(AbstractInvoker.class);

    protected final Class<?> clazz;

    public AbstractInvoker(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getInstance(Object... initargs) throws NoSuchConstructorException, InvocationException, ServiceException {
        Class<?>[] parameterClasses = new Class<?>[initargs.length];
        for (int i = 0; i < initargs.length; i++) {
            parameterClasses[i] = initargs[i].getClass();
        }

        try {
            Constructor<?> constructor = clazz.getConstructor(parameterClasses);
            TestSecurityContext.enable();
            return constructor.newInstance(initargs);
        } catch (InvocationTargetException e) {
            TestSecurityContext.disable();
            logger.info("Exception during invocation was thrown: ", e);
            throw new InvocationException(e.getCause());
        } catch (NoSuchMethodException e) {
            TestSecurityContext.disable();
            logger.info("Exception during invocation was thrown: ", e);
            throw new NoSuchConstructorException(Arrays.toString(parameterClasses));
        } catch (InstantiationException e) {
            TestSecurityContext.disable();
            logger.info("Exception during invocation was thrown: ", e);
            throw new InvocationException(e.getCause());
        } catch (IllegalAccessException e) {
            TestSecurityContext.disable();
            logger.error("Exception was thrown ", e);
            throw new ServiceException("Internal error");
        } finally {
            TestSecurityContext.disable();
        }
    }

    public Object invokeMethod(Object instance, String methodName, Object... args) throws InvocationException, app.exception.NoSuchMethodException, ServiceException {
        Class<?>[] parameterClasses = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterClasses[i] = args[i].getClass();
        }

        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterClasses);
            TestSecurityContext.enable();
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            TestSecurityContext.disable();
            logger.error("Exception was thrown ", e);
            throw new ServiceException("Internal error");
        } catch (InvocationTargetException e) {
            TestSecurityContext.disable();
            logger.info("Exception during invocation was thrown: ", e);
            throw new InvocationException(e.getCause());
        } catch (NoSuchMethodException e) {
            TestSecurityContext.disable();
            logger.info("Exception during invocation was thrown: ", e);
            throw new app.exception.NoSuchMethodException(methodName);
        } finally {
            TestSecurityContext.disable();
        }
    }
}
