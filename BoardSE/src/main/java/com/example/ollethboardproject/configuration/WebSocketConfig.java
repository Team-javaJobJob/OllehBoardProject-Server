package com.example.ollethboardproject.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("http://localhost:3000","*").withSockJS();
//        .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js");
    }

    // 어플리케이션 내부에서 사용할 path 를 지정할 수 있음

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 어플리케이션에서 사용할 목적지 접두사 섲렁, 이렇게 해서 어플리케이션 내부에서 메시지 처리할 떄 주소를 구분가능
        registry.setApplicationDestinationPrefixes("/app");
        // 구독을 위한 토픽 접두사임
        registry.enableSimpleBroker("/chatroom","/user","/chatroom/{communityId}");
        // 유저별로 메시지 보낼 떄 사용할 접두사 설정 이를 통해 다른 사용자에게 전달되는 메시지를 쉽게 구분 가능
        registry.setUserDestinationPrefix("/user");
    }


}