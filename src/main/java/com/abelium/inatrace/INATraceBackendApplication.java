package com.abelium.inatrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.abelium.inatrace.components.flyway.JpaMigration;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackageClasses = { INATraceBackendApplication.class }, 
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaMigration.class))
public class INATraceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(INATraceBackendApplication.class, args);
	}

	@PostConstruct
	private void init() {

		// Set default timezone to UTC, so we don't have trouble with persisting LocalDate
		// fields in MySQL (DB should also run in UTC timezone - by default MySQL does so)
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
