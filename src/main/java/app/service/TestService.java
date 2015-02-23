package app.service;

import app.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author marsel.maximov
 */

public class TestService {

    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    public static final String SUBMITTED_PATH = System.getProperty("user.home") + "/datafiles/submitted/";

    public void submit(String user, Long taskId, MultipartFile multipartFile) throws ServiceException {
        upload(user, taskId, multipartFile);
    }

    private void upload(String user, Long taskId, MultipartFile multipartFile) throws ServiceException {
        File dir = new File(SUBMITTED_PATH + user + "/" + taskId);
        File file = new File(dir, generateName(multipartFile.getOriginalFilename()));
        try {
            dir.mkdirs();
            file.createNewFile();
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error("Error during copying file", e);
            throw new ServiceException("Internal error");
        }
    }

    private String generateName(String filename) {
        return System.nanoTime() + "_" + filename;
    }
}
