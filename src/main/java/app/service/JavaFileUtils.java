package app.service;

import app.exception.ServiceException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author marsel.maximov
 */
public class JavaFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(JavaFileUtils.class);
    private static final Pattern JAVA_PATTERN = Pattern.compile("\\.java");
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^\\s*package\\s*([^;]*);.*", Pattern.DOTALL);

    public static String resolveFullClassName(File javaFile) throws ServiceException {
        Matcher matcher;
        try {
            matcher = PACKAGE_PATTERN.matcher(FileUtils.readFileToString(javaFile));
        } catch (IOException e) {
            logger.error("Exception was thrown: ", e);
            throw new ServiceException("Internal error");
        }

        String fullClassName = resolveClassName(
                javaFile.getName()
        );

        if (matcher.find()) {
            String packageName = matcher.group(1);
            fullClassName = packageName + "." + fullClassName;
        }

        return fullClassName;
    }

    private static String resolveClassName(String fileName) throws ServiceException {
        Matcher matcher = JAVA_PATTERN.matcher(fileName);
        if (matcher.find()) {
            return fileName.substring(0, matcher.start());
        }

        throw new ServiceException("Error during loading file " + fileName);
    }
}
