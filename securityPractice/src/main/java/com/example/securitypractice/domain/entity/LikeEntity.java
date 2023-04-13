package com.example.securitypractice.domain.entity;

import com.example.securitypractice.domain.entity.audit.AuditEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"like\"")
public class LikeEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    private LikeEntity(UserEntity userEntity, PostEntity postEntity) {
        this.userEntity = userEntity;
        this.postEntity = postEntity;
    }
    public static LikeEntity of(UserEntity userEntity, PostEntity postEntity) {
        return new LikeEntity(userEntity, postEntity);
    }

}
