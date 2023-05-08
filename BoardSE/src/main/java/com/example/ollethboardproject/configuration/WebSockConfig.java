package com.example.ollethboardproject.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSockConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    @CrossOrigin
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(webSocketHandler, "/api/v1/ollehChat", "/Chat")
                .setAllowedOrigins("*"); //특정 도메인만 접속 가능하도록 설정
    }
}


/*
    websocket 설정
    RequiredArgsConstructor 이용하여 클래스 내 final 필드에 대한 생성자 자동생성
    Configuration 어노테이션 이용해 Spring context 에서 Bean 을 생성하고 구성하는 클래스임 나타내고
    @EnableWebSocket 이용해서 웹소켓 사용할 수 있도록 설정
    WebSocketConfigurer 인터페이스 구현해서 필요 메서드 제공받고
    핸들러 주입받아서 WebSocketHandlerRegistry 에 등록, 클라이언트에 접속할 URL과 허용된 도메인 설정
    여기선 /ws/chat 경로에 WebSocketHandler 등록, 모든 도메인에서 접속 허용
 */