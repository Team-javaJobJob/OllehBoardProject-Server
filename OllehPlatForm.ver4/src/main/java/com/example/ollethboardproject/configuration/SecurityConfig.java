
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
                .antMatchers("/api/v1/**", "/api/v1/members/login","/api/v1/main","/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/chats","/searchChat","/api/v1/communities").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/chats","/searchChat").permitAll()
                .antMatchers("/api/v1/loginAfter/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .build();
    }

    @Bean       // 박규현
    public JwtFilter jwtFilter(){
        return new JwtFilter(memberService, secretKey);
    }


}


