package com.example.ollethboardproject.service;

import com.example.ollethboardproject.domain.dto.ChatMessageDetailDTO;
import com.example.ollethboardproject.domain.dto.ChatRoomDTO;
import com.example.ollethboardproject.domain.dto.ChatRoomDetailDTO;
import com.example.ollethboardproject.domain.entity.ChatMessageEntity;
import com.example.ollethboardproject.domain.entity.ChatRoomEntity;
import com.example.ollethboardproject.repository.ChatRepository;
import com.example.ollethboardproject.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    //채팅방 전체보기
    @Override
    public List<ChatRoomDTO> findAllRooms(){
        List<ChatRoomEntity> chatRoomEntityList = chatRoomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ChatRoomDTO> chatRoomList = new ArrayList<>();

        for (ChatRoomEntity c : chatRoomEntityList){
            chatRoomList.add(ChatRoomDTO.create(c.getRoomName()));
        }

        return chatRoomList;
    }
    // 채팅방 Id로 채팅방찾기
    @Override
    public ChatRoomDetailDTO findRoomById(String roomId){
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(roomId);
        ChatRoomDetailDTO chatRoomDetailDTO = ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoomEntity);
        return chatRoomDetailDTO;
    }

//    @Override
//    public ChatRoomDTO createChatRoomDTO(String name, Authentication authentication) {
//        return null;
//    }

    //채팅방 생성하기
    @Override
    public ChatRoomDTO createChatRoomDTO(String name, Authentication authentication){
        ChatRoomDTO room = ChatRoomDTO.create(name);
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toChatRoomEntity(room.getName(),room.getRoomId(),room.getChatOwner());
        chatRoomRepository.save(chatRoomEntity);

        return room;
    }


    @Override
    public void deleteById(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

    @Override
    public List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId) {
        List<ChatMessageEntity> chatMessageEntityList = chatRepository.findAllByChatRoomEntity_RoomId(roomId);
        List<ChatMessageDetailDTO> chatMessageList = new ArrayList<>();
        for (ChatMessageEntity c:chatMessageEntityList){
            chatMessageList.add(ChatMessageDetailDTO.toChatMessageDetailDTO(c));
        }
        return chatMessageList;
    }
}
