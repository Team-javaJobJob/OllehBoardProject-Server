package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Chat;
import com.example.ollethboardproject.domain.entity.ChatRoom;
import com.example.ollethboardproject.domain.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {


    private Long id;

    private String roomName;
    private Community community;

    private List<Chat> chatDetails;



    // chatroom entitiy -> dto로 변환
    public static ChatRoomDTO fromEntity(ChatRoom chatRoom){
        return new ChatRoomDTO(
                chatRoom.getId(),
                chatRoom.getRoomName(),
                chatRoom.getCommunity(),
                chatRoom.getChatDetails()
        );
    }
}
