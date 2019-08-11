package com.trnka.trnkadevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.trnka.trnkadevice")
@Slf4j
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableJpaRepositories()
public class Application {

	public static void main(String[] args) {
		log.info("Starting Application-service Service");
		log.info("");

		SpringApplication.run(Application.class, args);

		log.info("");
		log.info("Application-service Service was started");
	}

}
