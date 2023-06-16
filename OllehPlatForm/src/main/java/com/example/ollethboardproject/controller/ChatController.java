package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.chat.Message;

import com.example.ollethboardproject.domain.dto.ChatMessageDetailDTO;
import com.example.ollethboardproject.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChatService chatService;


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
//        채팅 내용 저장
        chatService.saveChatMessage(message);

        return message;
    }


    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        //        채팅 내용 저장
        chatService.saveChatMessage(message);

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        return message;
    }


    @GetMapping("/searchChat/{keyword}")
    @ResponseBody
//    public List<Chat> searchChatMessage(@RequestParam("keyword") String keyword){
    public ResponseEntity<List<ChatMessageDetailDTO>> searchChatMessage(@PathVariable String keyword) {

        if (keyword == null || keyword.isEmpty()) {
            // 검색어가 비어있는 경우 빈 결과를 반환하거나 에러 처리를 진행할 수 있습니다.
//            return Collections.emptyList(); // 빈 결과 반환 예시
            log.info("keyword null");
        }

        // 검색 로직을 구현하여 키워드에 해당하는 채팅 내용을 조회합니다.
        List<ChatMessageDetailDTO> searchResult = chatService.searchChatMessage(keyword);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}