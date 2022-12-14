package se.lernholt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class }) <-- When not needed e.g. when using static key authentication.
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
