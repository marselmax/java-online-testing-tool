package app;

import app.service.TestService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
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
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Value("${datafiles.submitted.location}")
    private String submittedPath;

    @Bean
    public UserDetailsService userService() {
        return new UserService();
    }

    @Bean
    public TestService testService() throws MalformedURLException {
        final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        return new TestService(
                submittedPath,
                javaCompiler,
                diagnosticCollector,
                javaCompiler.getStandardFileManager(diagnosticCollector, null, null)
        );
    }
}
