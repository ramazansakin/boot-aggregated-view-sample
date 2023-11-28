package com.example.continuesaggregatesample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ContinuesAggregateSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContinuesAggregateSampleApplication.class, args);
	}

}
