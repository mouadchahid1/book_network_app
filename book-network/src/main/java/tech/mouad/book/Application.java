package tech.mouad.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import tech.mouad.book.role.Role;
import tech.mouad.book.role.RoleRepository;

@SpringBootApplication
// ici je mais le non de Bean qui est le nom de la methode par default
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // pour que Entity listner de autditing puisse bien travail .
@EnableAsync

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner start(RoleRepository roleRepository) {
		return args ->  {
			if(roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}

}
