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
public class UploadService {

    private final Logger logger = LoggerFactory.getLogger(UploadService.class);

    public File upload(MultipartFile multipartFile, String pathname) throws ServiceException {
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
}
