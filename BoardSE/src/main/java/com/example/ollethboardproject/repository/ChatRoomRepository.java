package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.ChatRoom;
import com.example.ollethboardproject.domain.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {


    Optional<ChatRoom> findByRoomName(String roomName);
    Optional<ChatRoom> findByCommunityId(Long communityId);

}
