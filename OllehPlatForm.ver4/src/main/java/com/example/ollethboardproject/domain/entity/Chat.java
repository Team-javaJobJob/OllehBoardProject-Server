package com.example.ollethboardproject.domain.entity;

import com.example.ollethboardproject.controller.Status;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.audit.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class Chat extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "message")
    private String message;
    @Enumerated(EnumType.STRING)
    private Status status;
}
