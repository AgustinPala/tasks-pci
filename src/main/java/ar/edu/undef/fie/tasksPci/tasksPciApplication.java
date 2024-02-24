package ar.edu.undef.fie.tasksPci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class tasksPciApplication {

    public static void main(String[] args) {
        SpringApplication.run(tasksPciApplication.class, args);
    }
}
