package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.controller.Status;
import com.example.ollethboardproject.domain.entity.Chat;
import com.example.ollethboardproject.domain.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDTO {
    private Long id;
    private String senderName;
    private String receiverName;
    private String message;
    private LocalDateTime createdAt;

    public static ChatMessageDetailDTO fromEntity(Chat chat) {
        return new ChatMessageDetailDTO(
                chat.getId(),
                chat.getSenderName(),
                chat.getReceiverName(),
                chat.getMessage(),
                chat.getCreatedAt());
    }


}
