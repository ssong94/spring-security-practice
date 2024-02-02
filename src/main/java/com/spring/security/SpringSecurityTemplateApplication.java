package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringSecurityTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityTemplateApplication.class, args);
	}

}
