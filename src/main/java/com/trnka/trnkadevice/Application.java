package com.trnka.trnkadevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan("com.trnka.trnkadevice")
@Slf4j
@EnableJpaRepositories("com.trnka.trnkadevice.repository")
@EntityScan(basePackages = {"com.trnka.trnkadevice.domain" })
public class Application {

    public static void main(String[] args) {
        log.info("Starting Application-service Service");
        log.info("");

        SpringApplication.run(Application.class, args);

        log.info("");
        log.info("Application-service Service was started");
    }

}
