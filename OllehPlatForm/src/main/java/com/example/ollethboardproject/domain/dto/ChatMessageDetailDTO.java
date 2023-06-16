package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDTO {
    private Long id;
    private String senderName;
    private String receiverName;
    private String message;
    private String createdAt;

    public static ChatMessageDetailDTO fromEntity(Chat chat) {
        return new ChatMessageDetailDTO(
                chat.getId(),
                chat.getSenderName(),
                chat.getReceiverName(),
                chat.getMessage(),
                chat.getCreatedAt());
    }


}
