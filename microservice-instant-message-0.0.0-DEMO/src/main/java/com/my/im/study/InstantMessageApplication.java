package com.my.im.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//d:/forCMD/ngrok http 8080
//mvn clean install
@SpringBootApplication
public class InstantMessageApplication {
    private final Logger log = LoggerFactory.getLogger(InstantMessageApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(InstantMessageApplication.class, args);
    }
}