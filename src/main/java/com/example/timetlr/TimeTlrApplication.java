package com.example.timetlr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.app.time.source.TimeSourceConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TimeSourceConfiguration.class)
public class TimeTlrApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTlrApplication.class, args);
	}
}
