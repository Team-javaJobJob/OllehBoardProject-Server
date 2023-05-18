package com.example.ollethboardproject.domain.entity;


import com.example.ollethboardproject.domain.entity.audit.AuditEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// 채팅룸을 위한 Entity
@Entity
@Getter
@NoArgsConstructor
@Data
@Table(name = "chatRoom_table")
public class ChatRoomEntity extends AuditEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Chatroom_id")
    private Long Id;

    @Column private String roomId;
    @Column private String roomName;

    @Column private String chatOwner;


//    @ManyToMany
//    @JoinTable(name = "chat_user_name",joinColumns = @JoinColumn(name = "room_id"),inverseJoinColumns = @JoinColumn(name = "userName"))
//    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoomEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

//    public static ChatRoomEntity create(String roomName){
//        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
//        chatRoomEntity.roomName = roomName;
//        return chatRoomEntity;
//    }

    public static ChatRoomEntity toChatRoomEntity(String roomName, String roomId, String chatOwner){
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setRoomName(roomName);
        chatRoomEntity.setRoomId(roomId);
        chatRoomEntity.setChatOwner(chatOwner);
        return chatRoomEntity;
    }

}
