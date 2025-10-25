package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "application",
    "controllers",
    "interfaces",
    "pojo"
})
public class CatchApplication {
	    public static void main(String[] args) {
	    	SpringApplication.run(CatchApplication.class, args);

    }
}
