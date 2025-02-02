package com.employeeproject.Allocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AllocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllocationApplication.class, args);
	}

}
