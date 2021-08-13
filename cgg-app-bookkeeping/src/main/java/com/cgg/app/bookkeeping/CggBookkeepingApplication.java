package com.cgg.app.bookkeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cgg")
public class CggBookkeepingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CggBookkeepingApplication.class, args);
    }

}
