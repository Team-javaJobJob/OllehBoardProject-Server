//package com.example.ollethboardproject.configuration;
//
//import com.example.ollethboardproject.domain.dto.ChatRoomDetailDTO;
//import com.example.ollethboardproject.service.ChatRoom;
//import com.example.ollethboardproject.service.ChatService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        String payload = (String) message.getPayload();
//        log.info("payload : {}",payload);
//
////        TextMessage intialGretting = new TextMessage("Welcome to Chat Server");
//        //JSON -> Java Object
//        ChatRoomDetailDTO chatRoomDetailDTO = objectMapper.readValue(payload, ChatRoomDetailDTO.class);
//        log.info("session : {}", chatRoomDetailDTO.toString());
//
//        ChatRoom room = chatService.findRoomById(chatRoomDetailDTO.getRoomId());
//        log.info("room : {}",room.toString());
//
//        room.handlerActions(session, chatRoomDetailDTO, chatService);
//
//    }
//
//    /** Client가 접속 시 호출되는 메서드*/
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info(session + " 클라이언트 접속");
//    }
//    /** client가 접속 시 호출되는 메서드*/
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info(session + " 클라이언트 접속 해제");
//    }
//
//
//}
