package com.avis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.Retryable;

@SpringBootApplication
@Retryable
public class ChallanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallanApiApplication.class, args);
	}

}
