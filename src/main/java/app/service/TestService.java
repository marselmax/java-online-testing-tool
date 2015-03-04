package app.service;

import app.entity.Task;
import app.exception.ServiceException;
import app.invokers.AbstractInvoker;
import app.tests.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author marsel.maximov
 */

public class TestService {

    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    private static final String JAVA_PATTERN = "\\.java";

    private final String submittedPath;
    private final JavaCompiler javaCompiler;
    private final DiagnosticCollector<JavaFileObject> diagnosticCollector;
    private final StandardJavaFileManager javaFileManager;

    public TestService(
            String submittedPath, JavaCompiler javaCompiler,
            DiagnosticCollector<JavaFileObject> diagnosticCollector,
            StandardJavaFileManager javaFileManager) {
        this.submittedPath = submittedPath;
        this.javaCompiler = javaCompiler;
        this.diagnosticCollector = diagnosticCollector;
        this.javaFileManager = javaFileManager;
    }

    public Boolean submit(String user, Task task, MultipartFile multipartFile) throws ServiceException {
        Long taskId = task.getId();
        String pathname = getPathname(user, taskId);
        File file = upload(multipartFile, pathname);
        if (!compile(file)) {
            throw new ServiceException("Error during compiling file " + multipartFile.getOriginalFilename());
        }

        Class<?> clazz = loadSubmittedClass(pathname, file.getName());

        Class<? extends AbstractInvoker> invokerClass = loadInvokerClass(task.getInvokerClass());

        Class<? extends Test> testClass = loadTestClass(task.getTestClass());

        Boolean result;
        try {
            result = testClass.newInstance().test(invokerClass.getConstructor(Class.class).newInstance(clazz));
        } catch (ReflectiveOperationException e) {
            //todo
            logger.error("error", e);
            throw new ServiceException("error");
        }

        logger.info("Result={}", result);

        return result;
    }

    private File upload(MultipartFile multipartFile, String pathname) throws ServiceException {
        File dir = new File(pathname);
        File file = new File(dir, multipartFile.getOriginalFilename());
        try {
            dir.mkdirs();
            file.createNewFile();
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error("Error during copying file", e);
            throw new ServiceException("Internal error");
        }

        return file;
    }

    private String getPathname(String user, Long taskId) {
        String dateStr = new Date().toString();
        return submittedPath + user + "/" + taskId + "/" + dateStr + "/";
    }

    private boolean compile(File file) {
        Iterable<? extends JavaFileObject> javaFileObjects = javaFileManager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, javaFileManager, diagnosticCollector, null, null, javaFileObjects);
        return task.call();
    }

    private Class<?> loadSubmittedClass(String pathname, String fileName) throws ServiceException {
        Class<?> clazz;
        try {
            URL pathUrl = Paths.get(pathname).toUri().toURL();
            ClassLoader classLoader = new URLClassLoader(new URL[]{pathUrl});
            clazz = classLoader.loadClass(resolveClassName(fileName));
        } catch (ClassNotFoundException e) {
            logger.error("Error during loading file " + fileName, e);
            throw new ServiceException("Error during loading file " + fileName);
        } catch (MalformedURLException e) {
            logger.error("Error during loading file " + pathname, e);
            throw new ServiceException("Error during loading file " + fileName);
        }

        return clazz;
    }

    private Class<? extends AbstractInvoker> loadInvokerClass(String invokerClassName) throws ServiceException {
        Class<? extends AbstractInvoker> invokerClass;
        try {
            invokerClass = (Class<? extends AbstractInvoker>) Class.forName(invokerClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Invoker class " + invokerClassName + " not found", e);
            throw new ServiceException("Internal error");
        } catch (ClassCastException e) {
            logger.error("Invoker class " + invokerClassName + " don't extends AbstractInvoker", e);
            throw new ServiceException("Internal error");
        }

        return invokerClass;
    }

    private Class<? extends Test> loadTestClass(String testClassName) throws ServiceException {
        Class<? extends Test> testClass;
        try {
            testClass = (Class<? extends Test>) Class.forName(testClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Test class " + testClassName + " not found", e);
            throw new ServiceException("Internal error");
        } catch (ClassCastException e) {
            logger.error("Test class " + testClassName + " don't implements Test", e);
            throw new ServiceException("Internal error");
        }
        return testClass;
    }

    private String resolveClassName(String fileName) throws ServiceException {
        Pattern pattern = Pattern.compile(JAVA_PATTERN);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return fileName.substring(0, matcher.start());
        }

        logger.error("Can not resolve class name " + fileName);
        throw new ServiceException("Error during loading file " + fileName);
    }
}
