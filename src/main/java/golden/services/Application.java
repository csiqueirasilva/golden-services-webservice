package golden.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author csiqueira
 */
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

	private static final Class<Application> APPLICATION_CLASS = Application.class;

	public static void main(String[] args) {
		SpringApplication.run(APPLICATION_CLASS, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(APPLICATION_CLASS);
	}

}
