package tech.mouad.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // pour que Entity listner de autditing puisse bien travail .
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
