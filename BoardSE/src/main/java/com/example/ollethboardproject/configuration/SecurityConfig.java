package com.example.ollethboardproject.configuration;


import com.example.ollethboardproject.configuration.filter.JwtFilter;
import com.example.ollethboardproject.exception.CustomAuthenticationEntryPoint;
import com.example.ollethboardproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberService memberService;
    @Value("${jwt.token.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/members/join", "/api/v1/members/login","api/v1/**").permitAll()
//                .antMatchers("/api/v1/**").authenticated()
                // .antMatchers(HttpMethod.GET, "/api/v1/reviews").hasRole("VIP")
                .antMatchers("/api/v1/random/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .build();
    }
}
//    @Bean       // 박규현
//    public JwtFilter jwtFilter(){
//        return new JwtFilter(memberService, secretKey);
//    }


//<<<<<<< Updated upstream
//=======




//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*")); // 허용할 도메인 설정
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE")); // 허용할 HTTP 메소드 설정
//        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 헤더 설정
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry){
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000") // 허용할 Origin 지정
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
//                        .allowCredentials(true);
//            }
//        };
//    }


//>>>>>>> Stashed changes

