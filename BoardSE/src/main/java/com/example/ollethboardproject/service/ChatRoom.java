package com.example.ollethboardproject.service;

import com.example.ollethboardproject.domain.dto.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId,String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
    }


    /*
        roomId,name,sessions 필드 만들고 roomId는 고유식별자로 지정.
        name 은 채팅방이름,
        session 은 채팅방에 접속한 WebSocketSession 객체들의 집합을 나타내며 ,Set 인터페이스를 구현한 HashSet 객체로 초기화해놓음

        @Builder 어노테이션은 Lombok 라이브러리의 어노테이션으로 객체 생성 시에 빌더 패턴을 사용하여 생성자의 파라미터를 설정할 수 있도록 한다

        handlerAction 메서드는 WebSocketSession, ChatMessage, ChatService 객체를 인자로 받아
        채팅방에서 처리해야할 액션 수행
        ChatMessage 의 타입이 ENTER 인 경우 , 해당 WebSocketSession 객체를 Sessions 에 추가하고, ChatMessage의
        메시지 내용을 "님이 입장했습니다" 로 설정함 (추후 view단과 연동할때 재확인필요)

        sendMesasge 메서드는 Generic으로 사용, T 타입의 메시지와 ChatService 객체를 인자로 받고,
        session 집합에 포함된 모든 WebSocketSession 객체에 대해 chatService의 sendMessage 메서드를 호출하여
        메시지 전송
        따라서, ChatRoom 클래스는 채팅방을 나타내며, 채팅방에 접속한 WebSocketSession 객체들을 sessions에 관리하고, 채팅방에서 처리해야 할 액션과 메시지를 전송하는 기능을 제공합니다
     */