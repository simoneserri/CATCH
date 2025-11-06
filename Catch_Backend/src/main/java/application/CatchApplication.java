package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {
    "application",
    "controllers",
    "interfaces",
    "services",
    "pojo"
})
public class CatchApplication {
	    public static void main(String[] args) {
	    	SpringApplication.run(CatchApplication.class, args);

    }
}
