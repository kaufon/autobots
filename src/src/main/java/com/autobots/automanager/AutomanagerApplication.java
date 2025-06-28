package com.autobots.automanager;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class AutomanagerApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
