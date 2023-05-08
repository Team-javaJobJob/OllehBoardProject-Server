package com.example.ollethboardproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer, WebConfigCor {

    @Override
    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(6000);
    }

//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);       // 내 서버의 json응답을 js가 처리할 수 있게 설정
//        config.addAllowedOrigin("http://localhost:3000"); //해당 ip에 응답허용
//        config.addAllowedHeader("*");   //모든 header에 응답허용
//        config.addAllowedMethod("*");//모든 메소드허용
////        Access.ORIGIN.EXPOSED.HEADERS
//        config.setMaxAge(3600L);
//        config.addExposedHeader(.HEADER);       // 이부분에서 어떻게 작성해야하는지 확인필요
//        source.registerCorsConfiguration("/api/v1/**",config);
//
//        return new CorsFilter(source);
//    }

}
