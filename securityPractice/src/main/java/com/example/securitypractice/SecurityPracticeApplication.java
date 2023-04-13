package com.example.securitypractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SecurityPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityPracticeApplication.class, args);
    }

}
