package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.controller.Status;
import com.example.ollethboardproject.domain.entity.ChatRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    //챗룸디티오가 필요할까 어차피 챗룸은필요없고 커뮤니티종속이니까 그냥 내용만 있어도될듯


    private Long id;
    private String senderName;
    private String receiverName;
    private String message;
    private Date date;
    private Status status;



    public static ChatRoomDTO entityToDto(ChatRoomEntity entity){

        ChatRoomDTO dto = new ChatRoomDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;

    }

}
