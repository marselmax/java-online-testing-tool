package app.ws;

import app.dao.SubmitResultRepository;
import app.dao.TaskRepository;
import app.dao.UserRepository;
import app.exception.ServiceException;
import app.model.db.*;
import app.model.ui.ResultUI;
import app.service.TaskService;
import app.service.TestService;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marsel.maximov
 */

@RequestMapping(value = "/")
public class MainController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SubmitResultRepository submitResultRepository;
    private final TestService testService;
    private final TaskService taskService;
    private final String tasksPath;

    public MainController(TaskRepository taskRepository,
                          UserRepository userRepository,
                          SubmitResultRepository submitResultRepository,
                          TestService testService,
                          TaskService taskService,
                          String tasksPath
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.submitResultRepository = submitResultRepository;
        this.testService = testService;
        this.taskService = taskService;
        this.tasksPath = tasksPath;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @ModelAttribute(value = "tasks")
    public List<Task> getTasks() {
        return taskRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public String submit(HttpServletRequest request,
                         Model model,
                         @RequestParam("datafile") MultipartFile multipartFile,
                         @RequestParam("taskId") Long taskId
    ) throws ServiceException {
        String userName = request.getRemoteUser();
        Task task = taskRepository.getOne(taskId);
        User user = userRepository.findByName(userName);
        TestResult result = testService.submit(user, task, multipartFile);
        model.addAttribute("testResult", result);

        return "testResult";
    }

    @ModelAttribute(value = "isAdmin")
    private Boolean isAdmin(HttpServletRequest request) {
        String userName = request.getRemoteUser();

        User user = userRepository.findByName(userName);
        if (user == null) {
            return Boolean.FALSE;
        }

        for (Role role : user.getRoles()) {
            if (role.getValue() == Role.Value.ADMIN) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newTask() {
        return "newTask";
    }

    @RequestMapping(value = "newTask", method = RequestMethod.POST)
    public String saveNewTask(@RequestParam Integer taskId,
                              @RequestParam String taskCondition,
                              @RequestParam MultipartFile invokerMultipartFile,
                              @RequestParam MultipartFile testMultipartFile
    ) throws ServiceException {

        taskService.saveNewTask(taskId, taskCondition, invokerMultipartFile, testMultipartFile);

        return "redirect:/";
    }

    @ModelAttribute("allUsers")
    public List<String> allUsers() {
        List<User> userList = userRepository.findAll();
        List<String> result = new ArrayList<>();
        for (User user : userList) {
            result.add(user.getName());
        }

        return result;
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String userPage(String userName, Model model) {
        List<SubmitResult> submitResults = submitResultRepository.findByUser(userName);

        List<ResultUI> resultUIs = new ArrayList<>(submitResults.size());
        for (SubmitResult submitResult : submitResults) {
            resultUIs.add(
                    new ResultUI.Builder()
                            .taskId(submitResult.getTask().getTaskId())
                            .taskCondition(submitResult.getTask().getCondition())
                            .result(submitResult.getTestResult().getResult())
                            .submitDateTime(submitResult.getSubmitDateTime().toLocalDateTime())
                            .build()
            );
        }

        model.addAttribute("submittedResults", resultUIs);

        return "user";
    }

}
