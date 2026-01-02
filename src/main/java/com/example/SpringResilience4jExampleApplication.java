package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringResilience4jExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringResilience4jExampleApplication.class, args);
	}
}
