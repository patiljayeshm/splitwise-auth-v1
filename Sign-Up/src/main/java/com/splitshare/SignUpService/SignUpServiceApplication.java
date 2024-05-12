package com.splitshare.SignUpService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SignUpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SignUpServiceApplication.class, args);
	}

}
