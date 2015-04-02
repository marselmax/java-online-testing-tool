package app.service;

import app.dao.SubmitResultRepository;
import app.exception.ServiceException;
import app.invokers.AbstractInvoker;
import app.model.db.SubmitResult;
import app.model.db.Task;
import app.model.db.TestResult;
import app.model.db.User;
import app.model.dto.CompileResult;
import app.tests.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author marsel.maximov
 */

public class TestService {
    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    private final String submittedPath;
    private final UploadService uploadService;
    private final CompilerService compilerService;
    private final SubmitResultRepository submitResultRepository;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public TestService(
            String submittedPath,
            UploadService uploadService,
            CompilerService compilerService, SubmitResultRepository submitResultRepository
    ) {
        this.submittedPath = submittedPath;
        this.uploadService = uploadService;
        this.compilerService = compilerService;
        this.submitResultRepository = submitResultRepository;
    }

    public TestResult submit(User user, Task task, MultipartFile multipartFile) throws ServiceException {
        Integer taskId = task.getTaskId();
        String pathname = getPathname(user.getName(), taskId);
        File file = uploadService.upload(multipartFile, pathname);
        TestResult result;
        CompileResult compileResult = compile(file);
        if (!compileResult.getResult()) {
            logger.info("Error during compilation file " + multipartFile.getOriginalFilename());
            result = new TestResult(Boolean.FALSE, "Compilation error: " + compileResult.getErrorMessage());
        } else {
            Class<?> clazz = loadSubmittedClass(pathname, JavaFileUtils.resolveFullClassName(file));

            Class<? extends AbstractInvoker> invokerClass = loadInvokerClass(task.getInvokerClassPath(), task.getInvokerClass());

            Class<? extends Test> testClass = loadTestClass(task.getTestClassPath(), task.getTestClass());

            Future<TestResult> testResultFuture = executorService.submit(new TestTask(testClass, invokerClass, clazz));

            try {
                result = testResultFuture.get();
            } catch (InterruptedException e) {
                logger.error("Exception was thrown ", e);
                throw new ServiceException("Internal error");
            } catch (ExecutionException e) {
                logger.error("Exception was thrown ", e);
                throw new ServiceException("Internal error");
            }
        }

        logger.info("Result={}", result);

        saveSubmitResult(user, task, result, pathname + multipartFile.getOriginalFilename());

        return result;
    }

    private void saveSubmitResult(User user, Task task, TestResult result, String pathname) {
        SubmitResult submitResult = new SubmitResult();
        submitResult.setUser(user);
        submitResult.setTask(task);
        submitResult.setTestResult(result);
        submitResult.setSubmitDateTime(new Timestamp(System.currentTimeMillis()));
        submitResult.setPathToFile(pathname);
        submitResultRepository.save(submitResult);
    }

    private String getPathname(String user, Integer taskId) {
        String dateStr = new Date().toString();
        return submittedPath + user + "/" + taskId + "/" + dateStr + "/";
    }

    private CompileResult compile(File file) {
        return compilerService.compile(file);
    }

    private Class<?> loadSubmittedClass(String pathname, String className) throws ServiceException {
        Class<?> clazz;
        try {
            URL pathUrl = Paths.get(pathname).toUri().toURL();
            ClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            logger.error("Error during loading class " + className, e);
            throw new ServiceException("Error during loading class " + className);
        } catch (MalformedURLException e) {
            logger.error("Error during loading class " + pathname, e);
            throw new ServiceException("Error during loading class " + className);
        }

        return clazz;
    }

    private Class<? extends AbstractInvoker> loadInvokerClass(String pathName, String invokerClassName) throws ServiceException {
        Class<? extends AbstractInvoker> invokerClass;
        try {
            URL pathUrl = Paths.get(pathName).toUri().toURL();
            ClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
            invokerClass = (Class<? extends AbstractInvoker>) classLoader.loadClass(invokerClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Invoker class " + invokerClassName + " not found", e);
            throw new ServiceException("Internal error");
        } catch (ClassCastException e) {
            logger.error("Invoker class " + invokerClassName + " don't extends AbstractInvoker", e);
            throw new ServiceException("Internal error");
        } catch (MalformedURLException e) {
            logger.error("Error during loading file " + invokerClassName, e);
            throw new ServiceException("Error during loading file " + invokerClassName);
        }

        return invokerClass;
    }

    private Class<? extends Test> loadTestClass(String pathName, String testClassName) throws ServiceException {
        Class<? extends Test> testClass;
        try {
            URL pathUrl = Paths.get(pathName).toUri().toURL();
            ClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
            testClass = (Class<? extends Test>) classLoader.loadClass(testClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Test class " + testClassName + " not found", e);
            throw new ServiceException("Internal error");
        } catch (ClassCastException e) {
            logger.error("Test class " + testClassName + " don't implements Test", e);
            throw new ServiceException("Internal error");
        } catch (MalformedURLException e) {
            logger.error("Error during loading file " + testClassName, e);
            throw new ServiceException("Error during loading file " + testClassName);
        }
        return testClass;
    }
}
