package gr.aueb.network_book;

import gr.aueb.network_book.role.IRoleRepository;
import gr.aueb.network_book.role.Role;
import lombok.Builder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//Enables JPA auditing and tells Spring to use AppAuditAware for getting the current user.
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@Builder
public class NetworkBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkBookApplication.class, args);

	}

	@Bean
	public CommandLineRunner runner(IRoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}
}
