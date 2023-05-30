package com.example.ollethboardproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDTO {

    private Long chatId;
//    private Long chatRoomId;

//    private String roomId;
    private String senderName;
    private String receiverName;
    private String message;

    private LocalDateTime sendDate;


}
