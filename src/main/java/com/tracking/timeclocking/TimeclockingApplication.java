package com.tracking.timeclocking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TimeclockingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeclockingApplication.class, args);
	}

}
