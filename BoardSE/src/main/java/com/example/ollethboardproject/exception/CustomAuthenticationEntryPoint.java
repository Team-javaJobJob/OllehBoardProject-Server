package com.example.ollethboardproject.exception;

import com.example.ollethboardproject.controller.response.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");    //응답 본문에서 전송되는 데이터 유형을 나타내는 HTTP 헤더
        response.setStatus(ErrorCode.INVALID_TOKEN.getHttpStatus().value());    // HTTP 상태 코드
        response.getWriter().write(Response.error(ErrorCode.INVALID_TOKEN.name()).toStream());  //response body에 담을 값
    }
}
