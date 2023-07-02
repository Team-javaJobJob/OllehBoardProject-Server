package com.example.ollethboardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OllethBoardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OllethBoardProjectApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigureer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry){
                corsRegistry.addMapping("/**").allowedOrigins().allowedOriginPatterns();
            }
        };
    }

}
