//package com.example.ollethboardproject.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSocket
//public class WebSockConfig implements WebSocketConfigurer {
//
//    private final WebSocketHandler webSocketHandler;
//
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        //endpoint 설정 : /ws/chat
//        // 이를 통해서 ws://localhost:8080/ws/chat 으로 요청이 들어오면 webSocket 통신진행
//        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
//    }
//
//
//}
