package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.chat.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
//        System.out.println(message.toString());
        return message;
    }
}


/*
페이로드(Payload)란
페이로드(payload)는 전송되는 데이터를 의미한다.
데이터를 전송할 때, 헤더와 메타 데이터, 에러 체크 비트 등과 같은 다양한 요소들을 함께 보내어,
데이터 전송의 효율과 안정성을 높이게 된다.
이 때, 보내고자 하는 데이터 자체를 의미하는 것이 바로 페이로드이다.
우리가 택배 배송을 보내고 받을 때, 택배 물건이 페이로드이고, 송장이나 박스,
뽁뽁이와 같은 완충재 등은 부가적인 것이기 때문에 페이로드가 아니다.
*/

/*
SimpMessagingTemplate는 Spring Framework의 클래스로,
웹소켓(WebSocket)을 통해 클라이언트와 서버 간에 실시간으로 메시지를 교환하는 데 사용됩니다.

위의 코드에서 SimpMessagingTemplate는 @MessageMapping 어노테이션이 지정된 메서드들에서 WebSocket을 통해 메시지를 보내는 역할을 합니다.

simpMessagingTemplate.convertAndSendToUser() 메서드는 특정 사용자에게 개인적인 메시지를 보낼 때 사용됩니다.
이 메서드의 첫 번째 인자로는
메시지를 받을 사용자의 식별자(일반적으로는 사용자 이름)를 전달하고, 두 번째 인자로는 메시지를 보낼 대상의 주소를 지정합니다. 이 경우 "/private" 주소에 메시지를 보내게 됩니다.

SimpMessagingTemplate를 사용하면 서버 측에서 클라이언트로 메시지를 보낼 수 있으며, 클라이언트 측에서도 서버로 메시지를 보낼 수 있습니다.
이를 통해 실시간 채팅이나 알림 기능 등을 구현할 수 있습니다.





*/