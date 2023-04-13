package com.example.securitypractice.domain.entity;

import com.example.securitypractice.domain.entity.audit.AuditEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE comment SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"comment\"")
public class CommentEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Column(name = "comment")
    private String comment;

    private CommentEntity(UserEntity userEntity, PostEntity postEntity, String comment) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
        this.comment = comment;
    }

    public static CommentEntity of(UserEntity userEntity, PostEntity postEntity, String comment) {
        return new CommentEntity(userEntity, postEntity, comment);
    }
}
