//package com.example.ollethboardproject.domain.entity;
//
//import com.example.ollethboardproject.controller.Status;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
////채팅내용 Entity
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "Chat_table")
//public class ChatMessageEntity {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "chat_id")
//    private Long id;
//
////    @ManyToOne(fetch = FetchType.EAGER)
////    @JoinColumn(name = "chatRoom_id")
////    private ChatRoomEntity chatRoomEntity;
//
//    @Column
//    private String senderName;
//
//    @Column
//    private String receiverName;
//    @Column
//    private String message;
//
//    @Enumerated(EnumType.STRING)
//    private Status status;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime date;
//
//
//    }
//
//
//}
//
