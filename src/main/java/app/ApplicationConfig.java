package app;

import app.dao.SubmitResultRepository;
import app.dao.TaskRepository;
import app.dao.UserRepository;
import app.service.*;
import app.ws.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.net.MalformedURLException;

/**
 * @author marsel.maximov
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationConfig {
    public static void main(String[] args) {
        TestSecurityContext.init();
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Value("${datafiles.submitted.location}")
    private String submittedPath;

    @Value("${datafiles.tasks.location}")
    private String tasksPath;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmitResultRepository submitResultRepository;

    @Bean
    public UserDetailsService userService() {
        return new UserService();
    }

    @Bean
    public UploadService uploadService() {
        return new UploadService();
    }

    @Bean
    public CompilerService compilerService() {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        return new CompilerService(javaCompiler, fileManager);
    }

    @Bean
    public TestService testService() throws MalformedURLException {
        return new TestService(
                submittedPath,
                uploadService(),
                compilerService(),
                submitResultRepository
        );
    }

    @Bean
    public TaskService taskService() {
        return new TaskService(uploadService(), compilerService(), taskRepository, tasksPath);
    }

    @Bean
    public MainController mainController() throws MalformedURLException {
        return new MainController(
                taskRepository, userRepository, submitResultRepository,
                testService(), taskService(), tasksPath
        );
    }
}
