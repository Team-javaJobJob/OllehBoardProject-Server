package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.chat.Message;
import com.example.ollethboardproject.domain.dto.ChatMessageDetailDTO;
import com.example.ollethboardproject.repository.ChatMessageRepository;
import com.example.ollethboardproject.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    private final ChatService chatService;


    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatMessageRepository chatMessageRepository, ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMessageRepository = chatMessageRepository;
        this.chatService = chatService;
    }

    // message 경로로 메시지 받고 처리, sendTo 어노테이션으로 해당 메시지를 chatroom/public 으로 보내줌
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    @ExceptionHandler
    public Message receiveMessage(@Payload Message message,String communityName, String roomName)
    {
//        채팅 내용 저장
        chatService.saveChatMessage(message, communityName, roomName);
        return message;
    }

    @MessageMapping("/list")
    @SendTo("/chatroom/list")
    @ExceptionHandler
    public Message selectMemberList(@Payload Message message)
    {
        String memberList = chatService.selectMemberList(message);
        message.setMessage(memberList);
        return message;
    }



    // private-message 경로로 개인메시지 받고 처리, 받은메시지를 db에 저장하고 SimpMessageingTemplate 의 ConvertAndSendToUser 로 특정 사용자에게 메시지 전송
    @MessageMapping("/private-message")
    @ExceptionHandler
    public Message recMessage(@Payload Message message, String communityName, String roomName){
        //        채팅 내용 저장
        chatService.saveChatMessage(message, communityName, roomName);

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody Message message,String communityName, String roomName) {
        // 클라이언트에서 보낸 message를 사용하여 필요한 로직을 구현합니다.
        // 예를 들어, 채팅 내용을 데이터베이스에 저장하고, 변경 내용을 클라이언트에게 전송할 수 있습니다.
        chatService.saveChatMessage(message, communityName, roomName);

        // 응답 코드와 메시지를 함께 리턴합니다.
        return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
    }

    //
    @MessageMapping("/message/{communityId}")
    @SendTo("/chatroom/{communityId}/public")
    @ExceptionHandler
    public Message receiveMessage2(@Payload Message message,String communityName, String roomName)  {
// 채팅 내용 저장
        chatService.saveChatMessage(message, communityName, roomName);
// 아래코드가 계속 user must not be null 에러를 발생시킴 뭔가 authentication 처리를 안해서 그런거같은데 우선 이건 제외
//        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/chatroom/{communityId}/public",message);
        return message;
    }




    @GetMapping("/searchChat/{keyword}")
    @ResponseBody
//    public List<Chat> searchChatMessage(@RequestParam("keyword") String keyword){
    public ResponseEntity<List<ChatMessageDetailDTO>> searchChatMessage(@PathVariable String keyword){
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