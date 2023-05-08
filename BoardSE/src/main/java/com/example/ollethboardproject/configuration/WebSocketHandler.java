package com.example.ollethboardproject.configuration;

import com.example.ollethboardproject.domain.dto.ChatMessage;
import com.example.ollethboardproject.service.ChatRoom;
import com.example.ollethboardproject.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("{}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}


/*  WebSocket 통신에서 서버 측에서 수신한 메시지를 처리하기 위한 핸들러 클래스
    @Component 어노테이션은 스프링 컨텍스트에서 Bean을 생성,구성하는 클래스임을 나타냄
    TextWebSocketHandler 를 상속받아서 텍스트 메시지 처리하기 위한 핸들러 구현
    handleTextMessage 메서드 오버라이드하여 서버측에서 수신한 메시지 처리

    String payload = message.getPayload(); => 메시지 페이로드를 문자열로 가져옴
    해당 메시지지를 log에 출력, ChatMessage로 맵핑, ObjectMapper 클래스는 JSON 데이터를 자바 객체로 매핑하기위해서 사용함
    ChatMessage에서 roomid가져와 방 있는지 확인 후 ChatRoom클래스의 HandlerActions 메서드 호출해서
    해당 방에서 처리해야 할 액션 수행하고 parameter 값으로 WebSocketSession 과 ChatService 객체 인자 전달

    WebSocket 통신에서 수신한 메시지를 처리하고 해당 방에서 처리 할 액션 수행


 */