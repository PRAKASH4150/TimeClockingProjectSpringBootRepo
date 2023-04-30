package com.tracking.timeclocking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan(basePackages = {"com.tracking.timeclocking.entity"})
@EnableScheduling
public class TimeclockingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TimeclockingApplication.class, args);
	}

	 @Override
	   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(TimeclockingApplication.class);
	   }
}
