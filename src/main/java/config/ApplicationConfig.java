package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author marsel.maximov
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan()
public class ApplicationConfig {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ApplicationConfig.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
