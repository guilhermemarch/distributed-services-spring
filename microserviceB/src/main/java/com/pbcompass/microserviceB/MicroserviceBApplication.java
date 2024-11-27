package com.pbcompass.microserviceB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceBApplication.class, args);
	}

}
