package com.plzt.onenet.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class Starter  {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Starter.class, args);
	}
	
	
}
