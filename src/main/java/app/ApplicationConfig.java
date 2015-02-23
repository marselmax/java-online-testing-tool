package app;

import app.service.TestService;
import app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author marsel.maximov
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan()
public class ApplicationConfig {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Bean
    public UserDetailsService userService() {
        return new UserService();
    }

    @Bean
    public TestService testService() {
        return new TestService();
    }
}
