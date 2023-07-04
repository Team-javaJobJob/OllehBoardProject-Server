package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.controller.Status;
import com.example.ollethboardproject.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByMessage(String keyword);
    List<Chat> findAllBySenderNameAndStatusAndChatRoomId(String senderName, Status status, Long chatRoomId);

}

