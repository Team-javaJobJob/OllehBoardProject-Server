package com.example.ollethboardproject.service;


import com.example.ollethboardproject.controller.request.chat.Message;
import com.example.ollethboardproject.domain.dto.ChatMessageDetailDTO;
import com.example.ollethboardproject.domain.entity.Chat;
import com.example.ollethboardproject.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    // 채팅내용저장
    public Message saveChatMessage(Message message) {

        Chat chat = new Chat();

        chat.setSenderName(message.getSenderName());
        chat.setReceiverName(message.getReceiverName());
        chat.setMessage(message.getMessage());
        chat.setStatus(message.getStatus());

        //chat Entity 저장
        Chat saveChat = chatMessageRepository.save(chat);
        // 저장된 ChatEntity ID Value 를 message 객체에 설정
        message.setMessage(saveChat.getMessage());

        // Message 객체 변환
        return message;
    }


    public List<ChatMessageDetailDTO> searchChatMessage(String keyword) {
        String regex = "(?i).*" + Pattern.quote(keyword) + ".*";

        return chatMessageRepository.findAll()
                .stream()
                .filter(message -> {
                    String chatMessage = message.getMessage();
                    return chatMessage != null && chatMessage.matches(regex);
                })
                .map(ChatMessageDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }


    // entity -> dto 변환 메서드
    private ChatMessageDetailDTO mapToChatMessageDetailDTO(Chat chat) {
        return ChatMessageDetailDTO.fromEntity(chat);
    }



//    TODO : Authorization user for join the room but should consider with communities

}
