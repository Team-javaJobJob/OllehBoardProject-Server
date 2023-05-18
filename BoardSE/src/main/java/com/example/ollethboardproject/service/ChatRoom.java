//package com.example.ollethboardproject.service;
//
//import com.example.ollethboardproject.domain.dto.ChatRoomDetailDTO;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter
//@Data
//public class ChatRoom {
//
//    private String roomId; // 채팅방 아이디
//    private String name; // 채팅방 이름
//    private Set<WebSocketSession> sessions = new HashSet<>();
//
//    @Builder
//    public ChatRoom(String roomId,String name){
//        this.roomId = roomId;
//        this.name = name;
//    }
//
//    public void handlerActions(WebSocketSession session, ChatRoomDetailDTO chatRoomDetailDTO, ChatService chatService){
//
//        //message에 담김 타입확인
//        // 이떄 message 에서 getType으로 가져온 내용이 chatMessage의 열거형인 MessageType 안에 있는 ENTER와 동일 값이면
//        if (chatRoomDetailDTO.getType().equals(ChatRoomDetailDTO.MessageType.ENTER)) {
//            // 세선에 넘어온 세선을 담고
//            sessions.add(session);
//            // message에는 입장했다는 메시지 띄우기
//            chatRoomDetailDTO.setMessage(chatRoomDetailDTO.getSender() + "님이 입장했습니다.");
//            sendMessage(chatRoomDetailDTO, chatService);
//        }else if(chatRoomDetailDTO.getType().equals(ChatRoomDetailDTO.MessageType.TALK)){
//            chatRoomDetailDTO.setMessage(chatRoomDetailDTO.getMessage());
//            sendMessage(chatRoomDetailDTO, chatService);
//        }
//
//
//    }
//
//    private <T> void sendMessage(T message, ChatService chatService) {
//        sessions.parallelStream()
//                .forEach(session -> chatService.sendMessage(session, message));
//    }
//    }
//
//
//    /*
//    페이로드란?(payload)
//    - 전송되는 데이터
//    - 데이터를 전송 할 때, Header와 META데이터, 여러 체크 비트등과 같은 다양한 요소들을 함께 보낼 데이터 전송 효율과 안정성을 높히게된다
//    - 이때, 보내고자 하는 데이터 자체를 의미하는 것이 페이로드
//    - 예를 들면 택배 배송을 보내고 받을 때 물건이 페이로드고 송장이나 박스 등은 부가적인 것이기 때문에 페이로드가 아니다
//    - 다음은 JSON 에서 페이로드는 'data' 이고 나머지는 통신을 하는데 있어 용이하게 해주는 부가적 정보
//
//   {
//"status":
//"from":"localhost",
//"to":"http://codej.com/chatroom/1",
//"method":"GET",
//"data":{"message":"Welcome my room!"}
//}
//     */