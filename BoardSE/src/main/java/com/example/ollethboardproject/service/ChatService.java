package com.example.ollethboardproject.service;

import com.example.ollethboardproject.domain.dto.ChatMessageDetailDTO;
import com.example.ollethboardproject.domain.dto.ChatRoomDTO;
import com.example.ollethboardproject.domain.dto.ChatRoomDetailDTO;
import org.springframework.security.core.Authentication;

import java.util.*;



public interface ChatService {
    List<ChatRoomDTO> findAllRooms();

    ChatRoomDetailDTO findRoomById(String id);
    //채팅방 생성하기
    ChatRoomDTO createChatRoomDTO(String name, Authentication authentication);


    void deleteById(Long chatRoomId);

    List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId);


}
