package WineCellar.SEP4.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("WineCellar/SEP4/api")
public class Sep4Application {

	public static void main(String[] args) {
		SpringApplication.run(Sep4Application.class, args);
	}

}
