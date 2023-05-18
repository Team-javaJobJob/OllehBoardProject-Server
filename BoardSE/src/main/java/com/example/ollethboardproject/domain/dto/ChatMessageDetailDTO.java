package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.ChatMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDTO {

    private Long chatId;
    private Long chatRoomId;

    private String roomId;
    private String writer;
    private String message;

    private LocalDateTime sendDate;

    public static ChatMessageDetailDTO toChatMessageDetailDTO(ChatMessageEntity chatMessageEntity){

        ChatMessageDetailDTO chatMessageDetailDTO = new ChatMessageDetailDTO();

        chatMessageDetailDTO.setChatId(chatMessageEntity.getId());

        chatMessageDetailDTO.setChatRoomId(chatMessageEntity.getChatRoomEntity().getId());
        chatMessageDetailDTO.setRoomId(chatMessageEntity.getChatRoomEntity().getRoomId());

        chatMessageDetailDTO.setWriter(chatMessageEntity.getWriter());
        chatMessageDetailDTO.setMessage(chatMessageEntity.getMessage());
        chatMessageDetailDTO.setSendDate(chatMessageEntity.getSendDate());

        return chatMessageDetailDTO;


    }
}
