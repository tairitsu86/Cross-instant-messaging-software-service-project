package com.cimss.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrossIMApplication {
    private final Logger log = LoggerFactory.getLogger(CrossIMApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrossIMApplication.class, args);
    }
    @Bean
    public ObjectMapper createObjectMapper(){
        return new ObjectMapper();
    }
}