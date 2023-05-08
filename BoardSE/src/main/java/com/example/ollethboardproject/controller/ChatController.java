package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.service.ChatRoom;
import com.example.ollethboardproject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ChatController {
    private final ChatService chatService;


    @PostMapping
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }


    @GetMapping("/ollehChat")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }


}

/*
이 코드는 ChatController 클래스이다. @RequiredArgsConstructor 어노테이션은 Lombok 라이브러리의 어노테이션 중 하나로,
클래스 내의 final 필드에 대한 생성자를 자동으로 생성하고.

@RestController 어노테이션은 Spring Framework에서 RESTful 웹 서비스를 구현할 때 사용되는 어노테이션 중 하나로,
컨트롤러 클래스에 @Controller와 @ResponseBody 어노테이션을 합쳐 놓은 것입니다.
이 어노테이션을 사용하면 컨트롤러 클래스의 모든 메서드에서 @ResponseBody 어노테이션을 생략할 수 있습니다.


ChatController 클래스는 ChatService 객체를 필드로 갖고 있으며,
createRoom 메서드는 POST 방식의 HTTP 요청을 처리합니다.
요청 본문에는 채팅방의 이름을 나타내는 문자열이 포함되어 있으며,
ChatService 객체의 createRoom 메서드를 호출하여 새로운 채팅방을 생성한다.

findAllRoom 메서드는 GET 방식의 HTTP 요청을 처리하고
ChatService 객체의 findAllRoom 메서드를 호출하여 모든 채팅방의
목록을 가져옵니다. 반환 값은 List<ChatRoom> 타입이며, JSON 형태로 클라이언트에게 전송된다.

따라서, ChatController 클래스는 RESTful API를 구현하며,
클라이언트의 요청에 따라 ChatService 객체의 메서드를 호출하여 처리합니다. c
reateRoom 메서드는 새로운 채팅방을 생성하고, findAllRoom 메서드는 모든 채팅방의 목록을 반환합니다.
 */