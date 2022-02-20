package hack.badgemeal.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "hack.badgemeal")
@EnableScheduling
public class ApisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApisApplication.class, args);
    }

}
