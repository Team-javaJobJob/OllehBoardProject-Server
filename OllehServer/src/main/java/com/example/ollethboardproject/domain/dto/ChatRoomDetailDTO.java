//package com.example.ollethboardproject.domain.dto;
//
//import com.example.ollethboardproject.domain.entity.ChatRoomEntity;
//import lombok.*;
//
///*
//채팅 message DTO
//채팅 내용은 들어오는 사람에 대한 환영메시지에 대한 ENTER, 방에 있는 사람들이 채팅을 칠때 사용하는 TALK 두가지 메시지 타입
// */
//@Getter
//@Setter
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ChatRoomDetailDTO {
//
//    private Long chatRoomId;
//    private String chatOwner;
//    private String roomId;
//    private String name;
//
//
//    public static ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoomEntity chatRoomEntity){
//        ChatRoomDetailDTO chatRoomDetailDTO = new ChatRoomDetailDTO();
//
//        chatRoomDetailDTO.setChatRoomId(chatRoomEntity.getId());
//        chatRoomDetailDTO.setChatOwner(chatRoomEntity.getChatOwner());
//        chatRoomDetailDTO.setRoomId(chatRoomEntity.getRoomId());
//        chatRoomDetailDTO.setName(chatRoomEntity.getRoomName());
//
//
//        return chatRoomDetailDTO;
//    }
//}
