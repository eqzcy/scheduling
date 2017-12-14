package com.gdtech.scheduling.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Controller
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = "com.gdtech.scheduling.schedule.mapper")
public class SchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingApplication.class, args);
	}
}
