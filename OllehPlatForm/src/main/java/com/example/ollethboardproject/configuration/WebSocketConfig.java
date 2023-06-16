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
        //        Client에서 Send 요청처리
//        Spring docs 에서는 /topic, /queue로 나오나 편의상 /app , /user 변경
        registry.setApplicationDestinationPrefixes("/app");
        //        해당 경로로 SimpleBroker 를 등록
//        SimpleBroker 는 해당하는 경로를 subscribe 하는 client 에게 메시지를 전달하는 간단한 작업 수행
        //        enableStompBrokerRelay
//        SimpleBroker 의 기능과 외부 Message Broker(RabbitMQ, ActiveMQ 등)에 메시지 전달하는 기능
        registry.enableSimpleBroker("/chatroom","/user");
        registry.setUserDestinationPrefix("/user");
    }


}