package com.cimss.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrossIMApplication {
    private final Logger log = LoggerFactory.getLogger(CrossIMApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrossIMApplication.class, args);
    }
}