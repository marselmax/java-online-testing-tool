package app.ws;

import app.dao.TaskRepository;
import app.entity.Task;
import app.exception.ServiceException;
import app.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author marsel.maximov
 */

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestService testService;

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
        String user = request.getRemoteUser();
        Task task = taskRepository.getOne(taskId);
        Boolean result = testService.submit(user, task, multipartFile);
        model.addAttribute("result", result);

        return "testResult";
    }
}
