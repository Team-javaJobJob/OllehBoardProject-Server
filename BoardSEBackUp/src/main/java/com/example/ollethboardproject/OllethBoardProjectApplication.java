package com.example.ollethboardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OllethBoardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OllethBoardProjectApplication.class, args);
    }

//<<<<<<< Updated upstream
//=======
//    @Bean
//    public WebMvcConfigurer corsConfigureer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry corsRegistry){
//                corsRegistry.addMapping("/**").allowedOrigins().allowedOriginPatterns();
//            }
//        };
//    }

}
