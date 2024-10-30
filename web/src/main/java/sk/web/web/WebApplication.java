package sk.web.web;

import sk.web.web.run.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class WebApplication {

	private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);

	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			Run run = new Run(1, "First Run", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 5,
					Location.OUTDOOR);
			// Add logic to save the 'run' object to the database if needed
			log.info("Run:" + run);
		};
	}
}