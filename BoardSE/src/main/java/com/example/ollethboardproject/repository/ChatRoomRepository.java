package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository <ChatRoomEntity, Long> {
    ChatRoomEntity findByRoomId(String roomId);
}
