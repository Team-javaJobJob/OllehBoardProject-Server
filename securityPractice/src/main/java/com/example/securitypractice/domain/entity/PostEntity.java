package com.example.securitypractice.domain.entity;


import com.example.securitypractice.domain.entity.audit.AuditEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"post\"")

public class PostEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "postEntity")
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private PostEntity(String title, String body, UserEntity userEntity) {
        this.title = title;
        this.body = body;
        this.userEntity = userEntity;
    }

    public static PostEntity of(String title, String body, UserEntity userEntity) {
        return new PostEntity(title, body, userEntity);
    }
}
