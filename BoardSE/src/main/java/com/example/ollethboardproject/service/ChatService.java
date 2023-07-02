package com.example.ollethboardproject.service;


import com.example.ollethboardproject.controller.Status;
import com.example.ollethboardproject.controller.request.chat.Message;
import com.example.ollethboardproject.domain.dto.ChatMessageDetailDTO;
import com.example.ollethboardproject.domain.entity.Chat;
import com.example.ollethboardproject.domain.entity.ChatRoom;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.repository.ChatMessageRepository;
import com.example.ollethboardproject.repository.ChatRepository;
import com.example.ollethboardproject.repository.ChatRoomRepository;
import com.example.ollethboardproject.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, Chat> redisTemplate;

    private final CommunityRepository communityRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;


    // 채팅내용저장
    public Message saveChatMessage(Message message, String communityName, String roomName){
        Chat chat = new Chat();

        Community community = communityRepository.findById(message.getCommunityId()).get();
        ChatRoom chatRoom = chatRoomRepository.findByCommunityId(message.getCommunityId()).get();

        chat.setSenderName(message.getSenderName());
        chat.setReceiverName(message.getReceiverName());
        chat.setMessage(message.getMessage());
        chat.setStatus(message.getStatus());

        //chat Entity 저장
        chat.setChatRoom(chatRoom);
        chat.setCommunity(community);

        // 가입은 한번만 기록한다.
        if (message.getStatus() == Status.JOIN) {
            List<Chat> allBySenderNameAndStatus = chatMessageRepository.findAllBySenderNameAndStatusAndChatRoomId(message.getSenderName(), message.getStatus(), message.getCommunityId());
            if (allBySenderNameAndStatus.size() > 0) {
                return message;
            }
        }

        Chat saveChat = chatMessageRepository.save(chat);

        // 저장된 ChatEntity ID Value 를 message 객체에 설정
        message.setMessage(saveChat.getMessage());
        message.setStatus(saveChat.getStatus());

        //Redis 에 저장
//        ValueOperations<String,Chat> valueOps = redisTemplate.opsForValue();
//        valueOps.set(chat.getMessage(),chat);

        // Message 객체 변환
        return message;
    }

    public String selectMemberList(Message message) {
        List<Chat> allByChatRoomId = chatRepository.findAllByChatRoomId(message.getCommunityId());
        String sendersName = allByChatRoomId.stream()
                .map(Chat::getSenderName)
                .distinct()
                .collect(Collectors.joining(","));
        return sendersName;
    }

//
//    public Message saveChatMessage(Message message, Long communityId, Long chatRoomId){
//        Chat chat = new Chat();
//
//        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
//        Community community = optionalCommunity.orElse(null);
//
//        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomId);
//        ChatRoom chatRoom;
//        if(optionalChatRoom.isPresent()) {
//            chatRoom = optionalChatRoom.get();
//        } else {
//            chatRoom = chatRoomRepository.findById(1L).orElseThrow(
//                    ()-> new RuntimeException("1번 챗룸이 존재하지 않습니다."));
//        }
//
//        chat.setSenderName(message.getSenderName());
//        chat.setReceiverName(message.getReceiverName());
//        chat.setMessage(message.getMessage());
//        chat.setStatus(message.getStatus());
//        //chat Entity 저장
//        chat.setChatRoom(chatRoom);
//        chat.setCommunity(community);
//
//        Chat saveChat = chatMessageRepository.save(chat);
//        // 저장된 ChatEntity ID Value 를 message 객체에 설정
//        message.setMessage(saveChat.getMessage());
//        message.setStatus(saveChat.getStatus());
//
//        //Redis 에 저장
//        ValueOperations<String,Chat> valueOps = redisTemplate.opsForValue();
//        valueOps.set(chat.getMessage(),chat);
//
//        // Message 객체 변환
//        return message;
//    }

    public List<ChatMessageDetailDTO> searchChatMessage(String keyword) {
        String regex = "(?i).*" + Pattern.quote(keyword) + ".*";

        // ChatMessage 조회 (Redis)
        List<Chat> chatMessages = redisTemplate.opsForValue().multiGet(redisTemplate.keys("*"));

        if(chatMessages.isEmpty()){
//            ChatMessage db에서 조회
            chatMessages = chatMessageRepository.findAll().stream().filter(message -> {
                        String chatMessage = message.getMessage();
                        return chatMessage != null && chatMessage.matches(regex);
                    })
                    .collect(Collectors.toList());
            // DB에서 조회한 결과를 Redis에 저장
            for (Chat chat : chatMessages) {
                ValueOperations<String, Chat> valueOps = redisTemplate.opsForValue();
                valueOps.set(String.valueOf(chat.getId()), chat);
            }
        }
        return chatMessages.stream()
                .map(ChatMessageDetailDTO::fromEntity)
                .collect(Collectors.toList());

    }

    // entity -> dto 변환 메서드
    private ChatMessageDetailDTO mapToChatMessageDetailDTO(Chat chat) {
        return ChatMessageDetailDTO.fromEntity(chat);
    }

}
