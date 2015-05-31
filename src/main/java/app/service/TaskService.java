package app.service;

import app.dao.TaskRepository;
import app.exception.ServiceException;
import app.model.db.Task;
import app.model.dto.CompileResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author marsel.maximov
 */
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static final String INVOKER_PATH = "invoker";
    private static final String TEST_PATH = "test";

    private final UploadService uploadService;
    private final CompilerService compilerService;
    private final TaskRepository taskRepository;
    private final String tasksPath;

    public TaskService(UploadService uploadService, CompilerService compilerService, TaskRepository taskRepository, String tasksPath) {
        this.uploadService = uploadService;
        this.compilerService = compilerService;
        this.taskRepository = taskRepository;
        this.tasksPath = tasksPath;
    }

    public void saveNewTask(Integer taskId,
                            String taskCondition,
                            MultipartFile invokerMultipartFile,
                            MultipartFile testMultipartFile
    ) throws ServiceException {

        Task newTask = new Task();
        newTask.setTaskId(taskId);
        newTask.setCondition(taskCondition);

        Date date = new Date();

        String invokerClassPathName = generatePathName(taskId, date, INVOKER_PATH);
        newTask.setInvokerClassPath(invokerClassPathName);
        File invokerFile = uploadService.upload(invokerMultipartFile, invokerClassPathName);
        String fullInvokerClassName = getFullClassName(invokerFile);
        newTask.setInvokerClass(fullInvokerClassName);
        compile(invokerFile);

        String testClassPathName = generatePathName(taskId, date, TEST_PATH);
        newTask.setTestClassPath(testClassPathName);
        File testFile = uploadService.upload(testMultipartFile, testClassPathName);
        String fullTestClassName = getFullClassName(testFile);
        newTask.setTestClass(fullTestClassName);
        compile(testFile);

        taskRepository.save(newTask);
    }

    private void compile(File file) throws ServiceException {
        CompileResult invokerCompileResult = compilerService.compile(file);
        if (!invokerCompileResult.getResult()) {
            throw new ServiceException("Error during compilation file " + file.getName());
        }
    }

    private String getFullClassName(File file) throws ServiceException {
        return JavaFileUtils.resolveFullClassName(file);
    }

    private String generatePathName(Integer taskId, Date date, String classType) {
        return tasksPath + taskId + "/" + date + "/" + classType + "/";
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    public Task getTask(Long taskId) {
        return taskRepository.getOne(taskId);
    }
}
