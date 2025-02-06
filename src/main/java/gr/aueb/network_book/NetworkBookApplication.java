package gr.aueb.network_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NetworkBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkBookApplication.class, args);
	}

}
