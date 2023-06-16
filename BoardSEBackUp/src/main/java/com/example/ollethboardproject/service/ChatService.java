package com.example.ollethboardproject.service;


import com.example.ollethboardproject.controller.request.chat.Message;
import com.example.ollethboardproject.domain.entity.chat.Chat;
import com.example.ollethboardproject.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

//
//    @Autowired ChatService(ChatMessageRepository chatMessageRepository){
//        this.chatMessageRepository = chatMessageRepository
//    }

    // 채팅내용저장
    public Message saveChatMessage(Message message){
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



//    TODO : Searching the Message ***

    public List<Chat> searchChatMessage(String keyword){
        return chatMessageRepository.findByMessage(keyword);
    }


//    TODO : Authorization user for join the room but should consider with communitie

}
